package com.maxiaoda.xwshop.config;

import com.maxiaoda.xwshop.service.ShiroRealmService;
import com.maxiaoda.xwshop.service.VerificationCheckService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, String> patten = new HashMap<>();
        patten.put("/api/code", "anon");
        patten.put("/api/login", "anon");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(patten);
        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager(ShiroRealmService shiroRealmService) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();

        //设置在某个区域内验权
        defaultWebSecurityManager.setRealm(shiroRealmService);

        defaultWebSecurityManager.setCacheManager(new MemoryConstrainedCacheManager());
        defaultWebSecurityManager.setSessionManager(new DefaultSessionManager());
        return defaultWebSecurityManager;
    }
    @Bean
    public ShiroRealmService shiroRealm(VerificationCheckService verificationCheckService){
        return new ShiroRealmService(verificationCheckService);
    }

}
