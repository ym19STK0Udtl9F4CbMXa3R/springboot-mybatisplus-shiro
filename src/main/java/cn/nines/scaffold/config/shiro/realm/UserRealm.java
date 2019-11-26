package cn.nines.scaffold.config.shiro.realm;

import cn.nines.scaffold.sys.entity.User;
import cn.nines.scaffold.sys.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * <p>
 * Shiro 配置类
 * </p>
 *
 * @author Nines
 * @since 2019-11-25
 */
public class UserRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(UserRealm.class);

    @Resource
    UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("执行授权逻辑！");
        User userInfo = (User) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

//        for (SysRole role : userInfo.getRoles()) {
//            // 添加角色
//            simpleAuthorizationInfo.addRole(role.getName());
//            for (SysPermission permission : role.getPermissions()) {
//                // 添加权限
//                simpleAuthorizationInfo.addStringPermission(permission.getName());
//            }
//        }
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("执行认证逻辑！");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = userService.getUserByUsername(token.getUsername());

        if (user == null){
            return null;
        }

        return new SimpleAuthenticationInfo(
                //  user对象
                user,
                //  数据库密码
                user.getPassword(),
                // 数据库盐值
                ByteSource.Util.bytes(user.getSalt()),
                // 当前 Realm 的名称
                getName());
    }
}
