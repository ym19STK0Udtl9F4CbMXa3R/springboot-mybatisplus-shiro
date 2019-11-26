package cn.nines.scaffold.sys.controller;


import cn.nines.scaffold.common.result.JsonResult;
import cn.nines.scaffold.common.result.PageRequest;
import cn.nines.scaffold.sys.entity.User;
import cn.nines.scaffold.sys.service.UserService;
import org.springframework.web.bind.annotation.*;

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
    public JsonResult saveUser(@RequestBody User user){
        if (user.getId() == null){
            return userService.addUser(user) ? JsonResult.success("添加成功") : JsonResult.error("添加失败");
        }else {
            return userService.updateUser(user) ? JsonResult.success("更新成功") : JsonResult.error("更新失败");
        }
    }

    @PutMapping("/{id}")
    public JsonResult updateStatus(@PathVariable Long id){
        return userService.updateStatus(id) ? JsonResult.success("操作成功") : JsonResult.error("操作失败");
    }

    @PostMapping("/findPage")
    public JsonResult findPage(@RequestBody PageRequest page){
        return JsonResult.success(userService.findPage(page.getSearchText(), page.getCurrent(), page.getSize()));
    }

    @GetMapping("/{id}")
    public JsonResult findOne(@PathVariable Long id){
        User user = userService.getById(id);
        return user == null ? JsonResult.error("获取失败") : JsonResult.success(user);
    }

}
