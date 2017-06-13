package common.utils;

import org.apache.commons.lang3.StringUtils;


/**
 * 帮助类
 */
public final class StringUtil {
	public static final String SEPARATOR = String.valueOf((char) 29);
	/**
	 * 是否空
	 */
	public static boolean isEmpty(String str){
		if(str != null){
			str=str.trim();
		}
		return StringUtils.isEmpty(str);
	}
	
	/**
	 * 是否不空
	 */
	public static boolean isNotEmpty(String str){
		return !isEmpty(str);
	}
	
	/**
     * 分隔
     */
    public static String[] splitString(String str, String separator) {
        return StringUtils.splitByWholeSeparator(str, separator);
    }
}
