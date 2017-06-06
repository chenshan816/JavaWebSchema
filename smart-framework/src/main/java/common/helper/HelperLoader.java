package common.helper;

import common.utils.ClassUtil;

/**
 * ͨ��һ����ڳ�������ĸ�Helper��ľ�̬��
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
