package cn.edu.uestc.smgt.service;

import java.util.Set;

import cn.edu.uestc.smgt.common.QueryBase;
import cn.edu.uestc.smgt.pojo.User;
import cn.edu.uestc.smgt.pojo.dto.PwdChangeDTO;

public interface UserService {
	int login(User user);

	User getUser(User user);

	int checkRole(User checkUser, User currentUser);

	int changePwd(PwdChangeDTO pwdChangeDTO);

	int resetPwd(PwdChangeDTO pwdChangeDTO);

	int add(User user);

	User getUserByLoginName(String loginName);

	int delete(String loginName);

	void query(QueryBase querybase);

	/**
	 * 根据用户名查找其角色
	 * 
	 * @param username
	 * @return
	 */
	public Set<String> findRoles(String username);

	/**
	 * 根据用户名查找其权限
	 * 
	 * @param username
	 * @return
	 */
	public Set<String> findPermissions(String username);

	/**
	 * 登录时检查角色
	 * 
	 * @return
	 */
	public boolean checkRoleWhenLogin(String loginName, Byte roleType);

}
