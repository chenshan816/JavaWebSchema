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
 * 控制器的助手类
 * @author cs
 *
 */
public class ControllerHelper {
	
	/**
	 * 保存所有的请求和处理方法之前的映射关系
	 */
	private static final Map<Request,Handler> ACTION_MAP = new HashMap<Request, Handler>();
	
	static{
		//获取所有Controller类
		Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
		if(CollectionUtil.isNotEmpty(controllerClassSet)){
			//循环遍历Controller类
			for(Class<?> controllerClass:controllerClassSet){
				//获取Controller中所有的方法
				Method[] methods = controllerClass.getDeclaredMethods();
				if(ArrayUtil.isNotEmpty(methods)){
					//循环遍历Controller中的方法
					for(Method method:methods){
						if(method.isAnnotationPresent(Action.class)){
							//判断该方法是否为Action的处理方法
							Action action = method.getAnnotation(Action.class);
							String mapping = action.value();
							//保存URL和请求方法（put、post、get、delete等）
							if(mapping.matches("\\w+:/\\w*")){
								String[] array = mapping.split(":");
								if(ArrayUtil.isNotEmpty(array) && array.length == 2){
									String requestMethod = array[0];
									String requestPath = array[1];
									Request request = new Request(requestMethod, requestPath);
									Handler handler = new Handler(controllerClass, method);
									//保存在Map中
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
