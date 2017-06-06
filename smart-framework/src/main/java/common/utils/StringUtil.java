package common.utils;

import org.apache.commons.lang3.StringUtils;


/**
 * ×Ö·û´®¹¤¾ßÀà
 */
public class StringUtil {
	/**
	 * ÅÐ¶Ï×Ö·û´®ÊÇ·ñÎª¿Õ
	 */
	public static boolean isEmpty(String str){
		if(str != null){
			str=str.trim();
		}
		return StringUtils.isEmpty(str);
	}
	
	/**
	 * ÅÐ¶Ï×Ö·û´®ÊÇ·ñ·Ç¿Õ
	 */
	public static boolean isNotEmpty(String str){
		return !isEmpty(str);
	}
	
	/**
     * ·Ö¸î¹Ì¶¨¸ñÊ½µÄ×Ö·û´®
     */
    public static String[] splitString(String str, String separator) {
        return StringUtils.splitByWholeSeparator(str, separator);
    }
}
