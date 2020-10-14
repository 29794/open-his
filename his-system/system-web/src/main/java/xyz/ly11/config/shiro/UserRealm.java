package xyz.ly11.config.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import xyz.ly11.domain.User;
import xyz.ly11.service.UserService;
import xyz.ly11.vo.ActiveUser;

/**
 * @author by 29794
 * @date 2020/10/9 20:15
 */
public class UserRealm  extends AuthorizingRealm {

    @Autowired
    @Lazy
    UserService userService;

    @Override
    public String getName(){
        return this.getClass().getSimpleName();
    }

    /**
     * 认证 --登录
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //得到用户登陆名
        String phone=token.getPrincipal().toString();
        //根据电话查询用户是否存在
        User user = userService.queryUserByPhone(phone);
        if(null!=user){
            //说明用户存在，但是密码可能不正确
            //组存放到redis里面的对象
            ActiveUser activeUser = new ActiveUser();
            activeUser.setUser(user);
            //匹配密码
            return new SimpleAuthenticationInfo(
                    activeUser,user.getPassword(), ByteSource.Util.bytes(user.getSalt()), this.getName()
            );
        }else{
            //代表用户不存在
            return null;
        }

    }

    /**
     * 授权 --登录之后的权限校验，是否有某个菜单或按钮的权限
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //身份得到的就是上一个方法的返回值的值 第一个参数activeUser
        ActiveUser activeUser= (ActiveUser) principals.getPrimaryPrincipal();
        return new SimpleAuthorizationInfo();

    }
}
