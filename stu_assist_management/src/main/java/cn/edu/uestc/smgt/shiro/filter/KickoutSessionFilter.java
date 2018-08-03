package cn.edu.uestc.smgt.shiro.filter;

import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.edu.uestc.smgt.common.Constants;

public class KickoutSessionFilter extends AccessControlFilter{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private boolean kickoutAfter = false; //踢出之前登录的/之后登录的用户 默认踢出之前登录的用户
	private int maxSession = 1; //同一个帐号最大会话数 默认1

	private SessionManager sessionManager;
	private Cache<String, Deque<Serializable>> cache;
	public void setKickoutAfter(boolean kickoutAfter) {
		this.kickoutAfter = kickoutAfter;
	}

	public void setMaxSession(int maxSession) {
		this.maxSession = maxSession;
	}

	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	public void setCacheManager(CacheManager cacheManager) {
		this.cache = cacheManager.getCache("shiro-kickout-session");
	}
	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
        Subject subject = getSubject(request, response);
        if(subject.isAuthenticated() || !subject.isRemembered())
        	return true;
        Session session = subject.getSession();
        Serializable sessionId = session.getId();
        Boolean marker = (Boolean)session.getAttribute(Constants.KICKOUT_SESSION);
        logger.info("marker={}",marker);
        //如果marker = true,标记被踢出
        if( marker != null && marker){
        	return false;
        }
        String username = (String) subject.getPrincipal();
        Deque<Serializable> deque = cache.get(username);
        if(deque == null){
        	deque = new LinkedList<Serializable>();
        	cache.put(username, deque);
        }
        if(!deque.contains(sessionId) && marker == null){
        	deque.push(sessionId);
        }
        while(deque.size() > maxSession){
        	Serializable kickoutSessionId = null;
			if(kickoutAfter) { //如果踢出后者
				kickoutSessionId = deque.removeFirst();
			} else { //否则踢出前者
				kickoutSessionId = deque.removeLast();
			}
			try {
				Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
				if(kickoutSession != null) {
					//设置会话的kickout属性表示踢出了
					kickoutSession.setAttribute(Constants.KICKOUT_SESSION, true);
				}
			} catch (Exception e) {//ignore exception
				logger.error("",e);
			}
        }
        
		return true;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		Subject subject = getSubject(request, response);
		subject.logout();
		WebUtils.getSavedRequest(request);
		return false;
	}

}
