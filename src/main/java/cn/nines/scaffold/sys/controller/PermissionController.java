package cn.nines.scaffold.sys.controller;


import cn.nines.scaffold.common.result.JsonResult;
import cn.nines.scaffold.common.result.PageRequest;
import cn.nines.scaffold.sys.entity.Permission;
import cn.nines.scaffold.sys.service.PermissionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author Nines
 * @since 2019-11-25
 */
@RestController
@RequestMapping("/sys/permission")
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    @PostMapping("/save")
    public JsonResult saveUser(@RequestBody Permission permission){
        if (permission.getId() == null){
            return permissionService.addPermission(permission) ? JsonResult.success("添加成功") : JsonResult.error("添加失败");
        }else {
            return permissionService.updatePermission(permission) ? JsonResult.success("更新成功") : JsonResult.error("更新失败");
        }
    }

    @PostMapping("/findPage")
    public JsonResult findPage(@RequestBody PageRequest page){
        return JsonResult.success(permissionService.findPage(page.getSearchText(), page.getCurrent(), page.getSize()));
    }

    @GetMapping("/{id}")
    public JsonResult findOne(@PathVariable Long id){
        Permission permission = permissionService.getById(id);
        return permission == null ? JsonResult.error("获取失败") : JsonResult.success(permission);
    }

}
