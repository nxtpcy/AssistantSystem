package cn.edu.uestc.smgt.controller.common;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.uestc.smgt.common.Response;
import cn.edu.uestc.smgt.common.Status;
import cn.edu.uestc.smgt.dao.DeptMapper;
import cn.edu.uestc.smgt.dao.UserMapper;
import cn.edu.uestc.smgt.pojo.Dept;
import cn.edu.uestc.smgt.pojo.User;

@Controller
@RequestMapping("/usr/")
public class UserController {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private DeptMapper deptMapper;

	/**
	 * 如果role=0；显示超级管理员 如果role=1；查找deptId对应的单位名称+管理员，连带返回
	 * 
	 * @return
	 * @author ljd
	 */
	@RequestMapping("info")
	@ResponseBody
	public Object getUserInfo(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		User curUser = (User) session.getAttribute("user");
		Byte role = curUser.getRole();
		HashMap userInfo = new HashMap();
		userInfo.put("loginName", curUser.getLoginName());
		userInfo.put("role", role);
		if (role == 0) {
			// 返回login_name，和超级管理员
			userInfo.put("authority", "超级管理员");
		} else {
			userInfo.put("authority", "管理员");
			Dept dept = deptMapper.selectByDeptId(curUser.getDeptId());
			userInfo.put("deptName", dept.getDeptName());
			userInfo.put("deptId", dept.getDeptId());
		}
		return new Response(Status.SUCCESS, userInfo);
	}

	/**
	 * 修改用户密码
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @author ljd
	 */
	@RequestMapping("changepwd")
	@ResponseBody
	public Object changeUserPwd(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			@RequestParam HashMap map) {
		String loginName = (String) map.get("username");
		String oldPwd = (String) map.get("password");
		String newPwd = (String) map.get("newPwd");
		User curUser = (User) session.getAttribute("user");
		if (!curUser.getLoginName().equals(loginName)) {
			return new Response(Status.ERROR, "非本用户");
		}
		if (!curUser.getPwd().equals(oldPwd)) {
			return new Response(Status.ERROR, "原密码错误");
		}
		User user = new User();
		user.setLoginName(loginName);
		user.setPwd(newPwd);
		int ans = userMapper.updatePwdByLoginName(user);
		if (ans > 0) {
			return new Response(Status.SUCCESS, "修改成功");
		}
		return new Response(Status.ERROR, "修改失败");
	}
}
