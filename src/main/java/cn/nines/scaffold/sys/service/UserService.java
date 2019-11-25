package cn.nines.scaffold.sys.service;

import cn.nines.scaffold.sys.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

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
     * 修改用户状态(有效切无效，无效切有效)
     * @param id 用户ID
     * @return 成功与否
     */
    boolean updateStatus(Long id);
}
