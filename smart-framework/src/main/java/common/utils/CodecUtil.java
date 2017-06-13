package common.utils;

import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ����������������
 * @author dell
 * @since 1.0.0
 */
public class CodecUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(CodecUtil.class);
	
	/**
	 * URL编码
	 */
	public static String encodeURL(String source){
		String target;
		try{
			target = URLEncoder.encode(source, "UTF-8");
		}catch(Exception e){
			LOGGER.error("encode url failure",e);
			throw new RuntimeException();
		}
		return target;
	}
	
	/**
	 * URL解码
	 */
	public static String decodeURL(String source){
		String target;
		try{
			target = URLDecoder.decode(source, "UTF-8");
		}catch(Exception e){
			LOGGER.error("decode url failure",e);
			throw new RuntimeException();
		}
		return target;
	}
	
	/**
	 * MD5加密
	 */
	public static String md5(String source){
		return DigestUtils.md5Hex(source);
	}
}
