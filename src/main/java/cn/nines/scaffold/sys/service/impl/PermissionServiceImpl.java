package cn.nines.scaffold.sys.service.impl;

import cn.nines.scaffold.sys.entity.Permission;
import cn.nines.scaffold.sys.entity.RolePermission;
import cn.nines.scaffold.sys.mapper.PermissionMapper;
import cn.nines.scaffold.sys.mapper.RolePermissionMapper;
import cn.nines.scaffold.sys.service.PermissionService;
import cn.nines.scaffold.sys.service.RolePermissionService;
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
 * 权限表 服务实现类
 * </p>
 *
 * @author Nines
 * @since 2019-11-25
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private RolePermissionMapper rolePermissionMapper;

    @Resource
    private RolePermissionService rolePermissionService;

    @Override
    public boolean addPermission(Permission permission) {
        permission.setCreateTime(LocalDateTime.now());
        permission.setUpdateTime(LocalDateTime.now());
        int result = permissionMapper.insert(permission);
        return result > 0;
    }

    @Override
    public boolean updatePermission(Permission permission) {
        Permission previousPermission = permissionMapper.selectById(permission.getId());
        previousPermission.setName(permission.getName());
        previousPermission.setParentId(permission.getParentId());
        previousPermission.setRemark(permission.getRemark());
        previousPermission.setType(permission.getType());
        previousPermission.setSort(permission.getSort());
        previousPermission.setUrl(permission.getUrl());
        previousPermission.setUpdateTime(LocalDateTime.now());

        int result = permissionMapper.updateById(previousPermission);
        return result > 0;
    }

    private boolean updatePermissionAndRolePermissionByPermissionId(Long id, boolean status){
        Permission permission = permissionMapper.selectById(id);
        if (permission != null){
            try {
                // // 冻结权限（恢复权限）
                permission.setStatus(status);
                permission.setUpdateTime(LocalDateTime.now());
                permissionMapper.updateById(permission);

                // 冻结角色权限表中的关联数据（恢复角色权限表中的关联数据） 权限相关角色
                QueryWrapper<RolePermission> wrapper = new QueryWrapper<>();
                wrapper.eq("permission_id", id);
                List<RolePermission> rolePermissions = rolePermissionMapper.selectList(wrapper);

                rolePermissions.forEach(rolePermission -> {
                    rolePermission.setStatus(status);
                    rolePermission.setUpdateTime(LocalDateTime.now());
                });
                rolePermissionService.updateBatchById(rolePermissions);
                return true;
            }catch (Exception e){
                return false;
            }
        }
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean freezePermissionAndRolePermissionByPermissionId(Long id) {
        return updatePermissionAndRolePermissionByPermissionId(id, false);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean recoverPermissionAndRolePermissionByPermissionId(Long id) {
        return updatePermissionAndRolePermissionByPermissionId(id, true);
    }

    @Override
    public Map findPage(String searchText, int current, int size) {
        Page<Permission> page = new Page<>(current, size);

        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(searchText)){
            wrapper.like("name", searchText);
        }

        IPage<Permission> userPage = permissionMapper.selectPage(page, wrapper);

        Map<String, Object> map = new HashMap<>(3);
        map.put("pages", userPage.getPages());
        map.put("total", userPage.getTotal());
        map.put("rows", userPage.getRecords());
        return map;
    }
}
