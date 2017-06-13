package common.proxy;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 切面代理
 * @author cs
 * @since 1.0.0
 */
public class AspectProxy implements Proxy{
	//出现一系列类似于钩子方法，子类进行选择性的实现
	//钩子方法：由子类的一个方法返回值决定公共部分的执行结果
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AspectProxy.class);

	@Override
	public final Object doProxy(ProxyChain proxyChain) throws Throwable {
		Object result =null;
		
		Class<?> cls = proxyChain.getTargetClass();
		Method method = proxyChain.getTargetMethod();
		Object[] params = proxyChain.getMethodParams();
		
		begin();
		try{
			if(intercept(cls,method,params)){
				before(cls,method,params);
				result = proxyChain.doProxyChain();
				after(cls,method,params,result);
			}
		}catch(Exception e){
			LOGGER.error("proxy failure",e);
			error(cls,method,params,e);
		}finally{
			end();
		}
		return result;
	}


	public void begin() {
		
	}
	
	public boolean intercept(Class<?> cls, Method method, Object[] params) {
		return true;
	}
	
	public void before(Class<?> cls, Method method, Object[] params) {
		
	}
	
	public void error(Class<?> cls, Method method, Object[] params, Exception e) {
		
	}
	
	public void after(Class<?> cls, Method method, Object[] params,
			Object result) {
	}

	public void end() {
		
	}

	
	
	
}
