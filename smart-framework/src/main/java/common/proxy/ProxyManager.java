package common.proxy;

import java.lang.reflect.Method;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 创建代理对象
 * @author cs
 * @since 1.0.0
 */
public class ProxyManager {
	
	@SuppressWarnings("unchecked")
	public static <T> T createProxy(final Class<?> targetClass,final List<Proxy> proxyList){
		//为目标类创建子类
		return (T)Enhancer.create(targetClass, new MethodInterceptor() {
			@Override
			public Object intercept(Object targetObject, Method targetMethod, Object[] methodParams,
					MethodProxy methodProxy) throws Throwable {
				//AOP链
				return new ProxyChain(targetClass, targetObject, targetMethod,
						methodProxy, methodParams, proxyList).doProxyChain();
			}
		});
	}
}
