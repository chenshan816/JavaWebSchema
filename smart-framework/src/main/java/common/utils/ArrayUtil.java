package common.utils;

import org.apache.commons.lang3.ArrayUtils;

/**
 * ���鹤����
 * @author cs
 *
 */
public final class ArrayUtil {
	
	/**
	 * �ж������Ƿ�ǿ�
	 */
	public static boolean isNotEmpty(Object[] array){
		return !ArrayUtils.isEmpty(array);
	}
	
	/**
	 * �ж������Ƿ�Ϊ��
	 */
	public static boolean isEmpty(Object[] array){
		return ArrayUtils.isEmpty(array);
	}
}
