package org.smart4j.plugin.security;

import java.util.Set;

/**
 * Smart Security �ӿ�
 * @author cs
 * @since 1.0.0
 */
public interface SmartSecurity {
	/**
	 * �����û�����ȡ����
	 * @param username  �û���
	 * @return ����
	 */
	String getPassword(String username);
	
	/**
	 * �����û�����ȡ��ɫ������
	 * @param username
	 * @return
	 */
	Set<String> getRoleNameSet(String username);
	/**
	 * ���ݽ�ɫ����ȡȨ��������
	 * @param roleName ��ɫ��
	 * @return Ȩ��������
	 */
	Set<String> getPermissionNameSet(String roleName);
}
