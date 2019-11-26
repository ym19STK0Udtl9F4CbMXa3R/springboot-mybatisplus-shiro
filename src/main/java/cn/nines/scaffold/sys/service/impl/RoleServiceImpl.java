package cn.nines.scaffold.sys.service.impl;

import cn.nines.scaffold.sys.entity.Role;
import cn.nines.scaffold.sys.mapper.RoleMapper;
import cn.nines.scaffold.sys.service.RoleService;
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

    @Override
    public boolean updateStatus(Long id) {
        Role role = roleMapper.selectById(id);
        if (role != null){
            if (role.getStatus()){
                role.setStatus(false);
            }else {
                role.setStatus(true);
            }
            role.setUpdateTime(LocalDateTime.now());
            int result = roleMapper.updateById(role);
            return result > 0;
        }
        return false;
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
