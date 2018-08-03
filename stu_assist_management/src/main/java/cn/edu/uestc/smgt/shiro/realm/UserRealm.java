package cn.edu.uestc.smgt.shiro.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edu.uestc.smgt.common.Constants;
import cn.edu.uestc.smgt.pojo.User;
import cn.edu.uestc.smgt.service.UserService;

public class UserRealm extends AuthorizingRealm {
	@Autowired
	private UserService userService;
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		//		String username = (String)arg0.getPrimaryPrincipal();
		String username = (String)SecurityUtils.getSubject().getPrincipal();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		try{

			authorizationInfo.setRoles(userService.findRoles(username));
			authorizationInfo.setStringPermissions(userService.findPermissions(username));
		}catch(UnauthorizedException e){
			logger.error("UnauthorizedException error",e);
		}catch(AuthorizationException e){
			logger.error("AuthorizationException error",e);
		}
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken arg0) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken)arg0;  
		User user = userService.getUserByLoginName(token.getUsername());  
		if(user != null){
			SimpleAuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user.getLoginName(),user.getPwd(),"");
			this.setSession(Constants.CURRENT_USER,user);
			return authcInfo;
		}
		else{
			return null;
		}
	}

	private static final Logger logger = LoggerFactory.getLogger(UserRealm.class);
	public  static void main(String []args){
		User user = new User();
		user.setDeptId("007");
		try{
			int a = 9/0;
		}catch(Exception e){
			logger.info("Main Error,user={}",new Object[]{user},e);
		}
	}
	private void setSession(Object key, Object value){  
		Subject currentUser = SecurityUtils.getSubject();  
		if(null != currentUser){  
			Session session = currentUser.getSession();  
			if(null != session){  
				session.setAttribute(key, value); 
//				session.setTimeout(1000 * 10);
//				logger.info("Session超时时间为[" + session.getTimeout() + "]毫秒");  
			}  
		}  
	}  
	@Override
	public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
	}

	@Override
	public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
		super.clearCachedAuthenticationInfo(principals);
	}

	@Override
	public void clearCache(PrincipalCollection principals) {
		super.clearCache(principals);
	}

	public void clearAllCachedAuthorizationInfo() {
		getAuthorizationCache().clear();
	}

	public void clearAllCachedAuthenticationInfo() {
		getAuthenticationCache().clear();
	}

	public void clearAllCache() {
		clearAllCachedAuthenticationInfo();
		clearAllCachedAuthorizationInfo();
	}

}
