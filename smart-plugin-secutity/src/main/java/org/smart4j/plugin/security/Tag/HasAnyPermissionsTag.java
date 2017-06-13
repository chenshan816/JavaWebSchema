package org.smart4j.plugin.security.Tag;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.tags.PermissionTag;

/**
 * �жϵ�ǰ�û��Ƿ�ӵ������ĳһ��Ȩ�ޣ����ŷָ�����ʾ���򡱵Ĺ�ϵ��
 *
 * @author cs
 * @since 1.0.0
 */
public class HasAnyPermissionsTag extends PermissionTag {

    private static final String PERMISSION_NAMES_DELIMITER = ",";

    @Override
    protected boolean showTagBody(String permissionNames) {
        boolean hasAnyPermission = false;
        Subject subject = getSubject();
        if (subject != null) {
            for (String permissionName : permissionNames.split(PERMISSION_NAMES_DELIMITER)) {
                if (subject.isPermitted(permissionName.trim())) {
                    hasAnyPermission = true;
                    break;
                }
            }
        }
        return hasAnyPermission;
    }
}

