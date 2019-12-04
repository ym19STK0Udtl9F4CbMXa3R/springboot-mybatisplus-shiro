package cn.nines.scaffold.config.exception;

import cn.nines.scaffold.common.result.ResponseJson;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: GlobalExceptionHandler
 * @Description: 全局异常处理
 * @author: Nines
 * @date: 2019年12月03日 17:28
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 捕捉shiro的异常
     * @param e 异常
     * @return 报错信息
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public ResponseJson handle401(ShiroException e) {
        logger.error("shiro异常: " + e.getMessage());
        return ResponseJson.error(401, e.getMessage());
    }

    /**
     * 捕捉UnauthorizedException
     * @return ResponseJson
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseJson handle401(UnauthorizedException e) {
        return ResponseJson.error(401, e.getMessage());
    }

    /**
     * 捕捉其他所有异常
     * @return 异常信息
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseJson globalException(HttpServletRequest request, Throwable ex) {
        logger.error("其他异常: " + ex.getMessage());
        return ResponseJson.error(getStatus(request).value(), "未知异常, 请联系管理员");
    }

    /**
     * 获取异常状态码
     * @return 状态码
     */
    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

}
