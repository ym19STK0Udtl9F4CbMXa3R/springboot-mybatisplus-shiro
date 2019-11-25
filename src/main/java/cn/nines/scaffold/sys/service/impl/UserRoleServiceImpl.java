package cn.nines.scaffold.sys.service.impl;

import cn.nines.scaffold.sys.entity.UserRole;
import cn.nines.scaffold.sys.mapper.UserRoleMapper;
import cn.nines.scaffold.sys.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
