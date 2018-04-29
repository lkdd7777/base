package com.sppan.base.config.shiro;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;

@Configuration
@Import(ShiroManager.class)
public class ShiroConfig {

	@Bean(name = "realm")
	@DependsOn("lifecycleBeanPostProcessor")
	@ConditionalOnMissingBean
	public Realm realm() {
		return new MyRealm();
	}
	
	 /**
     * 用户授权信息Cache
     */
    @Bean(name = "shiroCacheManager")
    @ConditionalOnMissingBean
    public CacheManager cacheManager() {
        return new MemoryConstrainedCacheManager();
    }

    @Bean(name = "securityManager")
    @ConditionalOnMissingBean
    public DefaultSecurityManager securityManager() {
        DefaultSecurityManager sm = new DefaultWebSecurityManager();
        sm.setCacheManager(cacheManager());
        return sm;
    }

	@Bean(name = "shiroFilter")
	@DependsOn("securityManager")
	@ConditionalOnMissingBean
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultSecurityManager securityManager, Realm realm) {
		securityManager.setRealm(realm);

		ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
		shiroFilter.setSecurityManager(securityManager);
		shiroFilter.setLoginUrl("/admin/login");
		shiroFilter.setSuccessUrl("/admin/index");
		shiroFilter.setUnauthorizedUrl("/previlige/no");
		Map<String, String> filterChainDefinitionMap = new HashMap<String, String>();
		//静态文件
		filterChainDefinitionMap.put("/assets/**", "anon");
		//下载文件
//		filterChainDefinitionMap.put("/upload/**", "anon");
		//用户登录
		filterChainDefinitionMap.put("/admin/login", "anon");
		//用户管理
		filterChainDefinitionMap.put("/admin/user/index", "perms[system:user:index]");
		filterChainDefinitionMap.put("/admin/user/add", "perms[system:user:add]");
		filterChainDefinitionMap.put("/admin/user/edit*", "perms[system:user:edit]");
		filterChainDefinitionMap.put("/admin/user/deleteBatch", "perms[system:user:deleteBatch]");
		filterChainDefinitionMap.put("/admin/user/grant/**", "perms[system:user:grant]");
		//权限管理
		filterChainDefinitionMap.put("/admin/role/index", "perms[system:role:index]");
		filterChainDefinitionMap.put("/admin/role/add", "perms[system:role:add]");
		filterChainDefinitionMap.put("/admin/role/edit*", "perms[system:role:edit]");
		filterChainDefinitionMap.put("/admin/role/deleteBatch", "perms[system:role:deleteBatch]");
		filterChainDefinitionMap.put("/admin/role/grant/**", "perms[system:role:grant]");
		//资源管理
		filterChainDefinitionMap.put("/admin/resource/index", "perms[system:resource:index]");
		filterChainDefinitionMap.put("/admin/resource/add", "perms[system:resource:add]");
		filterChainDefinitionMap.put("/admin/resource/edit*", "perms[system:resource:edit]");
		filterChainDefinitionMap.put("/admin/resource/deleteBatch", "perms[system:resource:deleteBatch]");
		
		filterChainDefinitionMap.put("/admin/**", "authc");

		//文件管理
		filterChainDefinitionMap.put("/file/index", "perms[web:file:index]");
		filterChainDefinitionMap.put("/file/add", "perms[web:file:add]");
		filterChainDefinitionMap.put("/file/edit*", "perms[web:file:edit]");
		filterChainDefinitionMap.put("/file/deleteBatch", "perms[web:file:deleteBatch]");
		filterChainDefinitionMap.put("/file/download", "perms[web:file:download]");
		shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilter;
	}
}
