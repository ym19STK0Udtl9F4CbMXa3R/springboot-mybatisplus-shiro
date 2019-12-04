package cn.nines.scaffold.sys.controller;

import cn.nines.scaffold.common.result.ResponseJson;
import cn.nines.scaffold.common.util.JWTUtil;
import cn.nines.scaffold.common.util.PasswordSaltUtil;
import cn.nines.scaffold.config.exception.ExceptionEnum;
import cn.nines.scaffold.sys.entity.User;
import cn.nines.scaffold.sys.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @ClassName: IndexController
 * @Description: 登陆，注册
 * @author: Nines
 * @date: 2019年12月02日 18:04
 */
@RestController
@RequestMapping("/sys")
public class WebController {

    @Resource
    private UserService userService;

    /**
     * 判断用户名是否存在
     * @param username 用户名
     * @return msg：操作是否成功
     */
    @GetMapping("/exists")
    public ResponseJson findByUsername(String username){
        User user = userService.getUserByUsername(username);
        return user == null ? ResponseJson.success("用户名可用") : ResponseJson.error(ExceptionEnum.USERNAME_IS_EXIST);
    }

    /**
     * 登陆
     * @return msg
     */
    @PostMapping("/login")
    public ResponseJson login(@RequestBody User loginUser){
        User user = userService.getUserByUsername(loginUser.getUsername());
        if (user != null){
            if (user.getPassword().equals(PasswordSaltUtil.md5(loginUser.getPassword(), user.getSalt()))) {
                return ResponseJson.success("登录成功", JWTUtil.sign(user.getUsername(), user.getPassword()));
            } else {
                return ResponseJson.error(ExceptionEnum.INCORRECT_ACCOUNT_OR_PASSWORD);
            }
        }else {
            return ResponseJson.error(ExceptionEnum.ACCOUNT_NOT_EXIST);
        }

    }

    /**
     * 未授权操作
     * @return msg
     */
    @CrossOrigin
    @RequestMapping(path = "/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseJson unauthorized() {
        return ResponseJson.error(ExceptionEnum.AUTHENTICATION_FAILED);
    }
}
