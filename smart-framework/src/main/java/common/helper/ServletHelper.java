package common.helper;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.proxy.AspectProxy;

/**
 *  Servlet助手类--提供Servlet API的调用
 * @author cs
 * @since 1.0.0
 */
public final class ServletHelper {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServletHelper.class);
	
	/**
	 * 每个线程拥有一份ServletHelper实例
	 */
	private static final ThreadLocal<ServletHelper> SERVLET_HELPER_HOLDER = 
			new ThreadLocal<ServletHelper>();
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public ServletHelper(HttpServletRequest request,
			HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}
	
	/**
	 * 初始化
	 */
	public static void init(HttpServletRequest request,HttpServletResponse response){
		SERVLET_HELPER_HOLDER.set(new ServletHelper(request, response));
	}
	
	/**
	 * 销毁
	 */
	public static void destroy(){
		SERVLET_HELPER_HOLDER.remove();
	}
	
	/**
	 * 获取Request对象
	 */
	private static HttpServletRequest getRequest(){
		return SERVLET_HELPER_HOLDER.get().request;
	}
	
	/**
	 * 获取Response对象
	 */
	private static HttpServletResponse getResponse(){
		return SERVLET_HELPER_HOLDER.get().response;
	}
	
	/**
	 * 获取Session对象
	 */
	private static HttpSession getSession(){
		return getRequest().getSession();
	}
	
	/**
	 * 获取ServletContext对象
	 */
	private static ServletContext getServletContext(){
		return getRequest().getServletContext();
	}
	
	/**
	 * 将属性放入Request中
	 */
	public static void setRequestAttribute(String key,Object value){
		getRequest().setAttribute(key, value);
	}
	
	/**
	 * 从Request中获取属性
	 */
	public static <T> T getRequestAttribute(String key){
		return (T)getRequest().getAttribute(key);
	}
	
	/**
	 * 从Request中移除属性
	 */
	public static void remoteRequestAttribute(String key){
		getRequest().removeAttribute(key);
	}
	
	/**
	 * 发送重定向响应
	 */
	public static void sendRedirect(String location){
		try {
			getResponse().sendRedirect(getRequest().getContextPath()+location);
		} catch (IOException e) {
			LOGGER.error("redirect failure",e);
		}
	}
	
	/**
	 * 将属性放入Session中
	 */
	public static void setSessionAttribute(String key,Object value){
		getSession().setAttribute(key, value);
	}
	
	/**
	 * 从session中获取属性
	 */
	public static <T> T getSessionAttribute(String key){
		return (T) getRequest().getSession().getAttribute(key);
	}
	
	/**
	 * 从Session中移除属性
	 */
	public static void removeSessionAttribute(String key){
		getRequest().getSession().removeAttribute(key);
	}
	
	/**
	 * 是session失效
	 */
	public static void invalidateSession(){
		getRequest().getSession().invalidate();
	}
}
