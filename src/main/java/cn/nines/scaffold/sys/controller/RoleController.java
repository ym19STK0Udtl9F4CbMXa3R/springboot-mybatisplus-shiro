package cn.nines.scaffold.sys.controller;


import cn.nines.scaffold.common.result.ResponseJson;
import cn.nines.scaffold.common.result.PageRequest;
import cn.nines.scaffold.sys.entity.Role;
import cn.nines.scaffold.sys.entity.RolePermission;
import cn.nines.scaffold.sys.service.RolePermissionService;
import cn.nines.scaffold.sys.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 角色 前端控制器
 * </p>
 *
 * @author Nines
 * @since 2019-11-25
 */
@RequiresAuthentication
@RestController
@RequestMapping("/sys/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @Resource
    private RolePermissionService rolePermissionService;

    /**
     * 获取所有角色信息
     * @return list
     */
    @GetMapping("/list")
    public ResponseJson getRoleList(){
        List<Role> roles = roleService.findList();
        return ResponseJson.success(roles);
    }

    /**
     * 添加角色
     * @param role 角色
     * @return ResponseJson
     */
    @PostMapping("/add")
    public ResponseJson addRole(@RequestBody Role role){
        return roleService.addRole(role) ? ResponseJson.success("添加成功") : ResponseJson.error("添加失败");
    }

    /**
     * 更新角色
     * @param role 角色
     * @return ResponseJson
     */
    @PutMapping("/update")
    public ResponseJson updateRole(@RequestBody Role role){
        return roleService.updateRole(role) ? ResponseJson.success("更新成功") : ResponseJson.error("更新失败");
    }

    /**
     * 冻结角色
     * @param id 角色ID
     * @return ResponseJson
     */
    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("/{id}")
    public ResponseJson deleteRole(@PathVariable Long id){
        return roleService.freezeRoleAndUserRoleAndRolePermissionByRoleId(id) ? ResponseJson.success("操作成功") : ResponseJson.error("操作失败");
    }

    /**
     * 恢复角色
     * @param id 角色ID
     * @return ResponseJson
     */
    @Transactional(rollbackFor = Exception.class)
    @PutMapping("/{id}")
    public ResponseJson recoverRole(@PathVariable Long id){
        return roleService.recoverRoleAndUserRoleAndRolePermissionByRoleId(id) ? ResponseJson.success("操作成功") : ResponseJson.error("操作失败");
    }

    /**
     * 分页查询
     * @param page 分页参数
     * @return ResponseJson
     */
    @PostMapping("/findPage")
    public ResponseJson findPage(@RequestBody PageRequest page){
        return ResponseJson.success(roleService.findPage(page.getSearchText(), page.getCurrent(), page.getSize()));
    }

    /**
     * 通过角色ID获取角色详情
     * @param id 角色ID
     * @return ResponseJson
     */
    @GetMapping("/{id}")
    public ResponseJson findOne(@PathVariable Long id){
        Role role = roleService.getById(id);
        return role == null ? ResponseJson.error("获取失败") : ResponseJson.success(role);
    }

    /**
     * 通过角色ID获取对应的角色权限表数据
     * @param rid 角色ID
     * @return JsonResult1
     */
    @GetMapping("/findRolePermission/{rid}")
    public ResponseJson findUserRole(@PathVariable Long rid){
        return ResponseJson.success(rolePermissionService.findListByRid(rid));
    }

    /**
     * 修改用户的角色
     * @param rolePermissions 用户角色信息
     * @return ResponseJson
     */
    @PostMapping("/modifyRolePermission")
    public ResponseJson modifyUserRole(Long roleId, @RequestBody List<RolePermission> rolePermissions){
        return rolePermissionService.modifyUserRole(roleId, rolePermissions) ? ResponseJson.success("分配成功") : ResponseJson.error("分配失败");
    }

}
