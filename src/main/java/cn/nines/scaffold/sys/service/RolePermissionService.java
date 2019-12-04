package cn.nines.scaffold.sys.service;

import cn.nines.scaffold.sys.entity.RolePermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色与权限关系表 服务类
 * </p>
 *
 * @author Nines
 * @since 2019-11-25
 */
public interface RolePermissionService extends IService<RolePermission> {

    /**
     * 通过rid查找List
     * @param rid 角色id
     * @return List<RolePermission>
     */
    List<RolePermission> findListByRid(Long rid);

    /**
     * 修改用户角色
     * @param rolePermissions 角色权限列表
     * @return 成功与否
     */
    boolean modifyUserRole(Long roleId, List<RolePermission> rolePermissions);
}
