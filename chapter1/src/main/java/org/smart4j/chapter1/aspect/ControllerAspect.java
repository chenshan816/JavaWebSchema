package org.smart4j.chapter1.aspect;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import annotation.Aspect;
import annotation.Controller;
import common.proxy.AspectProxy;

@Aspect(Controller.class)
public class ControllerAspect extends AspectProxy {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAspect.class);
	
	private long begin;
	
	@Override
	public void before(Class<?> cls, Method method, Object[] params) {
		LOGGER.debug("--------begin---------");
		LOGGER.debug(String.format("class: %s", cls.getName()));
		LOGGER.debug(String.format("class: %s", method.getName()));
		
		begin = System.currentTimeMillis();
	}
	
	@Override
	public void after(Class<?> cls, Method method, Object[] params,
			Object result) {
		LOGGER.debug(String.format("time: %dms", System.currentTimeMillis()-begin));
		LOGGER.debug("--------end---------");
	}
}
