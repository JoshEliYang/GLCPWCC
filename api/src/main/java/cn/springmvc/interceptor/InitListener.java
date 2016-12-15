package cn.springmvc.interceptor;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.ServletContextAware;

import cn.springmvc.mq.MqReceiver;

/**
 * 
 * @author johnson
 *
 */
public class InitListener implements InitializingBean, ServletContextAware {

	Logger logger = Logger.getLogger(InitListener.class);

	public void setServletContext(ServletContext arg0) {

	}

	/**
	 * start MQ receiver and let it listen the ActiveMq
	 */
	public void afterPropertiesSet() throws Exception {
		Thread thread = new Thread(new MqReceiver());
		thread.start();
		logger.error("mq listener start success");
	}

}
