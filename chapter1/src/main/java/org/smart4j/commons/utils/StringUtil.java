package org.smart4j.commons.utils;

import org.apache.commons.lang3.StringUtils;


/**
 * �ַ���������
 */
public class StringUtil {
	/**
	 * �ж��ַ����Ƿ�Ϊ��
	 */
	public static boolean isEmpty(String str){
		if(str != null){
			str=str.trim();
		}
		return StringUtils.isEmpty(str);
	}
	
	/**
	 * �ж��ַ����Ƿ�ǿ�
	 */
	public static boolean isNotEmpty(String str){
		return !isEmpty(str);
	}
}
