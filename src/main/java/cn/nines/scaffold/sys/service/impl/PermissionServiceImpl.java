package cn.nines.scaffold.sys.service.impl;

import cn.nines.scaffold.sys.entity.Permission;
import cn.nines.scaffold.sys.mapper.PermissionMapper;
import cn.nines.scaffold.sys.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
