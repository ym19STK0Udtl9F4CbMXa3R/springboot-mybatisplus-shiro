package cn.nines.scaffold.config.shiro.realm;

import cn.nines.scaffold.common.util.JWTUtil;
import cn.nines.scaffold.config.shiro.JWTToken;
import cn.nines.scaffold.sys.entity.RolePermission;
import cn.nines.scaffold.sys.entity.User;
import cn.nines.scaffold.sys.entity.UserRole;
import cn.nines.scaffold.sys.service.*;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @ClassName: MyRealm
 * @Description: 自定义 MyRealm 实现 Shiro Realm 的登录控制
 *              1. 重写 Realm 的 supports() 方法是通过 JWT 进行登录判断的关键
 *                   因为前文中创建了 JWTToken 用于替换 Shiro 原生 token
 *                   所以必须在此方法中显式的进行替换，否则在进行判断时会一直失败
 *              2. 登录的合法验证通常包括 token 是否有效 、用户名是否存在 、密码是否正确
 *                  通过 JWTUtil 对前端传入的 token 进行处理获取到用户名
 *                  然后使用用户名前往数据库获取到用户密码
 *                  再将用户面传入 JWTUtil 进行验证即可
 * @author: Nines
 * @date: 2019年12月03日 19:53
 */
@Service
public class MyRealm extends AuthorizingRealm {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private UserService userService;

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private RolePermissionService rolePermissionService;

    @Resource
    private PermissionService permissionService;

    /**
     * 使用JWT替代原生Token
     * 大坑！，必须重写此方法，不然Shiro会报错
     *
     * @param token 密钥
     * @return boolean
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = JWTUtil.getUsername(principals.toString());
        User user = userService.getUserByUsername(username);

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        // 获取用户角色拥有的权限ID
        List<UserRole> userRoles = userRoleService.findListByUid(user.getId());
        List<Long> permissionIdList = new ArrayList<>();
        userRoles.forEach(userRole -> {
            List<RolePermission> rolePermissions = rolePermissionService.findListByRid(userRole.getRoleId());
            rolePermissions.forEach(rolePermission -> permissionIdList.add(rolePermission.getPermissionId()));
        });

        // 通过权限ID获取权限
        Set<String> permissions = new HashSet<>();
        permissionIdList.forEach(permissionId -> {
            String permCode = permissionService.getById(permissionId).getPermCode();
            if (permCode != null){
                permissions.add(permCode);
            }
        });
        System.out.println(permissions);

//        simpleAuthorizationInfo.addRole(user.getRole());
        simpleAuthorizationInfo.addStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
        String token = (String) authToken.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = JWTUtil.getUsername(token);
        if (username == null) {
//            throw new AuthenticationException("token invalid");
            throw new AuthenticationException("无效的身份认证");
        }

        User user = userService.getUserByUsername(username);
        if (user == null) {
//            throw new AuthenticationException("User didn't existed!");
            throw new AuthenticationException("账号不存在");
        }

        if (! JWTUtil.verify(token, username, user.getPassword())) {
//            throw new AuthenticationException("Username or password error");
            throw new AuthenticationException("用户名或密码错误");
        }

        return new SimpleAuthenticationInfo(token, token, "my_realm");
    }

}
