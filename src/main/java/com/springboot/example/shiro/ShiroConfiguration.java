// package com.springboot.example.shiro;
//
// import org.apache.shiro.mgt.SecurityManager;
// import org.apache.shiro.realm.AuthorizingRealm;
// import org.apache.shiro.session.mgt.SessionManager;
// import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
// import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
// import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
// import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
// import org.crazycake.shiro.RedisManager;
// import org.crazycake.shiro.RedisSessionDAO;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
//
// import javax.servlet.Filter;
// import java.util.HashMap;
// import java.util.LinkedHashMap;
// import java.util.Map;
//
// @Configuration
// public class ShiroConfiguration {
//
//     @Value("${spring.redis.host}:${spring.redis.port}")
//     private String host;
//     @Value("${spring.redis.password}")
//     private String password;
//
//     @Bean
//     public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
//         ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
//         shiroFilterFactoryBean.setSecurityManager(securityManager);
//         Map<String, Filter> filters = new HashMap<>();
//         // 如果要在 session 过期重新登录后跳转到登录器前页面，或者 session 过期后发送 ajax 请求能给予友好提示信息，
//         // 一般会自定义 AuthenticationFilter
//         filters.put("authc", new SimpleAuthenticationFilter());
//         shiroFilterFactoryBean.setFilters(filters);
//         Map<String,String> filterChainDefinitionMap = new LinkedHashMap<>();
//         // 过滤链定义，从上向下顺序执行，一般将 /** 放在最下边，这是一个坑呢，一不小心代码就不好使了
//         // 配置不会被拦截的链接，anon：匹配到的 url 可以匿名访问
//         filterChainDefinitionMap.put("/static/**", "anon");
//         filterChainDefinitionMap.put("/login", "anon");
//         filterChainDefinitionMap.put("/logout", "anon");
//         // 如果 index 为登录成功后的网站首页，那么登录成功后没关闭浏览器，访问 index 就不再需要登录
//         filterChainDefinitionMap.put("/index", "user");
//         // authc：匹配到的 url 必须认证通过才可以访问
//         filterChainDefinitionMap.put("/**", "authc");
//         //未授权
//         // shiroFilterFactoryBean.setUnauthorizedUrl("/403");
//         shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
//         return shiroFilterFactoryBean;
//     }
//
//     @Bean
//     public SecurityManager securityManager() {
//         DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//         securityManager.setRealm(authorizingRealm());
//         securityManager.setSessionManager(sessionManager());
//         return securityManager;
//     }
//
//     @Bean
//     public AuthorizingRealm authorizingRealm() {
//         return new SimpleAuthorizingRealm();
//     }
//
//     @Bean
//     public SessionManager sessionManager() {
//         DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
//         sessionManager.setSessionDAO(redisSessionDAO());
//         return sessionManager;
//     }
//
//     @Bean
//     public RedisSessionDAO redisSessionDAO() {
//         RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
//         redisSessionDAO.setRedisManager(redisManager());
//         return redisSessionDAO;
//     }
//
//     @Bean
//     public RedisManager redisManager() {
//         RedisManager redisManager = new RedisManager();
//         redisManager.setHost(host);
//         redisManager.setPassword(password);
//         return redisManager;
//     }
//
//     // 开启对 Shiro Spring AOP 权限注解 @RequiresPermissions 的支持
//     // @Bean
//     // public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
//     //     return new LifecycleBeanPostProcessor();
//     // }
//     // @Bean
//     // @DependsOn("lifecycleBeanPostProcessor")
//     // public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
//     //     DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
//     //     advisorAutoProxyCreator.setProxyTargetClass(true);
//     //     return advisorAutoProxyCreator;
//     // }
//     @Bean
//     public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
//         AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
//         authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
//         return authorizationAttributeSourceAdvisor;
//     }
//
// }
