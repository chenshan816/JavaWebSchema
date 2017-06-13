package org.smart4j.plugin.security;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.CachingSecurityManager;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.ShiroFilter;
import org.smart4j.plugin.security.realm.SmartCustomRealm;
import org.smart4j.plugin.security.realm.SmartJdbcRealm;


/**
 * ��ȫ������
 * @author cs
 * @since 1.0.0
 */
public class SmartSecurityFilter extends ShiroFilter{
	
	@Override
	public void init() throws Exception {
		super.init();
		WebSecurityManager webSecurityManager = super.getSecurityManager();
		//����Realm,����ͬʱ֧�ֶ��Realm���������Ⱥ�˳���ö��ŷָ�
		setRealms(webSecurityManager);
		//����cache,���ڼ������ݿ��ѯ����������IO����
		setCache(webSecurityManager);
	}

	private void setCache(WebSecurityManager webSecurityManager) {
		//��ȡ������
		if(SecurityConfig.isCache()){
			CachingSecurityManager cachingSecurityManager = (CachingSecurityManager) webSecurityManager;
			//ʹ�û����ڴ��CacheManager
			MemoryConstrainedCacheManager cacheManager = new MemoryConstrainedCacheManager();
			cachingSecurityManager.setCacheManager(cacheManager);
		}
	}

	private void setRealms(WebSecurityManager webSecurityManager) {
		//��ȡsmart.plugin.security.realms������
		String securityRealms = SecurityConfig.getRealms();
		if(securityRealms != null){
			//���ݶ��Ž��в��
			String[] securityRealmArray = securityRealms.split(",");
			if(securityRealmArray.length >0){
				//ʹRealm�߱�Ψһ�Ժ�˳����
				Set<Realm> realms = new LinkedHashSet<Realm>();
				for(String securityRealm : securityRealmArray){
					if(securityRealm.equalsIgnoreCase(SecurityConstant.REALMS_JDBC)){
						//����JDBC��Realm����Ҫ�������SQL��ѯ���
						addJdbcRealm(realms);
					}else if(securityRealm.equalsIgnoreCase(SecurityConstant.REALMS_CUSTOM)){
						addCustomRealm(realms);
					}
				}
				RealmSecurityManager realmSecurityManager = (RealmSecurityManager)webSecurityManager;
				realmSecurityManager.setRealms(realms);
			}
		}
	}

	private void addCustomRealm(Set<Realm> realms) {
		//��ȡsmart.plugin.security.custom.class������
		SmartSecurity smartSecurity = SecurityConfig.getSmartSecurity();
		//����Լ�ʵ�ֵ�Realm
		SmartCustomRealm smartCustomRealm = new SmartCustomRealm(smartSecurity);
		realms.add(smartCustomRealm);
	}

	private void addJdbcRealm(Set<Realm> realms) {
		//����Լ�ʵ�ֵĻ���JDBC��ream
		SmartJdbcRealm smartJdbcRealm = new SmartJdbcRealm();
		realms.add(smartJdbcRealm);
	}
}
