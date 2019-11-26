package cn.nines.scaffold.sys.service;

import cn.nines.scaffold.sys.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author Nines
 * @since 2019-11-25
 */
public interface RoleService extends IService<Role> {

    /**
     * 添加角色
     * @param role 角色
     * @return 成功与否
     */
    boolean addRole(Role role);

    /**
     * 更新角色
     * @param role 角色
     * @return 成功与否
     */
    boolean updateRole(Role role);

    /**
     * 修改角色状态(有效切无效，无效切有效)
     * @param id 角色ID
     * @return 成功与否
     */
    boolean updateStatus(Long id);


    /**
     * 获取角色列表
     * @param searchText 角色名
     * @param current   当前页
     * @param size  每页显示条数
     * @return map
     */
    Map findPage(String searchText, int current, int size);

}
