package org.smart4j.plugin.security.Tag;

import java.util.Arrays;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.tags.RoleTag;

/**
 * �жϵ�ǰ�û��Ƿ�ӵ�����еĽ�ɫ
 * @author cs
 * @since 1.0.0
 */
public class HasAllRolesTag extends RoleTag{
	
	private static final String ROLE_NAMES_DELIMITER = ",";
	
	@Override
	protected boolean showTagBody(String roleNames) {
		boolean hasAllRole = false;
		
		Subject subject = getSubject();
		if(subject != null){
			if(subject.hasAllRoles(Arrays.asList(roleNames.split(ROLE_NAMES_DELIMITER)))){
				hasAllRole = true;
			}
		}
		return hasAllRole;
	}
	
}
