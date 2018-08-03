package cn.edu.uestc.smgt.aspect;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.edu.uestc.smgt.annotation.SysControllerLogAnnotation;
import cn.edu.uestc.smgt.common.Constants;
import cn.edu.uestc.smgt.dao.HelpLogMapper;
import cn.edu.uestc.smgt.pojo.HelpLog;
import cn.edu.uestc.smgt.pojo.User;

@Component
@Aspect
public class SystemLogAspect {

	@Autowired
	private HelpLogMapper helpLogMapper;

	@Pointcut("@annotation(cn.edu.uestc.smgt.annotation.SysControllerLogAnnotation)")
	public void controllerAspect() {
	}

	@After("controllerAspect()")
	public void doAfter(JoinPoint joinPoint) {
		// 获取方法的第一个参数,是request
		HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[0];
		HttpSession session = request.getSession();

		try {
			// 从服务器session中获取用户
			User user = (User) session.getAttribute(Constants.CURRENT_USER);
			// 从shiro自带的session中获取用户
			Subject currentUser = SecurityUtils.getSubject();
			if (null != currentUser) {
				Session shiroSession = currentUser.getSession();
				if (null != shiroSession) {
					user = (User) shiroSession
							.getAttribute(Constants.CURRENT_USER);
				}
			}
			String ipAddr = getIpAddr(request);// ip地址
			String method = joinPoint.getTarget().getClass().getName() + "."
					+ joinPoint.getSignature().getName() + "()";
			// 调用getControllerMethodDesc()方法获取注解中的desc字符串
			String description = getControllerMethodDesc(joinPoint);
			HelpLog log = new HelpLog();// 添加一条日志记录
			// …省略set slog值的代码，结束后，调用service层方法添加
			log.setLogDate(new Date());
			log.setLogDesc(description);
			if (user != null) {
				log.setLoginName(user.getLoginName());
				log.setUserRole(user.getRole().toString());
				log.setUserDept(user.getDeptId());
			}
			log.setLogIp(ipAddr);
			log.setLogMethod(method);
			log.setLogUrl(null);
			helpLogMapper.insertSelective(log);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取注解中对方法的描述信息 用于Controller层注解
	 * 
	 * @param joinPoint
	 *            切点
	 * @return 方法描述
	 * @throws Exception
	 */
	public static String getControllerMethodDesc(JoinPoint joinPoint)
			throws Exception {
		// 获取切点对应的类全名称
		String clazzName = joinPoint.getTarget().getClass().getName();
		// 获取切点对应的方法名称
		String methodName = joinPoint.getSignature().getName();
		// 获取切点对应方法的参数
		Object[] arguments = joinPoint.getArgs();
		// 获取Class对象
		Class targetClass = Class.forName(clazzName);
		// 所有的方法对象
		Method[] methods = targetClass.getMethods();
		String desc = "";
		// 遍历所有方法对象，获取目标方法上的注解@SysControllerLogAnnotation的desc的内容
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == arguments.length) {
					desc = method.getAnnotation(
							SysControllerLogAnnotation.class).desc();
					break;
				}
			}
		}
		return desc;
	}

	// 获得客户端真实IP地址的方法：
	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

}
