package cn.nines.scaffold.sys.controller;


import cn.nines.scaffold.common.result.JsonResult;
import cn.nines.scaffold.common.result.PageRequest;
import cn.nines.scaffold.sys.entity.Permission;
import cn.nines.scaffold.sys.entity.PermissionTreeNode;
import cn.nines.scaffold.sys.service.PermissionService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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

    /**
     * 添加权限
     * @param permission 权限
     * @return JsonResult
     */
    @PostMapping("/add")
    public JsonResult addPermission(@RequestBody Permission permission){
        return permissionService.addPermission(permission) ? JsonResult.success("添加成功") : JsonResult.error("添加失败");
    }

    /**
     * 修改权限
     * @param permission 权限
     * @return JsonResult
     */
    @PutMapping("/update")
    public JsonResult updatePermission(@RequestBody Permission permission){
        return permissionService.updatePermission(permission) ? JsonResult.success("更新成功") : JsonResult.error("更新失败");
    }

    /**
     * 冻结权限
     * @param id 权限ID
     * @return JsonResult
     */
    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("/{id}")
    public JsonResult deletePermission(@PathVariable Long id){
        return permissionService.freezePermissionAndRolePermissionByPermissionId(id) ? JsonResult.success("操作成功") : JsonResult.error("操作失败");
    }

    /**
     * 恢复权限
     * @param id 权限ID
     * @return JsonResult
     */
    @Transactional(rollbackFor = Exception.class)
    @PutMapping("/{id}")
    public JsonResult recoverPermission(@PathVariable Long id){
        return permissionService.recoverPermissionAndRolePermissionByPermissionId(id) ? JsonResult.success("操作成功") : JsonResult.error("操作失败");
    }

    /**
     * 分页信息
     * @param page 分页参数
     * @return JsonResult
     */
    @PostMapping("/findPage")
    public JsonResult findPage(@RequestBody PageRequest page){
        return JsonResult.success(permissionService.findPage(page.getSearchText(), page.getCurrent(), page.getSize()));
    }

    /**
     * 通过ID获取权限详情
     * @param id 权限ID
     * @return JsonResult
     */
    @GetMapping("/{id}")
    public JsonResult findOne(@PathVariable Long id){
        Permission permission = permissionService.getById(id);
        return permission == null ? JsonResult.error("获取失败") : JsonResult.success(permission);
    }

    /**
     * 获取权限树
     * @return 树
     */
    @GetMapping("/findPermissionTree")
    public JsonResult findListByParentId(){
        List<PermissionTreeNode> tree = permissionService.findPermissionTree();
        return JsonResult.success(tree);
    }

    @GetMapping("/findMenuList")
    public JsonResult findMenuList(){
        List<Permission> menu = permissionService.findMenuList();
        return JsonResult.success(menu);
    }

}
