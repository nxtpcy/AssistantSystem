package cn.edu.uestc.smgt.controller.base;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.uestc.smgt.annotation.SysControllerLogAnnotation;
import cn.edu.uestc.smgt.common.QueryBase;
import cn.edu.uestc.smgt.common.Response;
import cn.edu.uestc.smgt.common.StatusType;
import cn.edu.uestc.smgt.pojo.Dept;
import cn.edu.uestc.smgt.pojo.User;
import cn.edu.uestc.smgt.service.UserService;
import cn.edu.uestc.smgt.utils.UserUtil;

@Controller
@RequestMapping("/base/")
public class ManageUserController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private UserService userService;

	/**
	 * 增加一条用户信息
	 * 
	 * @author ljd 2016-10-25
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 */
	@SysControllerLogAnnotation(desc = "增加用户")
	@RequestMapping(value = "user/add", method = RequestMethod.POST)
	@ResponseBody
	public Object add(HttpServletRequest request, HttpServletResponse response,
			@RequestBody User user) {
		try {
			if (user.getLoginName() == null) {
				return new Response(-1, "用户名不能为空");
			}
			if (user.getDeptId() == null) {
				return new Response(-1, "请选择部门");
			}
			if (user != null && user.getPwd() == null) {
				user.setPwd(UserUtil.string2MD5("111111"));
			}
			if (user.getRole() == null) {
				user.setRole((byte) 1);
			}
			if (userService.getUserByLoginName(user.getLoginName()) != null) {
				return new Response(-1, "该用户名已经存在");
			}
			user.setCreateTime(new Date());
			int status = userService.add(user);
			String message = StatusType.value(status).getMessage();
			return new Response(status, message);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("调用ManageUserController.add出错,user={},error={}",
					new Object[] { user, e });
			return new Response(StatusType.EXCEPTION.getVal(),
					StatusType.EXCEPTION.getMessage());
		}
	}

	/**
	 * 删除一个用户,删除的用户为role=1，这个操作只能超级管理员使用
	 * 
	 * @author ljd 2016-10-25
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 */
	@SysControllerLogAnnotation(desc = "删除用户")
	@RequestMapping(value = "user/delete", method = RequestMethod.POST)
	@ResponseBody
	public Object delete(HttpServletRequest request,
			HttpServletResponse response, @RequestBody User user) {
		try {
			User getUser = userService.getUserByLoginName(user.getLoginName());
			if (getUser == null) {
				return new Response(-1, "该用户不存在");
			}
			if (getUser.getRole() == 0) {
				return new Response(-1, "您无权删除超级管理员");
			}
			int status = userService.delete(user.getLoginName());
			String message = StatusType.value(status).getMessage();
			return new Response(status, message);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("调用ManageUserController.delete出错,dept={},error={}",
					new Object[] { user.getLoginName(), e });
			return new Response(StatusType.EXCEPTION.getVal(),
					StatusType.EXCEPTION.getMessage());
		}
	}

	/**
	 * 根据条件分页查询用户信息
	 */
	@RequestMapping(value = "user/list", method = RequestMethod.POST)
	@ResponseBody
	public Object list(HttpServletRequest request,
			HttpServletResponse response, @RequestBody Map map) {
		try {
			QueryBase querybase = new QueryBase();
			String deptIds = (String) map.get("deptId");
			String[] deptIdArray = null;
			if (StringUtils.isEmpty(deptIds)) {
				deptIdArray = null;
			} else {
				deptIdArray = deptIds.split(",");
			}
			querybase.addParameter("deptId", deptIdArray);
			querybase.addParameter("loginName", map.get("loginName"));
			querybase.addParameter("role", 1);// 默认查询所有学院的用户信息
			querybase.setPageSize(Long.parseLong(map.get("rows").toString()));
			querybase
					.setCurrentPage(Long.parseLong(map.get("page").toString()));
			userService.query(querybase);
			HashMap result = new HashMap();
			result.put("total", querybase.getTotalRow());
			result.put("result", querybase.getResults());
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("调用ManageUserController.list出错,map={},error={}",
					new Object[] { map, e });
			return new Response(StatusType.EXCEPTION.getVal(),
					StatusType.EXCEPTION.getMessage());
		}
	}

	/**
	 * 根据条件分页查询用户信息
	 */
	@RequestMapping(value = "user/list1", method = RequestMethod.POST)
	@ResponseBody
	public Object list1(HttpServletRequest request,
			HttpServletResponse response, @RequestParam Map map) {
		try {
			QueryBase querybase = new QueryBase();
			String deptIds = (String) map.get("deptId");
			String[] deptIdArray = null;
			if (StringUtils.isEmpty(deptIds)) {
				deptIdArray = null;
			} else {
				deptIdArray = deptIds.split(",");
			}
			querybase.addParameter("deptId", deptIdArray);
			querybase.addParameter("loginName", map.get("loginName"));
			querybase.addParameter("role", 1);// 默认查询所有学院的用户信息
			querybase.setPageSize(Long.parseLong(map.get("rows").toString()));
			querybase
					.setCurrentPage(Long.parseLong(map.get("page").toString()));
			userService.query(querybase);
			HashMap result = new HashMap();
			result.put("total", querybase.getTotalRow());
			result.put("rows", querybase.getResults());
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("调用ManageUserController.list出错,map={},error={}",
					new Object[] { map, e });
			return new Response(StatusType.EXCEPTION.getVal(),
					StatusType.EXCEPTION.getMessage());
		}
	}

}
