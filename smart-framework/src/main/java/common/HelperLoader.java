package common;

import common.helper.AOPHelper;
import common.helper.BeanHelper;
import common.helper.ClassHelper;
import common.helper.ControllerHelper;
import common.helper.IOCHelper;
import common.utils.ClassUtil;

/**
 * ͨ��һ����ڳ�������ĸ�Helper��ľ�̬��
 * @author cs
 * @since
 */
public final class HelperLoader {
	
	public static void init(){
		//有顺序之分
		Class<?>[] classList={
				ClassHelper.class,
	            BeanHelper.class,
	            AOPHelper.class,
	            IOCHelper.class,
	            ControllerHelper.class
				};
		
		for(Class<?> cls : classList){
			ClassUtil.loadClass(cls.getName());
		}
	}
}
