package org.smart4j.chapter1.Security;

import java.util.Set;

import common.helper.DatabaseHelper;

import plugin.security.SmartSecurity;

/**
 * Ӧ�ð�ȫ����
 * @author cs
 *
 */
public class AppSecurity implements SmartSecurity{

	public String getPassword(String username) {
		String sql = "select password from user where username=? ";
		return DatabaseHelper.query(sql, username);
	}

	public Set<String> getRoleNameSet(String username) {
		String sql = "select r.role_name from user u , user_role ur, "
				+ "role r where u.id=ur.user_id and r.id = ur.role_id and u.username=?";
		return DatabaseHelper.querySet(sql, username);
	}

	public Set<String> getPermissionNameSet(String roleName) {
		String sql = "select p.permission_name from role r,role_permission rp, permission p "
				+ "where r.id=rp.role_id and p.id=rp.permission_id and r.role_name=?";
		return DatabaseHelper.querySet(sql, roleName);
	}
	
}
