package common.helper;

import common.utils.ClassUtil;

/**
 * 通过一个入口程序加载四个Helper类的静态块
 * @author cs
 * @since
 */
public final class HelperLoader {
	
	public static void init(){
		Class<?>[] classList={
				ClassHelper.class,
	            BeanHelper.class,
	            IOCHelper.class,
	            ControllerHelper.class
				};
		
		for(Class<?> cls : classList){
			ClassUtil.loadClass(cls.getName());
		}
	}
}
