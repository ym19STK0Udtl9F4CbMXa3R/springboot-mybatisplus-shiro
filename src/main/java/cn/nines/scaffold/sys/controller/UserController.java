package cn.nines.scaffold.sys.controller;


import cn.nines.scaffold.common.result.JsonResult;
import cn.nines.scaffold.common.result.PageRequest;
import cn.nines.scaffold.sys.entity.User;
import cn.nines.scaffold.sys.entity.UserRole;
import cn.nines.scaffold.sys.service.UserRoleService;
import cn.nines.scaffold.sys.service.UserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author Nines
 * @since 2019-11-25
 */
@RestController
//@RequiresAuthentication
@RequestMapping("/sys/user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private UserRoleService userRoleService;

    /**
     * 判断用户名是否存在
     * @param username 用户名
     * @return msg：操作是否成功
     */
    @GetMapping("/exists")
    public JsonResult findByUsername(String username){
        User user = userService.getUserByUsername(username);
        return user == null ? JsonResult.success("用户名可用") : JsonResult.error("用户名已存在");
    }

    /**
     * 添加用户
     * @param user 用户
     * @return JsonResult
     */
    @PostMapping("/add")
    public JsonResult saveUser(@RequestBody User user){
        return userService.addUser(user) ? JsonResult.success("添加成功") : JsonResult.error("添加失败");
    }

    /**
     * 更新用户基本信息
     * @param user 用户
     * @return JsonResult
     */
    @PutMapping("/update")
    public JsonResult addUser(@RequestBody User user){
        return userService.updateUser(user) ? JsonResult.success("更新成功") : JsonResult.error("更新失败");
    }

    /**
     * 冻结用户
     * @param id 用户ID
     * @return JsonResult
     */
    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("/{id}")
    public JsonResult deleteUser(@PathVariable Long id){
        return userService.freezeUserAndUserRoleByUserId(id) ? JsonResult.success("操作成功") : JsonResult.error("操作失败");
    }

    /**
     * 恢复用户
     * @param id 用户ID
     * @return JsonResult
     */
    @Transactional(rollbackFor = Exception.class)
    @PutMapping("/{id}")
    public JsonResult recoverUser(@PathVariable Long id){
        return userService.recoverUserAndUserRoleByUserId(id) ? JsonResult.success("操作成功") : JsonResult.error("操作失败");
    }

    /**
     * 分页查询
     * @param page 分页参数
     * @return JsonResult
     */
    @PostMapping("/findPage")
    public JsonResult findPage(@RequestBody PageRequest page){
        return JsonResult.success(userService.findPage(page.getSearchText(), page.getCurrent(), page.getSize()));
    }

    /**
     * 通过ID获取用户详情
     * @param id 用户ID
     * @return JsonResult
     */
    @GetMapping("/{id}")
    public JsonResult findOne(@PathVariable Long id){
        User user = userService.findOne(id);
        return user == null ? JsonResult.error("获取失败") : JsonResult.success(user);
    }

    /**
     * 通过用户ID获取对应的用户角色表数据
     * @param uid 用户ID
     * @return JsonResult
     */
    @GetMapping("/findUserRole/{uid}")
    public JsonResult findUserRole(@PathVariable Long uid){
        return JsonResult.success(userRoleService.findListByUid(uid));
    }

    /**
     * 修改用户的角色
     * @param userRoles 用户角色信息
     * @return JsonResult
     */
    // 未知问题，Transactional 加在impl层不会回滚
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/modifyUserRole")
    public JsonResult modifyUserRole(Long userId, @RequestBody List<UserRole> userRoles){
        return userRoleService.modifyUserRole(userId, userRoles) ? JsonResult.success("分配成功") : JsonResult.error("分配失败");
    }

}
