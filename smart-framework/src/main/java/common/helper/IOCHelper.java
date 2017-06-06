package common.helper;

import java.lang.reflect.Field;
import java.util.Map;

import annotation.Inject;

import common.utils.ArrayUtil;
import common.utils.CollectionUtil;
import common.utils.ReflectionUtil;

/**
 * 依赖注入助手类
 * @author cs
 *
 */
public final class IOCHelper {
	
	static {
		Map<Class<?>,Object> beanMap = BeanHelper.getBeanMap();
		if(CollectionUtil.isNotEmpty(beanMap)){
			//循环遍历 Bean Map
			for(Map.Entry<Class<?>, Object> beanEntry:beanMap.entrySet()){
				//获取每个类的对象
				Class<?> beanClass = beanEntry.getKey();
				Object beanInstance = beanEntry.getValue();
				//获得这个类的每个参数
				Field[] beanFields = beanClass.getDeclaredFields();
				if(ArrayUtil.isNotEmpty(beanFields)){
					//Bean Field
					for(Field beanField:beanFields){
						//Bean Field中如果有注入标志则从beanMap中进行注入
						if(beanField.isAnnotationPresent(Inject.class)){
							Class<?> beanFieldClass = beanField.getType();
							Object beanFieldInstance = beanMap.get(beanFieldClass);
							if(beanFieldInstance != null){
								//通过反射进行创建
								ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
							}
						}
					}
				}
			}
		}
	}
}
