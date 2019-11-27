package cn.nines.scaffold.sys.service.impl;

import cn.nines.scaffold.sys.entity.Permission;
import cn.nines.scaffold.sys.mapper.PermissionMapper;
import cn.nines.scaffold.sys.service.PermissionService;
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
        previousPermission.setUrl(permission.getUrl());
        previousPermission.setUpdateTime(LocalDateTime.now());

        int result = permissionMapper.updateById(previousPermission);
        return result > 0;
    }

    @Override
    public boolean updateStatus(Long id) {
        return false;
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
