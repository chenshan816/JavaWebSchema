package org.smart4j.plugin.security;

import common.helper.ConfigHelper;
import common.utils.ReflectionUtil;

public class SecurityConfig {

	public static SmartSecurity getSmartSecurity() {
		String className = ConfigHelper.getString(SecurityConstant.SMART_SECURITY);
		return (SmartSecurity) ReflectionUtil.newInstance(className);
	}

	public static String getRealms() {
		return ConfigHelper.getString(SecurityConstant.REALMS);
	}

	public static boolean isCache() {
		return ConfigHelper.getBoolean(SecurityConstant.CACHEABLE);
	}

	public static String getJdbcAuthcQuery() {
		return ConfigHelper.getString(SecurityConstant.JDBC_AUTHC_QUERY);
	}

	public static String getJdbcRolesQuery() {
		return ConfigHelper.getString(SecurityConstant.JDBC_ROLES_QUERY);
	}

	public static String getJdbcPermissionsQuery() {
		return ConfigHelper.getString(SecurityConstant.JDBC_PERMISSIONS_QUERY);
	}

}
