package cn.nines.scaffold.common.util;

import cn.nines.scaffold.sys.entity.User;
import lombok.extern.java.Log;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;

import java.time.LocalDateTime;

/**
 * <p>
 * 密码进行MD5加密
 * </p>
 *
 * @author Nines
 * @since 2019-11-25
 */
@Log
public class PasswordSaltUtil {

    public static User md5(User user){
        // 需要加密的密码
        String password = user.getPassword();
        //随机出来的盐
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        // 使用加密算法 md5
        String encryptionAlgorithm = "md5";
        //反复加密的次数，md5(md5(""))
        int times = 2;
        String encodePassword = new SimpleHash(encryptionAlgorithm, password, salt, times).toString();
        log.info("加密密码!");
        log.info("原始密码是 "+ password +" , 盐是： "+ salt +", 运算次数是： "+ times +", 运算出来的密文是："+ encodePassword);
        user.setSalt(salt);
        user.setPassword(encodePassword);
        return user;
    }

    public static void main(String[] args){

        User user = new User();
        user.setNickName("测试账号");
        user.setUsername("test");
        user.setPassword("test");
        user.setEmail("test@test.com");
        user.setStatus(true);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());

        User newUser = md5(user);
        System.out.println(newUser);

    }

}
