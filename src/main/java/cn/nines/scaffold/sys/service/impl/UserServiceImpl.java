package cn.nines.scaffold.sys.service.impl;

import cn.nines.scaffold.common.util.PasswordSaltUtil;
import cn.nines.scaffold.sys.entity.User;
import cn.nines.scaffold.sys.mapper.UserMapper;
import cn.nines.scaffold.sys.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
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
//        user.setStatus(true);
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

    @Override
    public boolean updateStatus(Long id) {
        User user = userMapper.selectById(id);
        if (user != null){
            if (user.getStatus()){
                user.setStatus(false);
            }else {
                user.setStatus(true);
            }
            user.setUpdateTime(LocalDateTime.now());
            int result = userMapper.updateById(user);
            return result > 0;
        }
        return false;
    }

    @Override
    public Map findPage(String username, int current, int size) {
        Page<User> page = new Page<>(current, size);

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(username)){
            wrapper.like("username", username);
        }

        IPage<User> userPage = userMapper.selectPage(page, wrapper);

        Map<String, Object> map = new HashMap<>(3);
        map.put("pages", userPage.getPages());
        map.put("total", userPage.getTotal());
        map.put("rows", userPage.getRecords());
        return map;
    }
}
