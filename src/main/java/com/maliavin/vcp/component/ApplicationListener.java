package com.maliavin.vcp.component;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationListener implements ServletContextListener {

	@Value("${production}")
	private boolean production;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().setAttribute("production", production);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
