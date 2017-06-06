package common.helper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import annotation.Action;
import common.bean.Handler;
import common.bean.Request;
import common.utils.ArrayUtil;
import common.utils.CollectionUtil;

/**
 * 封装请求信息
 * @author cs
 *
 */
public class ControllerHelper {
	//获得所有Controller注解的类，通过反射获取该类中所有带有Action注解的方法
	//获取Action注解中的请求表达式，进而获取请求方法与请求路径，封装一个请求对象Request与处理对象Handler
	//最后将Request与Handler建立一个映射关系，放入ActionMap，并提供一个可根据请求方法与路径获取处理对象的方法
	
	/**
	 * 用于存放请求与处理器的映射关系
	 */
	private static final Map<Request,Handler> ACTION_MAP = new HashMap<Request, Handler>();
	
	static{
		//获取所有Controller类
		Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
		if(CollectionUtil.isNotEmpty(controllerClassSet)){
			//遍历这些Controller类
			for(Class<?> controllerClass:controllerClassSet){
				//获取Controller类中定义的方法
				Method[] methods = controllerClass.getDeclaredMethods();
				if(ArrayUtil.isNotEmpty(methods)){
					//遍历这些Controller类中的方法
					for(Method method:methods){
						if(method.isAnnotationPresent(Action.class)){
							//从Action注解中获取URL映射规则
							Action action = method.getAnnotation(Action.class);
							String mapping = action.value();
							//验证URL映射规则
							if(mapping.matches("\\w+:/\\w*")){
								String[] array = mapping.split(":");
								if(ArrayUtil.isNotEmpty(array) && array.length == 2){
									//获取请求方法与路径
									String requestMethod = array[0];
									String requestPath = array[1];
									Request request = new Request(requestMethod, requestPath);
									Handler handler = new Handler(controllerClass, method);
									//初始化ActionMap
									ACTION_MAP.put(request, handler);
								}
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * 获取handler
	 */
	public static Handler getHandler(String requestMethod,String requestPath){
		Request request = new Request(requestMethod,requestPath);
		return ACTION_MAP.get(request);
	}
}
