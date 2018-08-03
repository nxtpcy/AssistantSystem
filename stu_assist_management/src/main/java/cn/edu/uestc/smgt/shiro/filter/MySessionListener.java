package cn.edu.uestc.smgt.shiro.filter;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.edu.uestc.smgt.common.Constants;

public class MySessionListener implements SessionListener{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	public void onStart(Session session) {
		logger.info("onStart session created:id="+session.getId() + " begin at "+ session.getStartTimestamp());
	}

	public void onStop(Session session) {
		logger.info("onStop session created:id="+session.getId() + " begin at "+ session.getStartTimestamp());
		logger.info("{}",session.getAttribute(Constants.CURRENT_USER));
	}

	public void onExpiration(Session session) {
		logger.info("onExpiration session created:id="+session.getId() + " begin at "+ session.getStartTimestamp());
		logger.info("{}",session.getAttribute(Constants.CURRENT_USER));
	}

}
