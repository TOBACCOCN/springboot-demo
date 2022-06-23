package com.springboot.example.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashSet;
import java.util.Set;

/**
 * shiro 认证授权中心
 *
 * @author zhangyonghong
 * @date 2019.6.3
 */
public class SimpleAuthorizingRealm extends AuthorizingRealm {

    @Value("${shiro.login.unknown_account}")
    private boolean unknownAccount;
    @Value("${shiro.login.incorrect_credentials}")
    private boolean incorrectCredentials;
    @Value("${shiro.login.locked_account}")
    private boolean lockedAccount;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Set<String> roles = new HashSet<>();
        Set<String> permissions = new HashSet<>();

        // TODO
        //  获取角色和权限信息分别存到 roles 和 permissions

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(permissions);
        simpleAuthorizationInfo.setRoles(roles);
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());

        // TODO
        //  根据用户民查询出用户信息，为空抛异常 UnknownAccountException
        //  然后比对密码，不匹配抛异常 IncorrectCredentialsException
        //  检验用户状态，状态不可用抛异常 LockedAccountException
        if (unknownAccount) {
            throw new UnknownAccountException();
        }
        if (incorrectCredentials) {
            throw new IncorrectCredentialsException();
        }
        if (lockedAccount) {
            throw new LockedAccountException();
        }

        return new SimpleAuthenticationInfo(username, password, getName());
    }

}
