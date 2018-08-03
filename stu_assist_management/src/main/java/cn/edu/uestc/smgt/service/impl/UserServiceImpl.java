package cn.edu.uestc.smgt.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import cn.edu.uestc.smgt.common.QueryBase;
import cn.edu.uestc.smgt.common.Status;
import cn.edu.uestc.smgt.common.StatusType;
import cn.edu.uestc.smgt.dao.DeptMapper;
import cn.edu.uestc.smgt.dao.UserMapper;
import cn.edu.uestc.smgt.pojo.Dept;
import cn.edu.uestc.smgt.pojo.User;
import cn.edu.uestc.smgt.pojo.dto.PwdChangeDTO;
import cn.edu.uestc.smgt.pojo.dto.UserDTO;
import cn.edu.uestc.smgt.service.UserService;
import cn.edu.uestc.smgt.utils.DAOResultUtil;

@Service("UserService")
public class UserServiceImpl implements UserService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private DeptMapper deptMapper;

	public int login(User user) {
		User tempUser = null;
		try {
			tempUser = userMapper.selectByLogin(user);
			if (tempUser != null)
				if (user.getRole() == tempUser.getRole()) {
					return StatusType.SUCCESS.getVal();
				} else {
					return StatusType.ROLE_INVALID.getVal();
				}
			else {
				return StatusType.PASSWD_NOT_MATCH.getVal();
			}
		} catch (Throwable e) {
			logger.error("调用UserServiceImpl.login出错,传入参数user={},查询到user={}",
					new Object[] { user, tempUser }, e);
			return StatusType.EXCEPTION.getVal();
		}
	}

	public User getUser(User user) {
		User tempUser = null;
		try {
			tempUser = userMapper.selectByLogin(user);
		} catch (Throwable e) {
			logger.error("调用UserServiceImpl.getUser出错,传入参数user={},查询到user={}",
					new Object[] { user, tempUser }, e);
		}
		return tempUser;
	}

	public int checkRole(User checkUser, User currentUser) {
		try {
			if (checkUser == null || currentUser == null) {
				return StatusType.OBJECT_NULL.getVal();
			}
			int checkRole = checkUser.getRole();
			int currentRole = currentUser.getRole();
			if (checkRole == currentRole) {
				return StatusType.SUCCESS.getVal();
			} else {
				return StatusType.ROLE_INVALID.getVal();
			}
		} catch (Throwable e) {
			logger.error("调用UserServiceImpl.checkRole出错,标准参数user={},当前user={}",
					new Object[] { checkUser, currentUser }, e);
			return StatusType.EXCEPTION.getVal();
		}
	}

	public int changePwd(PwdChangeDTO pwdChangeDTO) {
		if (pwdChangeDTO == null || pwdChangeDTO.getFirstPwd() == null
				|| pwdChangeDTO.getSecondPwd() == null)
			return StatusType.OBJECT_NULL.getVal();
		int rows = 0;
		try {
			final String firstPwd = pwdChangeDTO.getFirstPwd();
			final String secondPwd = pwdChangeDTO.getSecondPwd();
			final String originPwd = pwdChangeDTO.getOriginPwd();
			if (firstPwd.equals(secondPwd)) {
				final String username = pwdChangeDTO.getLoginName();
				User user = userMapper.selectByLoginName(username);
				if (user != null) {
					if (user.getPwd().equals(originPwd)) {
						user.setLoginName(username);
						user.setPwd(firstPwd);
						rows = this.userMapper.updatePwdByLoginName(user);
					} else
						return StatusType.PASSWD_NOT_MATCH.getVal();
				}
			}
		} catch (Throwable e) {
			logger.error("调用UserServiceImpl.changePwd出错,PwdChangeDTO={}",
					new Object[] { pwdChangeDTO }, e);
			return StatusType.EXCEPTION.getVal();
		}
		return DAOResultUtil.getAddUpDateRemoveResult(rows, 0).getVal();
	}

	/**
	 * 增加一条用户信息
	 * 
	 * @author ljd 2016-10-25
	 * 
	 */
	public int add(User user) {
		// TODO Auto-generated method stub
		return userMapper.insert(user);
	}

	/**
	 * 根据用户名查询用户是否存在
	 * 
	 * @author ljd 2016-10-25
	 */
	public User getUserByLoginName(String loginName) {
		// TODO Auto-generated method stub
		return userMapper.selectByLoginName(loginName);
	}

	/**
	 * 根据用户名删除一个用户
	 * 
	 * @author ljd 2016-10-25
	 */
	public int delete(String loginName) {
		// TODO Auto-generated method stub
		return userMapper.deleteByLoginName(loginName);
	}

	/**
	 * 分页查询用户信息
	 */
	public void query(QueryBase querybase) {
		querybase.setTotalRow(userMapper.size(querybase.getParameters()));// 获取总行数
		List<UserDTO> out = new ArrayList<UserDTO>();
		List<User> userList = userMapper.selectByPageAndSelections(querybase);
		UserDTO temp = null;
		Dept dept = null;
		for (User user : userList) {
			temp = new UserDTO(user);
			dept = deptMapper.selectByDeptId(user.getDeptId());
			if (dept != null)
				temp.setDeptName(dept.getDeptName());
			out.add(temp);
		}
		querybase.setResults(out);// 获取需要返回的数据集
	}

	public Set<String> findRoles(String username) {
		Set<String> roleSet = new HashSet<String>();
		try {
			String roles = userMapper.findRoles(username);
			if (roles == null)
				return roleSet;
			String[] rolesArray = roles.split(",");
			Collections.addAll(roleSet, rolesArray);
			logger.info("roleSet=" + roleSet);
			return roleSet;
		} catch (Exception e) {
			logger.error("调用UserServiceImpl.findRoles错误,username={}", username,
					e);
			return roleSet;
		}
	}

	public List<String> getResources(String roleName) {
		List<String> resourceSet = new ArrayList<String>();
		try {
			String resources = userMapper.findResources(roleName);
			String[] resourcesArray = resources.split(",");
			Collections.addAll(resourceSet, resourcesArray);
			return resourceSet;
		} catch (Exception e) {
			logger.error("调用UserServiceImpl.findPermissions错误,roleName={}",
					roleName, e);
			return resourceSet;
		}
	}

	@Cacheable(value = "permission_cache", key = "#username")
	public Set<String> findPermissions(String username) {
		logger.info("findPermissions,{}", username);
		Set<String> permissionSet = new HashSet<String>();
		try {
			Set<String> roles = findRoles(username);

			for (String role : roles) {
				List<String> permissions = userMapper
						.findPermissions(getResources(role));
				logger.info("permissions=" + permissions);
				permissionSet.addAll(permissions);
			}
			return permissionSet;
		} catch (Exception e) {
			logger.error("调用UserServiceImpl.findPermissions错误,username={}",
					username, e);
			return permissionSet;
		}
	}

	public boolean checkRoleWhenLogin(String loginName, Byte roleType) {
		User currentUser = userMapper.selectByLoginName(loginName);
		if (currentUser == null || currentUser.getRole() != roleType) {
			return false;
		}
		return true;
	}

	public int resetPwd(PwdChangeDTO pwdChangeDTO) {
		// TODO Auto-generated method stub
		int rows = 0;
		try {
			final String firstPwd = pwdChangeDTO.getFirstPwd();
			final String secondPwd = pwdChangeDTO.getSecondPwd();
			if (firstPwd.equals(secondPwd)) {
				final String username = pwdChangeDTO.getLoginName();
				User user = userMapper.selectByLoginName(username);
				if (user != null) {
					user.setLoginName(username);
					user.setPwd(firstPwd);
					rows = this.userMapper.updatePwdByLoginName(user);
				}
			}
		} catch (Throwable e) {
			logger.error("调用UserServiceImpl.resetPwd出错,PwdChangeDTO={}",
					new Object[] { pwdChangeDTO }, e);
			return StatusType.EXCEPTION.getVal();
		}
		return DAOResultUtil.getAddUpDateRemoveResult(rows, 0).getVal();
	}

}
