package cn.nines.scaffold.config.shiro;

import cn.nines.scaffold.config.shiro.realm.MyRealm;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName: ShiroConfiguration
 * @Description: 在 ShiroConfiguration 中将所有的请求指向 JWT
 *                  1. 指定手工实现的 MyRealm 用于传入 DefaultWebSecurityManager
 *                  2. 在 securityManager 中关闭默认的 Session 控制
 *                      因为在前后分离项目中前端是无法获取到后端 Session 的，即无法实现用户登录状态的同步
 *                  3. 在 shiroFilterFactoryBean() 中传入自定义的 TokenFilter
 *                      并将所有的请求指向该过滤器 filterRuleMap.put("/**", "jwt")
 *
 * @author: Nines
 * @date: 2019年12月03日 20:28
 */
@Configuration
public class ShiroConfiguration {

    @Bean("securityManager")
    public DefaultWebSecurityManager securityManager(MyRealm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 使用自己的realm
        securityManager.setRealm(realm);

        /*
         * 关闭shiro自带的session，详情见文档
         * http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
         */
        DefaultSessionStorageEvaluator evaluator = new DefaultSessionStorageEvaluator();
        evaluator.setSessionStorageEnabled(false);

        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        subjectDAO.setSessionStorageEvaluator(evaluator);

        securityManager.setSubjectDAO(subjectDAO);

        return securityManager;
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();

        // 添加自己的过滤器并且取名为jwt
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("jwt", new JWTFilter());
        factoryBean.setFilters(filterMap);

        factoryBean.setSecurityManager(securityManager);
        factoryBean.setUnauthorizedUrl("/401");

        /*
         * 自定义url规则
         * http://shiro.apache.org/web.html#urls-
         */
        Map<String, String> filterRuleMap = new LinkedHashMap<>();

        filterRuleMap.put("/sys/exists", "anon");
        filterRuleMap.put("/sys/login", "anon");
        filterRuleMap.put("/sys/register", "anon");
        // 访问401和404页面不通过我们的Filter
        filterRuleMap.put("/sys/401", "anon");
        // 所有请求通过我们自己的JWT Filter
        filterRuleMap.put("/sys/**", "jwt");

        factoryBean.setFilterChainDefinitionMap(filterRuleMap);

        return factoryBean;
    }

    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib，防止重复代理和可能引起代理出错的问题
        // https://zhuanlan.zhihu.com/p/29161098
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

}
