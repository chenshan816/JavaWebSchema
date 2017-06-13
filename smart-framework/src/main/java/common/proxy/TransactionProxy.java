package common.proxy;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.helper.DatabaseHelper;
import annotation.Transaction;

public class TransactionProxy implements Proxy{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionProxy.class);
	//是否开启事务
	//保证同一线程中事务控制相关逻辑只会执行一次
	private static final ThreadLocal<Boolean> FLAG_HOLDER = new ThreadLocal<Boolean>(){
		protected Boolean initialValue() {
			return false;
		};
	};
	
	@Override
	public Object doProxy(ProxyChain proxyChain) throws Throwable {
		Object result;
		boolean flag = FLAG_HOLDER.get();
		Method method = proxyChain.getTargetMethod();
		if(!flag && method.isAnnotationPresent(Transaction.class)){
			FLAG_HOLDER.set(true);
			try {
				DatabaseHelper.beginTransaction();
				LOGGER.debug("begin tranaction");
				result = proxyChain.doProxyChain();
				DatabaseHelper.commitTransaction();
				LOGGER.debug("commit tranaction");
			} catch (Exception e) {
				DatabaseHelper.rollbackTransaction();
				LOGGER.debug("rollback tranaction");
				throw new RuntimeException();
			}
		}else{
			result = proxyChain.doProxyChain();
		}
		return result;
	}

}
