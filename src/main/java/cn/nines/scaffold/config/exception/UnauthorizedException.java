package cn.nines.scaffold.config.exception;

/**
 * @ClassName: UnauthorizedException
 * @Description: 未经授权的异常
 * @author: Nines
 * @date: 2019年12月03日 21:24
 */
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String msg) {
        super(msg);
    }

    public UnauthorizedException() {
        super();
    }

}
