package cn.nines.scaffold.sys.controller;


import cn.nines.scaffold.common.result.JsonResult;
import cn.nines.scaffold.common.result.PageRequest;
import cn.nines.scaffold.sys.entity.Role;
import cn.nines.scaffold.sys.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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

    @PostMapping("/save")
    public JsonResult saveUser(@RequestBody Role role){
        if (role.getId() == null){
            return roleService.addRole(role) ? JsonResult.success("添加成功") : JsonResult.error("添加失败");
        }else {
            return roleService.updateRole(role) ? JsonResult.success("更新成功") : JsonResult.error("更新失败");
        }
    }

    @PutMapping("/{id}")
    public JsonResult updateStatus(@PathVariable Long id){
        return roleService.updateStatus(id) ? JsonResult.success("操作成功") : JsonResult.error("操作失败");
    }

    @PostMapping("/findPage")
    public JsonResult findPage(@RequestBody PageRequest page){
        return JsonResult.success(roleService.findPage(page.getSearchText(), page.getCurrent(), page.getSize()));
    }

    @GetMapping("/{id}")
    public JsonResult findOne(@PathVariable Long id){
        Role role = roleService.getById(id);
        return role == null ? JsonResult.error("获取失败") : JsonResult.success(role);
    }

}
