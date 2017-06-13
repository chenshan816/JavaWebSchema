package org.smart4j.plugin.security.realm;

import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.smart4j.plugin.security.SecurityConfig;
import org.smart4j.plugin.security.password.Md5Credentialsatcher;

import common.helper.DatabaseHelper;

/**
 * 基于Smart的JDBC Realm（需要提供smart.plugin.security.jdbc.*配置项）
 * @author cs
 *
 */
public class SmartJdbcRealm extends JdbcRealm{

	public SmartJdbcRealm() {
		super.setDataSource(DatabaseHelper.getDataSource());
		super.setAuthenticationQuery(SecurityConfig.getJdbcAuthcQuery());
		super.setUserRolesQuery(SecurityConfig.getJdbcRolesQuery());
		super.setPermissionsQuery(SecurityConfig.getJdbcPermissionsQuery());
		super.setPermissionsLookupEnabled(true);
		super.setCredentialsMatcher(new Md5Credentialsatcher());//使用md5对密码进行加密
	}

	

}
