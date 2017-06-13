package org.smart4j.plugin.security;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.plugin.security.exception.AuthcException;

/**
 * SecurityÖúÊÖÀà
 * @author cs
 * @since 1.0.0
 */
public final class SecurityHelper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityHelper.class);
	/**
	 * µÇÂ¼
	 */
	public static void login(String username,String password) throws AuthcException{
		Subject currentUser = SecurityUtils.getSubject();
		if(currentUser != null){
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			try {
				currentUser.login(token);
			} catch (AuthenticationException e) {
				LOGGER.error("login failuer",e);
				throw new AuthcException(e); 
			}
		}
	}
	
	/**
	 * ×¢Ïú
	 */
	public static void logout(){
		Subject currentUser = SecurityUtils.getSubject();
		if(currentUser != null){
			currentUser.logout();
		}
	}
}
