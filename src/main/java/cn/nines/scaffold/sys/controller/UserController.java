package cn.nines.scaffold.sys.controller;


import cn.nines.scaffold.sys.entity.User;
import cn.nines.scaffold.sys.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author Nines
 * @since 2019-11-25
 */
@RestController
@RequestMapping("/sys/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/save")
    public String saveUser(@RequestBody User user){
        boolean f = userService.addUser(user);
        System.out.println(f);
        if (f){
            return "添加成功";
        }
        return "添加失败";
    }

}
