package common.bean;

import java.lang.reflect.Method;

/**
 * ��װAction��Ϣ
 * @author cs
 *
 */
public class Handler {
	
	/**
	 * Controller��
	 */
	private Class<?> controllerClass;
	/**
	 * Action����
	 */
	private Method actionMethod;
	
	public Handler(Class<?> controllerClass, Method actionMethod) {
		super();
		this.controllerClass = controllerClass;
		this.actionMethod = actionMethod;
	}
	public Class<?> getControllerClass() {
		return controllerClass;
	}
	public void setControllerClass(Class<?> controllerClass) {
		this.controllerClass = controllerClass;
	}
	public Method getActionMethod() {
		return actionMethod;
	}
	public void setActionMethod(Method actionMethod) {
		this.actionMethod = actionMethod;
	}
	
}
