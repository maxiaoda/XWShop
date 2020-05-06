package com.maxiaoda.xwshop.config;

import com.maxiaoda.xwshop.interceptor.UserLoginInterceptor;
import com.maxiaoda.xwshop.service.ShiroRealmService;
import com.maxiaoda.xwshop.service.UserService;
import com.maxiaoda.xwshop.service.VerificationCheckService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig implements WebMvcConfigurer {
    @Autowired
    private UserService userService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserLoginInterceptor(userService));
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager, ShiroLoginFilter shiroLoginFilter) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, String> patten = new HashMap<>();
        patten.put("/api/code", "anon");
        patten.put("/api/login", "anon");
        patten.put("/api/logout", "anon");
        patten.put("/api/status", "anon");
        patten.put("/**", "authc");

        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("shiroLoginFilter", shiroLoginFilter);
        shiroFilterFactoryBean.setFilters(filterMap);

        shiroFilterFactoryBean.setFilterChainDefinitionMap(patten);
        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager(ShiroRealmService shiroRealmService) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();

        SecurityUtils.setSecurityManager(defaultWebSecurityManager);

        //设置在某个区域内验权
        defaultWebSecurityManager.setRealm(shiroRealmService);

        defaultWebSecurityManager.setCacheManager(new MemoryConstrainedCacheManager());
        defaultWebSecurityManager.setSessionManager(new DefaultWebSessionManager());
        return defaultWebSecurityManager;
    }

    @Bean
    public ShiroRealmService shiroRealm(VerificationCheckService verificationCheckService) {
        return new ShiroRealmService(verificationCheckService);
    }

}
