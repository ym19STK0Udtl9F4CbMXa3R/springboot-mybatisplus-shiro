package cn.nines.scaffold.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @ClassName: JWTUtil
 * @Description: JWTUtil 整合 JWT 相关操作
 *              1. 在这个工具类中可以实现对用户名和密码的加密处理，校验 token 是否正确，获取用户名等操作
 *              2. Algorithm algorithm = Algorithm.HMAC256(password) 是对密码进行加密后再与用户名混淆在一起
 *              3. 在签名时可以通过 .withExpiresAt(date) 指定 token 的过期时间
 * @author: Nines
 * @date: 2019年12月03日 19:40
 */
public class JWTUtil {

    // 过期时间30天
//    private static final long EXPIRE_TIME = 24 * 60 * 30 * 1000;
    private static final long EXPIRE_TIME = 10 * 60 * 1000;

    /**
     * 校验token是否正确
     *
     * @param token    密钥
     * @param username 登录名
     * @param password 密码
     * @return boolean
     */
    public static boolean verify(String token, String username, String password) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(password);

            JWTVerifier verifier = JWT.require(algorithm).withClaim("username", username).build();

            DecodedJWT jwt = verifier.verify(token);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取登录名
     *
     * @param token 密钥
     * @return 用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);

            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成签名
     * @param username 用户名
     * @param password 密码
     * @return 签名
     */
    public static String sign(String username, String password) {
        try {
            // 指定过期时间
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);

            Algorithm algorithm = Algorithm.HMAC256(password);

            return JWT.create()
                    .withClaim("username", username)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

}
