package cn.edu.uestc.smgt.shiro.filter;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.web.session.mgt.WebSessionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.edu.uestc.smgt.shiro.entity.OnlineSession;

public class OnlineSessionFactory implements SessionFactory {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	public Session createSession(SessionContext initData) {
		OnlineSession session = new OnlineSession();  
		//	        if (initData != null && initData instanceof WebSessionContext) {  
		//	            WebSessionContext sessionContext = (WebSessionContext) initData;  
		//	            HttpServletRequest request = (HttpServletRequest) sessionContext.getServletRequest();  
		//	        }  
		logger.info("OnlineSessionFactory.createSession={}",new Object[]{session.getStatus()});
		return session;  
	}

}
