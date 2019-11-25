package cn.nines.scaffold.common.shiro;

import cn.nines.scaffold.common.shiro.realm.UserRealm;
import cn.nines.scaffold.sys.service.PermissionService;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * <p>
 * Shiro 配置类
 * </p>
 *
 * @author Nines
 * @since 2019-11-25
 */
@Configuration
public class ShiroConfig {

    private static final Logger logger = LoggerFactory.getLogger(ShiroConfig.class);

    @Resource
    PermissionService permissionService;

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager){
        logger.info("ShiroConfiguration.shirFilter()");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 拦截后跳转的页面     如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/toLogin");
        // 未授权页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        /*
            Shiro内置过滤器，可以实现权限相关的拦截器
                常用的过滤器：
                    anon：无需认证（登录）可以访问
                    authc：必须认证才可以访问
                    user：如果使用rememberMe的功能可以直接访问
                    perms：该资源必须得到资源权限才可以访问
                    role：该资源必须得到角色权限才可以访问
         */
        Map<String, String> filterMap = new LinkedHashMap<String, String>();

        //        匿名静态资源放行
        filterMap.put("/bootstarp/**", "anon");
        filterMap.put("/css/**", "anon");
        filterMap.put("/image/**", "anon");
        filterMap.put("/js/**", "anon");
        //  设置权限限制
//        List<SysPermission> permissions = permissionService.getPermissionAll();
//        for (SysPermission permission : permissions) {
//            filterMap.put(permission.getUrl(),"perms["+ permission.getName() +"]");
//        }
        //拦截所有请求
//        filterMap.put("/**", "authc");
        System.out.println(filterMap);
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm());
        return securityManager;
    }

    /**
     *  凭证匹配器
     *  （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理）
     *
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //散列算法，这里使用MD5算法
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        //散列的次数，比如散列两次，相当于md5(md5(""))
        hashedCredentialsMatcher.setHashIterations(2);
        return hashedCredentialsMatcher;
    }

    @Bean
    public UserRealm userRealm(){
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return userRealm;
    }

    /**
     * 开启 shiro aop 注解支持
     * 使用代理方式; 所以需要开启代码支持
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public SimpleMappingExceptionResolver simpleMappingExceptionResolver(){
        SimpleMappingExceptionResolver simpleMappingExceptionResolver = new SimpleMappingExceptionResolver();
        Properties mappings = new Properties();
        /*
            参数一：异常的类型，注意必须是异常类型的全名
            参数二：视图名称
         */
        // 数据库异常
        mappings.setProperty("DatabaseException", "databaseError");
        //未授权异常
        mappings.setProperty("UnauthorizedException", "403");

        simpleMappingExceptionResolver.setExceptionMappings(mappings);
        //默认错误视图
        simpleMappingExceptionResolver.setDefaultErrorView("error");
        //默认为 exception
        simpleMappingExceptionResolver.setExceptionAttribute("ex");
        return simpleMappingExceptionResolver;
    }

}
