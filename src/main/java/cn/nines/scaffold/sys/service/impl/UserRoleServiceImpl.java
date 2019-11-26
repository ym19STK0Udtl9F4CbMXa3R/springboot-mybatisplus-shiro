package cn.nines.scaffold.sys.service.impl;

import cn.nines.scaffold.sys.entity.UserRole;
import cn.nines.scaffold.sys.mapper.UserRoleMapper;
import cn.nines.scaffold.sys.service.UserRoleService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 用户与角色关系表 服务实现类
 * </p>
 *
 * @author Nines
 * @since 2019-11-25
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private UserRoleService userRoleService;

    @Override
    public List<UserRole> findListByUid(Long uid) {
        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", uid);
        return userRoleMapper.selectList(wrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean modifyUserRole(List<UserRole> userRoles) {
        // 删除该用户在用户角色表中的原数据
        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userRoles.get(0).getUserId());
        userRoleMapper.delete(wrapper);

        // 迭代添加日期
        userRoles.forEach(userRole -> {
            userRole.setCreateTime(LocalDateTime.now());
            userRole.setUpdateTime(LocalDateTime.now());
        });

        // 批量新增角色数据并返回
        return userRoleService.saveBatch(userRoles);
    }
}
