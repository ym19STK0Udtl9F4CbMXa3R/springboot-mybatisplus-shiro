package cn.nines.scaffold.sys.service;

import cn.nines.scaffold.sys.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户与角色关系表 服务类
 * </p>
 *
 * @author Nines
 * @since 2019-11-25
 */
public interface UserRoleService extends IService<UserRole> {

    /**
     * 通过uid查找List
     * @param uid 用户id
     * @return List<UserRole>
     */
    List<UserRole> findListByUid(Long uid);

    /**
     * 修改用户角色
     * @param userRoles 用户角色列表
     * @return 成功与否
     */
    boolean modifyUserRole(List<UserRole> userRoles);

}
