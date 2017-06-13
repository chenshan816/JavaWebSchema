package org.smart4j.plugin.security.Tag;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.tags.PermissionTag;

/**
 * �жϵ�ǰ�û��Ƿ�ӵ���������е�Ȩ�ޣ����ŷָ�����ʾ���롱�Ĺ�ϵ��
 *
 * @author cs
 * @since 1.0.0
 */
public class HasAllPermissionsTag extends PermissionTag {

    private static final String PERMISSION_NAMES_DELIMITER = ",";

    @Override
    protected boolean showTagBody(String permNames) {
        boolean hasAllPermission = false;
        Subject subject = getSubject();
        if (subject != null) {
            if (subject.isPermittedAll(permNames.split(PERMISSION_NAMES_DELIMITER))) {
                hasAllPermission = true;
            }
        }
        return hasAllPermission;
    }
}