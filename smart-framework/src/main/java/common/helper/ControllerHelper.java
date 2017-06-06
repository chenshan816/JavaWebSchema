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
 * ��װ������Ϣ
 * @author cs
 *
 */
public class ControllerHelper {
	//�������Controllerע����࣬ͨ�������ȡ���������д���Actionע��ķ���
	//��ȡActionע���е�������ʽ��������ȡ���󷽷�������·������װһ���������Request�봦�����Handler
	//���Request��Handler����һ��ӳ���ϵ������ActionMap�����ṩһ���ɸ������󷽷���·����ȡ�������ķ���
	
	/**
	 * ���ڴ�������봦������ӳ���ϵ
	 */
	private static final Map<Request,Handler> ACTION_MAP = new HashMap<Request, Handler>();
	
	static{
		//��ȡ����Controller��
		Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
		if(CollectionUtil.isNotEmpty(controllerClassSet)){
			//������ЩController��
			for(Class<?> controllerClass:controllerClassSet){
				//��ȡController���ж���ķ���
				Method[] methods = controllerClass.getDeclaredMethods();
				if(ArrayUtil.isNotEmpty(methods)){
					//������ЩController���еķ���
					for(Method method:methods){
						if(method.isAnnotationPresent(Action.class)){
							//��Actionע���л�ȡURLӳ�����
							Action action = method.getAnnotation(Action.class);
							String mapping = action.value();
							//��֤URLӳ�����
							if(mapping.matches("\\w+:/\\w*")){
								String[] array = mapping.split(":");
								if(ArrayUtil.isNotEmpty(array) && array.length == 2){
									//��ȡ���󷽷���·��
									String requestMethod = array[0];
									String requestPath = array[1];
									Request request = new Request(requestMethod, requestPath);
									Handler handler = new Handler(controllerClass, method);
									//��ʼ��ActionMap
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
	 * ��ȡhandler
	 */
	public static Handler getHandler(String requestMethod,String requestPath){
		Request request = new Request(requestMethod,requestPath);
		return ACTION_MAP.get(request);
	}
}
