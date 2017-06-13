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
 * ����Smart���Զ���Realm��ʵ��SmartSecurity�ӿڣ�
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
		
		//ͨ��AuthenticationToken�����ȡ�ӱ����ύ�������û���
		String username = ((UsernamePasswordToken)token).getUsername();
		String password = smartSecurity.getPassword(username);
		//���û������������AuthenticationInfo�����У����ں�������֤����
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
		
		//��ȡ����֤�û����û���
		String username = (String) super.getAvailablePrincipal(principals);
		
		//ͨ��SmartSecurity�ӿڲ������û�����ȡ��ɫ������
		Set<String> roleNameSet = smartSecurity.getRoleNameSet(username);
		
		//ͨ��SmartSecurity�ӿڲ������û�����ȡ�����Ӧ��Ȩ��������
		Set<String> permissionNameSet = new HashSet<String>();
		if(permissionNameSet != null && roleNameSet.size() > 0){
			for(String roleName : roleNameSet){
				Set<String> CurrentPermissionNameSet = smartSecurity.getPermissionNameSet(roleName);
				permissionNameSet.addAll(CurrentPermissionNameSet);
			}
		}
		//����ɫ��������Ȩ�������Ϸ���AuthorizationInfo�����У����ں�������Ȩ����
		SimpleAuthorizationInfo authorizationinfo = new SimpleAuthorizationInfo();
		authorizationinfo.setRoles(roleNameSet);
		authorizationinfo.setStringPermissions(permissionNameSet);
		return authorizationinfo;
	}

}
