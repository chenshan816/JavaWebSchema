package common.helper;

import java.util.Properties;

import common.constants.ConfigConstant;
import common.utils.PropsUtil;

/**
 * �����ļ�������
 * @author cs
 * @since 1.0.0
 *
 */
public final class ConfigHelper {
	
	private static final Properties CONFIG_PROPS = PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);
	
	/**
	 * JDBC驱动
	 */
	public static String getJdbcDriver(){
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_DRIVER);
	}
	
	/**
	 * JDBC URL
	 */
	public static String getJdbcUrl(){
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_URL);
	}
	
	/**
	 * JDBC用户姓名
	 */
	public static String getJdbcUserName(){
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_USERNAME);
	}
	/**
	 * JDBC用户密码
	 */
	public static String getJdbcPassword(){
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_PASSWORD);
	}
	/**
	 * App基础包名
	 */
	public static String getAppBasePackage(){
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_BASE_PACKAGE);
	}
	/**
	 * JSP的基础地址
	 */
	public static String getAppJspPath(){
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_JSP_PATH,"/WEB-INF/view/");
	}
	/**
	 * CSS、JS等包的基础地址
	 */
	public static String getAppAssetPath(){
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_ASSET_PATH,"/WEB-INF/asset/");
	}
	
	/**
	 * 上传文件的大小限制
	 */
	public static int getAppUploadLimit(){
		return PropsUtil.getInt(CONFIG_PROPS, ConfigConstant.APP_UPLOAD_LIMIT,10);
	}
	
	/**
	 * 获取字符串对象
	 */
	public static String getString(String key){
		return PropsUtil.getString(CONFIG_PROPS, key);
	}
	
	/**
	 * 获取布尔对象
	 */
	public static boolean getBoolean(String key){
		return PropsUtil.getBoolean(CONFIG_PROPS, key);
	}
}
