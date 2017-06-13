package org.smart4j.plugin.security;

import java.util.Set;

import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.shiro.web.env.EnvironmentLoaderListener;

/**
 * Smart Security���
 * @author cs 
 * @since 1.0.0
 */
public class SmartSecurityPlugin implements ServletContainerInitializer{

	public void onStartup(Set<Class<?>> handlesTypes, ServletContext servletContext)
			throws ServletException {
		//���ó�ʼ������
		servletContext.setInitParameter("shiroConfigLocations", "classpath:smart-security.ini");
		//ע��Listener
		servletContext.addListener(EnvironmentLoaderListener.class);
		//ע��Filter
		Dynamic smartSecurityFilter = servletContext.addFilter("SmartSecurityFilter", SmartSecurityFilter.class);
		smartSecurityFilter.addMappingForUrlPatterns(null, false, "/*");
	}

}
