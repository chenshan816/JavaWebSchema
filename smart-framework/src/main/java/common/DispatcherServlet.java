package common;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.bean.Data;
import common.bean.Handler;
import common.bean.Param;
import common.bean.View;
import common.helper.BeanHelper;
import common.helper.ConfigHelper;
import common.helper.ControllerHelper;
import common.helper.HelperLoader;
import common.utils.ArrayUtil;
import common.utils.CodecUtil;
import common.utils.JsonUtil;
import common.utils.ReflectionUtil;
import common.utils.StreamUtil;
import common.utils.StringUtil;

/**
 * 请求转发器
 * @author cs
 * @since 1.0.0
 */
@WebServlet(urlPatterns="/*",loadOnStartup =0)
public final class DispatcherServlet extends HttpServlet{
	
	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		//初始化相关helper类
		HelperLoader.init();
		//获取ServletContext对象
		ServletContext servletContext = servletConfig.getServletContext();
		
		registerServlet(servletContext);
	}
	
	private void registerServlet(ServletContext servletContext) {
		ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping("/index.jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");

        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping("/favicon.ico");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");

	}

	@Override
	public void service(HttpServletRequest req,HttpServletResponse res)
			throws ServletException, IOException {
		//获取请求方法与请求路径
		String requestMethod = req.getMethod().toLowerCase();
		String requestPath = req.getPathInfo();
		//获取Action处理器
		Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
		if(handler != null){
			//获取Controller类及其bean实例
			Class<?> controllerClass = handler.getControllerClass();
			Object controllerBean = BeanHelper.getBean(controllerClass);
			//创建请求参数
			Map<String,Object> paramMap = new HashMap<String,Object>();
			Enumeration<String> parameterNames = req.getParameterNames();
			while(parameterNames.hasMoreElements()){
				String paramName = parameterNames.nextElement();
				String paramValue = req.getParameter(paramName);
				paramMap.put(paramName, paramValue);
			}
			//获取url地址上的参数
			String body = CodecUtil.decodeURL(StreamUtil.getString(req.getInputStream()));
			if(StringUtil.isNotEmpty(body)){
				String[] params = StringUtil.splitString(body, "&");
				if(ArrayUtil.isNotEmpty(params)){
					for(String param : params){
						String[] array = StringUtil.splitString(param, "=");
						if(ArrayUtil.isNotEmpty(array)){
							String paramName = array[0];
							String paramValue = array[1];
							paramMap.put(paramName, paramValue);
						}
					}
				}
			}
			Param param = new  Param(paramMap);
			//调用Action方法
			Method actionMethod = handler.getActionMethod();
			Object result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
			//处理Action方法返回值
			//1.Action结果是视图
			if(result instanceof View){
				//返回JSP视图
				View view = (View) result;
				String path = view.getPath();
				if(StringUtil.isNotEmpty(path)){
					if(path.startsWith("/")){
						res.sendRedirect(req.getContextPath()+path);
					}else{
						Map<String, Object> model = view.getModel();
						for(Map.Entry<String, Object>entry:model.entrySet()){
							req.setAttribute(entry.getKey(), entry.getValue());
						}
						req.getRequestDispatcher(ConfigHelper.getAppJspPath()+path).forward(req, res);
					}
				}
			}else if(result instanceof Data){
				//返回JSON数据格式
				Data data = (Data) result;
				Object model = data.getModel();
				if(model != null){
					res.setContentType("application/json");
					res.setCharacterEncoding("UTF-8");
					PrintWriter writer = res.getWriter();
					String json = JsonUtil.toJson(model);
					writer.write(json);
					writer.flush();
					writer.close();
				}
			}
		}
	}
}
