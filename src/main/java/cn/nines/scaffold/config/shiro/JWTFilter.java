package cn.nines.scaffold.config.shiro;

import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName: JWTFilter
 * @Description: 创建 JWTFilter 实现前端请求统一拦截及处理
*                  1. executeLogin() 方法中的 getSubject(request, response).login(token) 就是触发 Shiro Realm 自身的登录控制，具体内容需要手动实现
 *                 2. executeLogin() 始终返回 true 的原因是因为具体的是否登录成功的判断，需要在 Realm 中手动实现，此处不做统一判断
 *                 3. LOGIN_SIGN 是前端放置在 headers 头文件中的登录标识，如果用户发起的请求是需要登录才能正常返回的，那么头文件中就必须存在该标识并携带有效值
 * @author: Nines
 * @date: 2019年12月03日 19:45
 */
public class JWTFilter extends BasicHttpAuthenticationFilter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 登录标识
     */
    private static String LOGIN_SIGN = "Authorization";

    /**
     * 检测用户是否登录
     * 检测header里面是否包含Authorization字段即可
     *
     * @param request 请求
     * @param response 响应
     * @return boolean
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;

        String authorization = req.getHeader(LOGIN_SIGN);

        return authorization != null;
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response){
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader(LOGIN_SIGN);

        JWTToken token = new JWTToken(authorization);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(token);
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }

    /**
     * 这里我们详细说明下为什么最终返回的都是true，即允许访问
     * 例如我们提供一个地址 GET /article
     * 登入用户和游客看到的内容是不同的
     * 如果在这里返回了false，请求会被直接拦截，用户看不到任何东西
     * 所以我们在这里返回true，Controller中可以通过 subject.isAuthenticated() 来判断用户是否登入
     * 如果有些资源只有登入用户才能访问，我们只需要在方法上面加上 @RequiresAuthentication 注解即可
     * 但是这样做有一个缺点，就是不能够对GET,POST等请求进行分别过滤鉴权(因为我们重写了官方的方法)，但实际上对应用影响不大
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginAttempt(request, response)) {
            try {
                executeLogin(request, response);
            } catch (Exception e) {
                response401(request, response);
            }
        }else {
            response401(request, response);
        }

        return true;
    }

    /**
     * 对跨域提供支持
     *
     * @param request 请求
     * @param response 响应
     * @return boolean
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    /**
     * 将非法请求跳转到 /401
     */
    private void response401(ServletRequest req, ServletResponse resp) {
        try {
            HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
            httpServletResponse.sendRedirect("/sys/401");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}
