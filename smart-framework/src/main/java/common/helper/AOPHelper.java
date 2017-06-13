package common.helper;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.proxy.AspectProxy;
import common.proxy.Proxy;
import common.proxy.ProxyManager;
import common.proxy.TransactionProxy;
import annotation.Aspect;
import annotation.Service;
import annotation.Transaction;

/**
 * 
 * @author cs
 * @since 1.0.0
 */
public final class AOPHelper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AspectProxy.class);
	
	static {
		try{
			Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
			Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
			for(Map.Entry<Class<?>, List<Proxy>> targetEntry:targetMap.entrySet()){
				Class<?> targetClass = targetEntry.getKey();
				List<Proxy> proxyList = targetEntry.getValue();
				//代理类
				Object proxy = ProxyManager.createProxy(targetClass, proxyList);
				//将目标类和代理类放入框架容器中
				BeanHelper.setBean(targetClass, proxy);
			}
		}catch(Exception e){
			LOGGER.error("aop failure",e);
		}
	}
	
	
	//获得带有Aspect的所有类
	private static Set<Class<?>> createTargetClassSet(Aspect aspect) throws Exception{
		Set<Class<?>> targetClassSet = new HashSet<Class<?>>();
		Class<? extends Annotation> annotation = aspect.value();
		if(annotation != null && !annotation.equals(Aspect.class)){
			targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
		}
		return targetClassSet;
	}
	
	//获取代理类和目标类集合之间的映射关系
	//代理类有Aspect的注解
	private static Map<Class<?>,Set<Class<?>>> createProxyMap() throws Exception{
		Map<Class<?>,Set<Class<?>>> proxyMap = new HashMap<Class<?>, Set<Class<?>>>();
		addAspectProxy(proxyMap);
		addTransactionProxy(proxyMap);
		return proxyMap;
	}
	
	private static void addTransactionProxy(
			Map<Class<?>, Set<Class<?>>> proxyMap) {
		//限定对象为Service层
		Set<Class<?>> serviceClassSet = ClassHelper.getClassSetBySuper(Service.class);
		proxyMap.put(TransactionProxy.class, serviceClassSet);
	}

	private static void addAspectProxy(Map<Class<?>,Set<Class<?>>> proxyMap) throws Exception{
		//获取所有代理类
		Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
		
		for(Class<?> proxyClass:proxyClassSet){
			//注解指定的类型
			if(proxyClass.isAnnotationPresent(Aspect.class)){
				//获取proxyClass的Aspect的标签
				Aspect aspect = proxyClass.getAnnotation(Aspect.class);
				Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
				proxyMap.put(proxyClass, targetClassSet);
			}
		}
	}
	
	//目标类和代理列表之间的映射关系
	private static Map<Class<?>,List<Proxy>> createTargetMap(Map<Class<?>,Set<Class<?>>> proxyMap) throws Exception{
		Map<Class<?>,List<Proxy>> targetMap = new HashMap<Class<?>, List<Proxy>>();
		for(Map.Entry<Class<?>, Set<Class<?>>> proxyEntry:proxyMap.entrySet()){
			//代理类 
			Class<?> proxyClass = proxyEntry.getKey();
			Set<Class<?>> targetClassSet = proxyEntry.getValue();
			for(Class<?> targetClass : targetClassSet){
				Proxy proxy = (Proxy) proxyClass.newInstance();
				if(targetMap.containsKey(targetClass)){
					targetMap.get(targetClass).add(proxy);
				}else{
					List<Proxy> proxyList = new ArrayList<Proxy>();
					proxyList.add(proxy);
					targetMap.put(targetClass, proxyList);
				}
			}
		}
		return targetMap;
	}
}
