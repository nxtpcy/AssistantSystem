package cn.edu.uestc.smgt.controller;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
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
import cn.edu.uestc.smgt.common.Constants;
import cn.edu.uestc.smgt.common.Response;
import cn.edu.uestc.smgt.common.StatusType;
import cn.edu.uestc.smgt.pojo.User;
import cn.edu.uestc.smgt.pojo.dto.PwdChangeDTO;
import cn.edu.uestc.smgt.service.UserService;
import cn.edu.uestc.smgt.shiro.entity.OnlineSession;
import cn.edu.uestc.smgt.utils.UserUtil;

@Controller
@RequestMapping("/usr/")
public class LoginController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private UserService userService;
	@Autowired
	private SessionDAO sessionDAO;

	/**
	 * 管理员或用户登录
	 * 
	 * @param map
	 * @param session
	 * @return
	 */
	@SysControllerLogAnnotation(desc = "登录系统")
	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	@ResponseBody
	public Object login(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			@RequestBody User user) {
		try {
			User getUser = userService.getUser(user);
			int status = StatusType.SUCCESS.getVal();
			if (getUser == null)
				status = StatusType.PASSWD_NOT_MATCH.getVal();
			else
				status = userService.checkRole(getUser, user);
			if (status == StatusType.SUCCESS.getVal()) {
				getUser.setPwd("");
				UserUtil.setCurrentUser(request, getUser);
			}
			String message = StatusType.value(status).getMessage();
			return new Response(status, message, getUser);
		} catch (Exception e) {
			logger.error("调用LoginController.login出错,user={},error={}",
					new Object[] { user, e });
			return new Response(StatusType.EXCEPTION.getVal(),
					StatusType.EXCEPTION.getMessage());
		}
	}

	// 退出登录
	@RequestMapping(value = "/user/logout", method = RequestMethod.GET)
	public Object logout(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			UserUtil.removeCurrentUser(request, response);
			int status = StatusType.SUCCESS.getVal();
			String message = StatusType.value(status).getMessage();
			return new Response(status, message);
		} catch (Exception e) {
			logger.error("调用LoginController.logout出错,error={}",
					new Object[] { e });
			return new Response(StatusType.EXCEPTION.getVal(),
					StatusType.EXCEPTION.getMessage());
		}
	}

	// @SysControllerLogAnnotation(desc="检测用户登录状态")
	@RequestMapping(value = "/user/getUserStatus", method = RequestMethod.GET)
	@ResponseBody
	public Object getUserStatus(ServletRequest request) {
		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser == null || currentUser.getSession(false) == null) {
			return new Response(StatusType.USER_IS_NULL.getVal(),
					StatusType.USER_IS_NULL.getMessage());
		}
		OnlineSession online = (OnlineSession) request
				.getAttribute(Constants.ONLINE_SESSION);
		if (online != null) {
			logger.info("online id={},status={}", online.getId(),
					online.getStatus());
		}
		OnlineSession kickout = (OnlineSession) request
				.getAttribute(Constants.KICKOUT_SESSION);
		if (request.getAttribute(Constants.KICKOUT_SESSION) != null) {
			logger.info("id={},status={}", kickout.getId(), kickout.getStatus());
			return new Response(StatusType.KICKOUT.getVal(),
					StatusType.KICKOUT.getMessage());
		}
		String username = (String) currentUser.getPrincipal();
		if (currentUser != null) {
			Session session = currentUser.getSession(false);
			// OnlineSession session = (OnlineSession)currentUser.getSession();
			if (session != null) {
				// if(session.getStatus() == OnlineStatus.KICKOUT)
				// return new
				// Response(StatusType.KICKOUT.getVal(),StatusType.KICKOUT.getMessage());
				User user = (User) session.getAttribute(Constants.CURRENT_USER);
				if (user == null)
					return new Response(StatusType.USER_IS_NULL.getVal(),
							StatusType.USER_IS_NULL.getMessage());
				else {
					User getUser = userService.getUserByLoginName(user
							.getLoginName());
					user.setPwd(null);
					return new Response(StatusType.SUCCESS.getVal(),
							StatusType.SUCCESS.getMessage(), getUser);
				}
			}
		}
		return new Response(StatusType.USER_IS_NULL.getVal(),
				StatusType.USER_IS_NULL.getMessage());
	}

	@SysControllerLogAnnotation(desc = "修改密码")
	@RequestMapping(value = "/user/changePwd", method = RequestMethod.POST)
	@ResponseBody
	public Object changePwd(HttpServletRequest request,
			@RequestBody PwdChangeDTO pwdChangeDTO) {
		try {
			int status = userService.changePwd(pwdChangeDTO);
			String message = StatusType.value(status).getMessage();
			return new Response(status, message);
		} catch (Exception e) {
			logger.error(
					"调用LoginController.changePwd出错,PwdChangeDTO={},error={}",
					new Object[] { pwdChangeDTO, e });
			return new Response(StatusType.EXCEPTION.getVal(),
					StatusType.EXCEPTION.getMessage());
		}
	}

	/**
	 * 管理员重置密码（权限：管理员）
	 * 
	 * @param request
	 * @param pwdChangeDTO
	 * @return
	 */
	@SysControllerLogAnnotation(desc = "管理员重置密码")
	@RequiresRoles("0")
	@RequestMapping(value = "/user/resetPwd", method = RequestMethod.POST)
	@ResponseBody
	public Object resetPwd(HttpServletRequest request,
			@RequestBody PwdChangeDTO pwdChangeDTO) {
		try {
			int status = userService.resetPwd(pwdChangeDTO);
			String message = StatusType.value(status).getMessage();
			return new Response(status, message);
		} catch (Exception e) {
			logger.error(
					"调用LoginController.resetPwd出错,PwdChangeDTO={},error={}",
					new Object[] { pwdChangeDTO, e });
			return new Response(StatusType.EXCEPTION.getVal(),
					StatusType.EXCEPTION.getMessage());
		}
	}

	@SysControllerLogAnnotation(desc = "登录")
	@RequestMapping(value = "/user/shiroLogin", method = RequestMethod.POST)
	@ResponseBody
	public Object shiroLogin(HttpServletRequest request,
			HttpServletResponse response, @RequestParam String loginName,
			@RequestParam String password, @RequestParam Byte role) {
		int status = StatusType.ERROR.getVal();

		if (!userService.checkRoleWhenLogin(loginName, role)) {
			// try {
			// //
			// response.sendRedirect("http://115.29.136.190/help/login.html");
			// } catch (IOException e) {
			// logger.error("角色出错");
			// }
			return new Response(StatusType.ROLE_INVALID.getVal(), StatusType
					.value(StatusType.ROLE_INVALID.getVal()).getMessage());
		}

		UsernamePasswordToken token = new UsernamePasswordToken(loginName,
				password);
		try {
			// token.setRememberMe(true);
			Subject currentUser = SecurityUtils.getSubject();
			currentUser.login(token);// 验证角色和权限
			if (currentUser.isAuthenticated()) {
				status = StatusType.SUCCESS.getVal();
				logger.info(loginName + " 登陆成功");

				// Cookie cookie = new Cookie("loginName",loginName);
				// cookie.setMaxAge(60*60*5); //cookie expires in 5 days
				// response.addCookie(cookie);
				status = StatusType.SUCCESS.getVal();
				return new Response(status, StatusType.value(status)
						.getMessage());
				// return new
				// Response(status,StatusType.value(status).getMessage(),uuid);
				// response.sendRedirect("http://115.29.136.190/help/");
			}

		} catch (UnknownAccountException e) {
			logger.error("对用户<{}.{}>进行登录验证..验证未通过,未知账户", new Object[] {
					loginName, password });
			token.clear();
			status = StatusType.USER_IS_NULL.getVal();
		} catch (IncorrectCredentialsException e) {
			logger.error("对用户<{}.{}>进行登录验证..验证未通过,错误的凭证", new Object[] {
					loginName, password });
			token.clear();
			status = StatusType.PASSWD_NOT_MATCH.getVal();
		} catch (AuthenticationException e) {
			logger.error("对用户<{}.{}>验证错误",
					new Object[] { loginName, password }, e);
			status = StatusType.UNAUTHORIZED.getVal();
			token.clear();
		}
		// catch (IOException e) {
		// logger.error("跳转出错");
		// }

		return new Response(status, StatusType.value(status).getMessage());
	}

	// @SysControllerLogAnnotation(desc="退出")
	@RequestMapping(value = "/user/shiroLogout", method = RequestMethod.POST)
	@ResponseBody
	public Object logout(HttpServletRequest request) {
		int status = StatusType.SUCCESS.getVal();
		User currentUser = (User) request.getSession().getAttribute(
				Constants.CURRENT_USER);
		try {
			Subject subject = SecurityUtils.getSubject();
			subject.logout();
			if (currentUser != null) {
				logger.info(currentUser.getLoginName() + " logout success");
			}
			return new Response(status, StatusType.value(status).getMessage());
		} catch (Exception e) {
			logger.error("{} logout failed", currentUser.getLoginName(), e);
			status = StatusType.ERROR.getVal();
			return new Response(status, StatusType.value(status).getMessage());
		}
	}

}
