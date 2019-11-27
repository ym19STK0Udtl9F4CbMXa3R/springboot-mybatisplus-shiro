package cn.nines.scaffold.sys.controller;


import cn.nines.scaffold.common.result.JsonResult;
import cn.nines.scaffold.common.result.PageRequest;
import cn.nines.scaffold.sys.entity.Role;
import cn.nines.scaffold.sys.entity.RolePermission;
import cn.nines.scaffold.sys.service.RolePermissionService;
import cn.nines.scaffold.sys.service.RoleService;
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
@RestController
@RequestMapping("/sys/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @Resource
    private RolePermissionService rolePermissionService;

    /**
     * 添加角色
     * @param role 角色
     * @return JsonResult
     */
    @PostMapping("/add")
    public JsonResult addRole(@RequestBody Role role){
        return roleService.addRole(role) ? JsonResult.success("添加成功") : JsonResult.error("添加失败");
    }

    /**
     * 更新角色
     * @param role 角色
     * @return JsonResult
     */
    @PutMapping("/update")
    public JsonResult updateRole(@RequestBody Role role){
        return roleService.updateRole(role) ? JsonResult.success("更新成功") : JsonResult.error("更新失败");
    }

    /**
     * 冻结角色
     * @param id 角色ID
     * @return JsonResult
     */
    @DeleteMapping("/{id}")
    public JsonResult deleteRole(@PathVariable Long id){
        return roleService.freezeRoleAndUserRoleAndRolePermissionByRoleId(id) ? JsonResult.success("操作成功") : JsonResult.error("操作失败");
    }

    /**
     * 恢复角色
     * @param id 角色ID
     * @return JsonResult
     */
    @PutMapping("/{id}")
    public JsonResult recoverRole(@PathVariable Long id){
        return roleService.recoverRoleAndUserRoleAndRolePermissionByRoleId(id) ? JsonResult.success("操作成功") : JsonResult.error("操作失败");
    }

    /**
     * 分页查询
     * @param page 分页参数
     * @return JsonResult
     */
    @PostMapping("/findPage")
    public JsonResult findPage(@RequestBody PageRequest page){
        return JsonResult.success(roleService.findPage(page.getSearchText(), page.getCurrent(), page.getSize()));
    }

    /**
     * 通过角色ID获取角色详情
     * @param id 角色ID
     * @return JsonResult
     */
    @GetMapping("/{id}")
    public JsonResult findOne(@PathVariable Long id){
        Role role = roleService.getById(id);
        return role == null ? JsonResult.error("获取失败") : JsonResult.success(role);
    }

    /**
     * 通过角色ID获取对应的角色权限表数据
     * @param rid 角色ID
     * @return JsonResult1
     */
    @GetMapping("/findRolePermission/{rid}")
    public JsonResult findUserRole(@PathVariable Long rid){
        return JsonResult.success(rolePermissionService.findListByRid(rid));
    }

    /**
     * 修改用户的角色
     * @param rolePermissions 用户角色信息
     * @return JsonResult
     */
    @PostMapping("/modifyRolePermission")
    public JsonResult modifyUserRole(@RequestBody List<RolePermission> rolePermissions){
        return rolePermissionService.modifyUserRole(rolePermissions) ? JsonResult.success("分配成功") : JsonResult.error("分配失败");
    }

}
