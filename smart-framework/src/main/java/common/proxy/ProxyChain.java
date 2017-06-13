package common.proxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.proxy.MethodProxy;

/**
 * 代理链式结构
 * @author cs
 * @since 1.0.0
 */
public class ProxyChain {
	
	private Class<?> targetClass; //目标类
	private Object targetObject; //目标对象
	private Method targetMethod; //目标方法
	private MethodProxy methodProxy; //方法代理
	private Object[] methodParams; //方法参数
	private List<Proxy> proxyList = new ArrayList<Proxy>(); //代理列表
	private int proxyIndex = 0; //代理索引
	
	public ProxyChain(Class<?> targetClass,Object targetObject,
			Method targetMethod,MethodProxy methodProxy,Object[] methodParams,
			List<Proxy> proxyList){
		this.targetClass = targetClass;
		this.targetObject = targetObject;
		this.targetMethod = targetMethod;
		this.methodProxy = methodProxy;
		this.methodParams = methodParams;
		this.proxyList = proxyList;
	}
	 
	public Object doProxyChain() throws Throwable{
		Object methodResult;
		if(proxyIndex < proxyList.size()){
			methodResult = proxyList.get(proxyIndex++).doProxy(this);
		}else{
			methodResult = methodProxy.invokeSuper(targetObject, methodParams);
		}
		return methodResult;
	}

	public Class<?> getTargetClass() {
		return targetClass;
	}

	public Method getTargetMethod() {
		return targetMethod;
	}

	public Object[] getMethodParams() {
		return methodParams;
	}
	
	
}
