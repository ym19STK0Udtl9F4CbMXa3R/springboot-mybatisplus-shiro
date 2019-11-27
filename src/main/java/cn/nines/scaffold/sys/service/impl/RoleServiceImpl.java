package cn.nines.scaffold.sys.service.impl;

import cn.nines.scaffold.sys.entity.Role;
import cn.nines.scaffold.sys.entity.RolePermission;
import cn.nines.scaffold.sys.entity.UserRole;
import cn.nines.scaffold.sys.mapper.RoleMapper;
import cn.nines.scaffold.sys.mapper.RolePermissionMapper;
import cn.nines.scaffold.sys.mapper.UserRoleMapper;
import cn.nines.scaffold.sys.service.RolePermissionService;
import cn.nines.scaffold.sys.service.RoleService;
import cn.nines.scaffold.sys.service.UserRoleService;
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
 * 角色 服务实现类
 * </p>
 *
 * @author Nines
 * @since 2019-11-25
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private RolePermissionMapper rolePermissionMapper;

    @Resource
    private RolePermissionService rolePermissionService;

    @Override
    public boolean addRole(Role role) {
        role.setCreateTime(LocalDateTime.now());
        role.setUpdateTime(LocalDateTime.now());
        int result = roleMapper.insert(role);
        return result > 0;
    }

    @Override
    public boolean updateRole(Role role) {
        Role previousRole = roleMapper.selectById(role.getId());
        previousRole.setName(role.getName());
        previousRole.setRemark(role.getRemark());
        previousRole.setUpdateTime(LocalDateTime.now());
        int result = roleMapper.updateById(previousRole);
        return result > 0;
    }

    private boolean updateRoleAndRolePermissionByRoleId(Long id, boolean status){
        // 冻结角色（恢复角色）
        Role role = roleMapper.selectById(id);
        if (role != null){
            role.setStatus(status);
            role.setUpdateTime(LocalDateTime.now());
            roleMapper.updateById(role);

            // 冻结用户角色表中的关联数据（恢复用户角色表中的关联数据）角色相关用户
            QueryWrapper<UserRole> userRoleWrapper = new QueryWrapper<>();
            userRoleWrapper.eq("role_id", role.getId());
            List<UserRole> userRoles = userRoleMapper.selectList(userRoleWrapper);

            userRoles.forEach(userRole -> {
                userRole.setStatus(status);
                userRole.setUpdateTime(LocalDateTime.now());
            });
            userRoleService.updateBatchById(userRoles);

            // 冻结角色权限表中的关联数据（恢复角色权限表中的关联数据） 角色相关权限
            QueryWrapper<RolePermission> rolePermissionWrapper = new QueryWrapper<>();
            rolePermissionWrapper.eq("role_id", role.getId());
            List<RolePermission> rolePermissions = rolePermissionMapper.selectList(rolePermissionWrapper);

            rolePermissions.forEach(rolePermission -> {
                rolePermission.setStatus(status);
                rolePermission.setUpdateTime(LocalDateTime.now());
            });
            rolePermissionService.updateBatchById(rolePermissions);

            return true;
        }
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean freezeRoleAndRolePermissionByRoleId(Long id) {
        return updateRoleAndRolePermissionByRoleId(id, false);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean recoverRoleAndRolePermissionByRoleId(Long id) {
        return updateRoleAndRolePermissionByRoleId(id, true);
    }

    @Override
    public Map findPage(String searchText, int current, int size) {
        Page<Role> page = new Page<>(current, size);

        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(searchText)){
            wrapper.like("name", searchText);
        }

        IPage<Role> userPage = roleMapper.selectPage(page, wrapper);

        Map<String, Object> map = new HashMap<>(3);
        map.put("pages", userPage.getPages());
        map.put("total", userPage.getTotal());
        map.put("rows", userPage.getRecords());
        return map;
    }
}
