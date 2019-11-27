package cn.nines.scaffold.sys.service;

import cn.nines.scaffold.sys.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author Nines
 * @since 2019-11-25
 */
public interface PermissionService extends IService<Permission> {

    /**
     * 添加权限
     * @param permission 权限
     * @return 成功与否
     */
    boolean addPermission(Permission permission);

    /**
     * 更新权限
     * @param permission 权限
     * @return 成功与否
     */
    boolean updatePermission(Permission permission);

    /**
     * 修改权限状态(有效切无效，无效切有效)
     * @param id 权限ID
     * @return 成功与否
     */
    boolean updateStatus(Long id);


    /**
     * 获取权限列表
     * @param searchText 权限名
     * @param current   当前页
     * @param size  每页显示条数
     * @return map
     */
    Map findPage(String searchText, int current, int size);

}
