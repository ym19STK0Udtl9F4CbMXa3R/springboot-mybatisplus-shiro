package cn.nines.scaffold.sys.mapper;

import cn.nines.scaffold.sys.entity.RolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 角色与权限关系表 Mapper 接口
 * </p>
 *
 * @author Nines
 * @since 2019-11-25
 */
@Repository
public interface RolePermissionMapper extends BaseMapper<RolePermission> {

}
