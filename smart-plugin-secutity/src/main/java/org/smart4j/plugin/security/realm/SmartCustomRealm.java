package org.smart4j.plugin.security.realm;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.smart4j.plugin.security.SecurityConstant;
import org.smart4j.plugin.security.SmartSecurity;
import org.smart4j.plugin.security.password.Md5Credentialsatcher;



/**
 * 基于Smart的自定义Realm（实现SmartSecurity接口）
 * @author dell
 *
 */
public class SmartCustomRealm  extends AuthorizingRealm{

	private final SmartSecurity smartSecurity;
	
	public SmartCustomRealm(SmartSecurity smartSecurity) {
		this.smartSecurity = smartSecurity;
		super.setName(SecurityConstant.REALMS_CUSTOM);
		super.setCredentialsMatcher(new Md5Credentialsatcher());
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		if(token == null){
			throw new AuthenticationException("paramter token is null");
		}
		
		//通过AuthenticationToken对象获取从表单中提交过来的用户名
		String username = ((UsernamePasswordToken)token).getUsername();
		String password = smartSecurity.getPassword(username);
		//将用户名和密码放入AuthenticationInfo对象中，便于后续的认证操作
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo();
		authenticationInfo.setPrincipals(new SimplePrincipalCollection(username, super.getName()));
		authenticationInfo.setCredentials(password);
		return authenticationInfo;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		if(principals == null){
			throw new AuthorizationException("parameter principals is null");
		}
		
		//获取已认证用户的用户名
		String username = (String) super.getAvailablePrincipal(principals);
		
		//通过SmartSecurity接口并根据用户名获取角色名集合
		Set<String> roleNameSet = smartSecurity.getRoleNameSet(username);
		
		//通过SmartSecurity接口并根据用户名获取与其对应的权限名集合
		Set<String> permissionNameSet = new HashSet<String>();
		if(permissionNameSet != null && roleNameSet.size() > 0){
			for(String roleName : roleNameSet){
				Set<String> CurrentPermissionNameSet = smartSecurity.getPermissionNameSet(roleName);
				permissionNameSet.addAll(CurrentPermissionNameSet);
			}
		}
		//将角色名集合与权限名集合放入AuthorizationInfo对象中，便于后续的授权操作
		SimpleAuthorizationInfo authorizationinfo = new SimpleAuthorizationInfo();
		authorizationinfo.setRoles(roleNameSet);
		authorizationinfo.setStringPermissions(permissionNameSet);
		return authorizationinfo;
	}

}
