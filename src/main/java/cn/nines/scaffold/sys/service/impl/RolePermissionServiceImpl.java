package cn.nines.scaffold.sys.service.impl;

import cn.nines.scaffold.sys.entity.RolePermission;
import cn.nines.scaffold.sys.mapper.RolePermissionMapper;
import cn.nines.scaffold.sys.service.RolePermissionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 角色与权限关系表 服务实现类
 * </p>
 *
 * @author Nines
 * @since 2019-11-25
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {

    @Resource
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public List<RolePermission> findListByRid(Long rid) {
        QueryWrapper<RolePermission> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", rid);
        return rolePermissionMapper.selectList(wrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean modifyUserRole(Long roleId, List<RolePermission> rolePermissions) {
        // 删除原表角色权限
        QueryWrapper<RolePermission> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", roleId);
        rolePermissionMapper.delete(wrapper);

        if (rolePermissions.size() > 0){
            // 迭代添加日期
            rolePermissions.forEach(rolePermission -> {
                rolePermission.setCreateTime(LocalDateTime.now());
                rolePermission.setUpdateTime(LocalDateTime.now());
            });

            // 新增角色权限 并返回处理结果
            return rolePermissionMapper.insertAllBatch(rolePermissions);
        }else {
            return true;
        }
    }
}
