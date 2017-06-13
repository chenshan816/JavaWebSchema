package org.smart4j.plugin.security.password;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

import common.utils.CodecUtil;

/**
 * MD5密码匹配器
 * @author dell
 *
 */
public class Md5Credentialsatcher implements CredentialsMatcher {

	public boolean doCredentialsMatch(AuthenticationToken token,
			AuthenticationInfo info) {
		String submitted = String.valueOf(((UsernamePasswordToken)token).getPassword());//明文
		//获取数据库中存储的密码，该密码已经经过MD5加密
		String encrypted = String.valueOf(info.getCredentials());
		return CodecUtil.md5(submitted).equals(encrypted);
	}

}
