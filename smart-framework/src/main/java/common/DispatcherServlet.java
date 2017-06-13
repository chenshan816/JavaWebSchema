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
import common.helper.RequestHelper;
import common.helper.ServletHelper;
import common.helper.UploadHelper;
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
		
		//初始化文件上传
		UploadHelper.init(servletContext);
	}
	
	private void registerServlet(ServletContext servletContext) {
		ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping("/index.jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");

        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");

	}

	@Override
	public void service(HttpServletRequest req,HttpServletResponse res)
			throws ServletException, IOException {
		//初始化ServletHelper类
		ServletHelper.init(req,res);
		try {
			//获取请求方法与请求路径
			String requestMethod = req.getMethod().toLowerCase();
			String requestPath = req.getPathInfo();
			
			if(requestPath.equals("/favicon.ico"))
				return;
			//获取Action处理器
			Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
			if(handler != null){
				//获取Controller类及其bean实例
				Class<?> controllerClass = handler.getControllerClass();
				Object controllerBean = BeanHelper.getBean(controllerClass);
				//创建请求参数
				Param param;
				if(UploadHelper.isMultipart(req)){
					param = UploadHelper.createParam(req);
				}else{
					param = RequestHelper.createParam(req);
				}
				//调用Action方法
				Method actionMethod = handler.getActionMethod();
				Object result;
				if(param.isEmpty()){
					result = ReflectionUtil.invokeMethod(controllerBean, actionMethod);
				}else{
					result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
				}
				//处理Action方法返回值
				//1.Action结果是视图
				if(result instanceof View){
					handleViewResult((View) result,req,res);
				}else if(result instanceof Data){
					handleDateResult((Data) result,res);
				}
			}
		} finally {
			ServletHelper.destroy();
		}
	}
	
	private void handleViewResult(View view,HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException{
		//返回JSP视图
		String path = view.getPath();
		if(StringUtil.isNotEmpty(path)){
			if(path.startsWith("/")){
				response.sendRedirect(request.getContextPath()+path);
			}else{
				Map<String, Object> model = view.getModel();
				for(Map.Entry<String, Object>entry:model.entrySet()){
					request.setAttribute(entry.getKey(), entry.getValue());
				}
				request.getRequestDispatcher(ConfigHelper.getAppJspPath()+path).forward(request, response);
			}
		}
	}
	
	private void handleDateResult(Data data,HttpServletResponse response) throws IOException{
		//返回JSON数据格式
		Object model = data.getModel();
		if(model != null){
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter writer = response.getWriter();
			String json = JsonUtil.toJson(model);
			writer.write(json);
			writer.flush();
			writer.close();
		}
	}
}
