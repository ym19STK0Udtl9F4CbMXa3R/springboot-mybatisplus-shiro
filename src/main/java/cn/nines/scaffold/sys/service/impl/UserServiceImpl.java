package cn.nines.scaffold.sys.service.impl;

import cn.nines.scaffold.common.util.PasswordSaltUtil;
import cn.nines.scaffold.sys.entity.User;
import cn.nines.scaffold.sys.entity.UserRole;
import cn.nines.scaffold.sys.mapper.UserMapper;
import cn.nines.scaffold.sys.mapper.UserRoleMapper;
import cn.nines.scaffold.sys.service.UserRoleService;
import cn.nines.scaffold.sys.service.UserService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author Nines
 * @since 2019-11-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private UserRoleService userRoleService;

    @Override
    public User getUserByUsername(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public boolean addUser(User user) {
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
//        user.setStatus(true); 数据库添加该字段默认为 1
        int result = userMapper.insert(PasswordSaltUtil.md5(user));
        return result > 0;
    }

    @Override
    public boolean updateUser(User user) {
        User previousUser = userMapper.selectById(user.getId());
        previousUser.setNickName(user.getNickName());
        previousUser.setEmail(user.getEmail());
        previousUser.setUpdateTime(LocalDateTime.now());

        int result = userMapper.updateById(previousUser);
        return result > 0;
    }

    private boolean updateUserByIdAndRole(Long id, boolean status){
        // 冻结用户（恢复用户）
        User user = userMapper.selectById(id);
        if (user != null){
            user.setStatus(status);
            user.setUpdateTime(LocalDateTime.now());
            userMapper.updateById(user);

            // 冻结用户角色表中的关联数据（恢复用户角色表中的关联数据）
            QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id", id);
            List<UserRole> userRoles = userRoleMapper.selectList(wrapper);

            userRoles.forEach(userRole -> {
                userRole.setStatus(status);
                userRole.setUpdateTime(LocalDateTime.now());
            });

            userRoleService.updateBatchById(userRoles);
            return true;
        }
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean freezeUserAndUserRoleByUserId(Long id) {
        return updateUserByIdAndRole(id, false);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean recoverUserAndUserRoleByUserId(Long id) {
        return updateUserByIdAndRole(id, true);
    }

    @Override
    public Map findPage(String searchText, int current, int size) {
        Page<User> page = new Page<>(current, size);

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(searchText)){
            wrapper.like("username", searchText);
        }

        IPage<User> userPage = userMapper.selectPage(page, wrapper);

        Map<String, Object> map = new HashMap<>(3);
        map.put("pages", userPage.getPages());
        map.put("total", userPage.getTotal());
        map.put("rows", userPage.getRecords());
        return map;
    }
}
