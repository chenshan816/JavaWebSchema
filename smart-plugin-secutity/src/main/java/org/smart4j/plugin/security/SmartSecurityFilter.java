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
 * 安全过滤器
 * @author cs
 * @since 1.0.0
 */
public class SmartSecurityFilter extends ShiroFilter{
	
	@Override
	public void init() throws Exception {
		super.init();
		WebSecurityManager webSecurityManager = super.getSecurityManager();
		//设置Realm,可以同时支持多个Realm，并按照先后顺序用逗号分割
		setRealms(webSecurityManager);
		//设置cache,用于减少数据库查询次数，降低IO访问
		setCache(webSecurityManager);
	}

	private void setCache(WebSecurityManager webSecurityManager) {
		//读取配置项
		if(SecurityConfig.isCache()){
			CachingSecurityManager cachingSecurityManager = (CachingSecurityManager) webSecurityManager;
			//使用基于内存的CacheManager
			MemoryConstrainedCacheManager cacheManager = new MemoryConstrainedCacheManager();
			cachingSecurityManager.setCacheManager(cacheManager);
		}
	}

	private void setRealms(WebSecurityManager webSecurityManager) {
		//读取smart.plugin.security.realms配置项
		String securityRealms = SecurityConfig.getRealms();
		if(securityRealms != null){
			//根据逗号进行拆分
			String[] securityRealmArray = securityRealms.split(",");
			if(securityRealmArray.length >0){
				//使Realm具备唯一性和顺序性
				Set<Realm> realms = new LinkedHashSet<Realm>();
				for(String securityRealm : securityRealmArray){
					if(securityRealm.equalsIgnoreCase(SecurityConstant.REALMS_JDBC)){
						//基于JDBC的Realm，需要配置相关SQL查询语句
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
		//读取smart.plugin.security.custom.class配置项
		SmartSecurity smartSecurity = SecurityConfig.getSmartSecurity();
		//添加自己实现的Realm
		SmartCustomRealm smartCustomRealm = new SmartCustomRealm(smartSecurity);
		realms.add(smartCustomRealm);
	}

	private void addJdbcRealm(Set<Realm> realms) {
		//添加自己实现的基于JDBC的ream
		SmartJdbcRealm smartJdbcRealm = new SmartJdbcRealm();
		realms.add(smartJdbcRealm);
	}
}
