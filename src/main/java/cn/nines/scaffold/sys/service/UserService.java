package cn.nines.scaffold.sys.service;

import cn.nines.scaffold.sys.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author Nines
 * @since 2019-11-25
 */
public interface UserService extends IService<User> {

    /**
     *  通过用户名得到用户
     * @param username 用户名
     * @return 用户
     */
    User getUserByUsername(String username);

    /**
     * 添加用户
     * @param user 用户
     * @return 成功与否
     */
    boolean addUser(User user);

    /**
     * 更新用户
     * @param user 用户
     * @return 成功与否
     */
    boolean updateUser(User user);

    /**
     * 冻结用户
     * @param id 用户ID
     * @return 成功与否
     */
    boolean freezeUserAndUserRoleByUserId(Long id);

    /**
     * 恢复用户
     * @param id 用户ID
     * @return 成功与否
     */
    boolean recoverUserAndUserRoleByUserId(Long id);

    /**
     * 获取用户列表
     * @param searchText 用户名
     * @param current   当前页
     * @param size  每页显示条数
     * @return map
     */
    Map findPage(String searchText, int current, int size);

    /**
     * 通过ID获取用户
     * @param id 用户ID
     * @return user
     */
    User findOne(Long id);

}
