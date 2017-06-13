package org.smart4j.plugin.security.password;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

import common.utils.CodecUtil;

/**
 * MD5����ƥ����
 * @author dell
 *
 */
public class Md5Credentialsatcher implements CredentialsMatcher {

	public boolean doCredentialsMatch(AuthenticationToken token,
			AuthenticationInfo info) {
		String submitted = String.valueOf(((UsernamePasswordToken)token).getPassword());//����
		//��ȡ���ݿ��д洢�����룬�������Ѿ�����MD5����
		String encrypted = String.valueOf(info.getCredentials());
		return CodecUtil.md5(submitted).equals(encrypted);
	}

}
