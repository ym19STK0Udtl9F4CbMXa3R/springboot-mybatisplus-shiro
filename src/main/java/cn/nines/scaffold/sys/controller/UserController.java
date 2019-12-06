package cn.nines.scaffold.sys.controller;


import cn.nines.scaffold.common.result.ResponseJson;
import cn.nines.scaffold.common.result.PageRequest;
import cn.nines.scaffold.sys.entity.User;
import cn.nines.scaffold.sys.entity.UserRole;
import cn.nines.scaffold.sys.service.UserRoleService;
import cn.nines.scaffold.sys.service.UserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * RequiresAuthentication 需要登录验证
 * @author Nines
 * @since 2019-11-25
 */
@RequiresAuthentication
@RestController
@RequestMapping("/sys/user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private UserRoleService userRoleService;

    /**
     * 添加用户
     * @param user 用户
     * @return ResponseJson
     */
    @RequiresPermissions({"user:add"})
    @PostMapping("/add")
    public ResponseJson saveUser(@RequestBody User user){
        return userService.addUser(user) ? ResponseJson.success("添加成功") : ResponseJson.error("添加失败");
    }

    /**
     * 更新用户基本信息
     * @param user 用户
     * @return ResponseJson
     */
    @RequiresPermissions({"user:update"})
    @PutMapping("/update")
    public ResponseJson addUser(@RequestBody User user){
        return userService.updateUser(user) ? ResponseJson.success("更新成功") : ResponseJson.error("更新失败");
    }

    /**
     * 冻结用户
     * @param id 用户ID
     * @return ResponseJson
     */
    @RequiresPermissions({"user:freeze"})
    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("/{id}")
    public ResponseJson deleteUser(@PathVariable Long id){
        return userService.freezeUserAndUserRoleByUserId(id) ? ResponseJson.success("操作成功") : ResponseJson.error("操作失败");
    }

    /**
     * 恢复用户
     * @param id 用户ID
     * @return ResponseJson
     */
    @RequiresPermissions({"user:recover"})
    @Transactional(rollbackFor = Exception.class)
    @PutMapping("/{id}")
    public ResponseJson recoverUser(@PathVariable Long id){
        return userService.recoverUserAndUserRoleByUserId(id) ? ResponseJson.success("操作成功") : ResponseJson.error("操作失败");
    }

    /**
     * 分页查询
     * @param page 分页参数
     * @return ResponseJson
     */
    @RequiresPermissions({"user:show"})
    @PostMapping("/findPage")
    public ResponseJson findPage(@RequestBody PageRequest page){
        return ResponseJson.success(userService.findPage(page.getSearchText(), page.getCurrent(), page.getSize()));
    }

    /**
     * 通过ID获取用户详情
     * @param id 用户ID
     * @return ResponseJson
     */
    @RequiresPermissions({"user:show"})
    @GetMapping("/{id}")
    public ResponseJson findOne(@PathVariable Long id){
        User user = userService.findOne(id);
        return user == null ? ResponseJson.error("获取失败") : ResponseJson.success(user);
    }

    /**
     * 通过用户ID获取对应的用户角色表数据
     * @param uid 用户ID
     * @return ResponseJson
     */
    @RequiresPermissions({"user:show"})
    @GetMapping("/findUserRole/{uid}")
    public ResponseJson findUserRole(@PathVariable Long uid){
        return ResponseJson.success(userRoleService.findListByUid(uid));
    }

    /**
     * 修改用户的角色
     * @param userRoles 用户角色信息
     * @return ResponseJson
     */
    @RequiresPermissions({"user:distribution"})
    // 未知问题，Transactional 加在impl层不会回滚
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/modifyUserRole")
    public ResponseJson modifyUserRole(Long userId, @RequestBody List<UserRole> userRoles){
        return userRoleService.modifyUserRole(userId, userRoles) ? ResponseJson.success("分配成功") : ResponseJson.error("分配失败");
    }

}
