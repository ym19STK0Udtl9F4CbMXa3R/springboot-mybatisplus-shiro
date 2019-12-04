package cn.nines.scaffold.sys.controller;


import cn.nines.scaffold.common.result.ResponseJson;
import cn.nines.scaffold.common.result.PageRequest;
import cn.nines.scaffold.sys.entity.Permission;
import cn.nines.scaffold.sys.entity.PermissionTreeNode;
import cn.nines.scaffold.sys.service.PermissionService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
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
@RequiresAuthentication
@RestController
@RequestMapping("/sys/permission")
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    /**
     * 添加权限
     * @param permission 权限
     * @return ResponseJson
     */
    @PostMapping("/add")
    public ResponseJson addPermission(@RequestBody Permission permission){
        return permissionService.addPermission(permission) ? ResponseJson.success("添加成功") : ResponseJson.error("添加失败");
    }

    /**
     * 修改权限
     * @param permission 权限
     * @return ResponseJson
     */
    @PutMapping("/update")
    public ResponseJson updatePermission(@RequestBody Permission permission){
        return permissionService.updatePermission(permission) ? ResponseJson.success("更新成功") : ResponseJson.error("更新失败");
    }

    /**
     * 冻结权限
     * @param id 权限ID
     * @return ResponseJson
     */
    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("/{id}")
    public ResponseJson deletePermission(@PathVariable Long id){
        return permissionService.freezePermissionAndRolePermissionByPermissionId(id) ? ResponseJson.success("操作成功") : ResponseJson.error("操作失败");
    }

    /**
     * 恢复权限
     * @param id 权限ID
     * @return ResponseJson
     */
    @Transactional(rollbackFor = Exception.class)
    @PutMapping("/{id}")
    public ResponseJson recoverPermission(@PathVariable Long id){
        return permissionService.recoverPermissionAndRolePermissionByPermissionId(id) ? ResponseJson.success("操作成功") : ResponseJson.error("操作失败");
    }

    /**
     * 分页信息
     * @param page 分页参数
     * @return ResponseJson
     */
    @PostMapping("/findPage")
    public ResponseJson findPage(@RequestBody PageRequest page){
        return ResponseJson.success(permissionService.findPage(page.getSearchText(), page.getCurrent(), page.getSize()));
    }

    /**
     * 通过ID获取权限详情
     * @param id 权限ID
     * @return ResponseJson
     */
    @GetMapping("/{id}")
    public ResponseJson findOne(@PathVariable Long id){
        Permission permission = permissionService.getById(id);
        return permission == null ? ResponseJson.error("获取失败") : ResponseJson.success(permission);
    }

    /**
     * 获取权限树
     * @return 树
     */
    @GetMapping("/findPermissionTree")
    public ResponseJson findListByParentId(){
        List<PermissionTreeNode> tree = permissionService.findPermissionTree();
        return ResponseJson.success(tree);
    }

    @GetMapping("/findMenuList")
    public ResponseJson findMenuList(){
        List<Permission> menu = permissionService.findMenuList();
        return ResponseJson.success(menu);
    }

}
