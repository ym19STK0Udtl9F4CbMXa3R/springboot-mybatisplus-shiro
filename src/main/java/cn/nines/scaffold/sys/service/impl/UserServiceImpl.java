package cn.nines.scaffold.sys.service.impl;

import cn.nines.scaffold.sys.entity.User;
import cn.nines.scaffold.sys.mapper.UserMapper;
import cn.nines.scaffold.sys.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
