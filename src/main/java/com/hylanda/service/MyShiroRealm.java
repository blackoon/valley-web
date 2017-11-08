package com.hylanda.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import com.hylanda.entity.UPermission;
import com.hylanda.entity.URole;
import com.hylanda.entity.UUser;

/** 
 * @author zhangy
 * @E-mail:blackoon88@gmail.com 
 * @qq:846579287
 * @version created at：2017年11月6日 上午10:48:46 
 * 在认证、授权内部实现机制中都有提到，最终处理都将交给Real进行处理。因为在Shiro中，最终是通过Realm来获取应用程序中的用户、角色及权限信息的。通常情况下，在Realm中会直接从我们的数据源中获取Shiro需要的验证信息。可以说，Realm是专用于安全框架的DAO.

Shiro的认证过程最终会交由Realm执行，这时会调用Realm的getAuthenticationInfo(token)方法。
该方法主要执行以下操作:

1、检查提交的进行认证的令牌信息

2、根据令牌信息从数据源(通常为数据库)中获取用户信息

3、对用户信息进行匹配验证。

4、验证通过将返回一个封装了用户信息的AuthenticationInfo实例。

5、验证失败则抛出AuthenticationException异常信息。

 */
//@Service
public class MyShiroRealm extends AuthorizingRealm{
	@Autowired
	private UUserService sysUserService;
	
	@Autowired
	private URoleService uRoleService;
	
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	
	
	//用户登录次数计数  redisKey 前缀

	public String SHIRO_LOGIN_COUNT = "shiro_login_count_";
		
	//用户登录是否被锁定    一小时 redisKey 前缀

	public String SHIRO_IS_LOCK = "shiro_is_lock_";
	/**
	 * 认证信息.(身份验证) : Authentication 是用来验证用户身份
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		System.out.println("身份认证方法：MyShiroRealm.doGetAuthenticationInfo()");
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
	    // 从数据库获取对应用户名密码的用户
	    UUser user = sysUserService.findByUsername(token.getUsername(),token.getPassword());
	    if (null == user) {
	        throw new AccountException("帐号或密码不正确！");
	    }else if(user.getStatus()==0){
	        /**
	         * 如果用户的status为禁用。那么就抛出<code>DisabledAccountException</code>
	         */
	        throw new DisabledAccountException("帐号已经禁止登录！");
	    }else{
	        //更新登录时间 last login time
	        user.setLastLoginTime(new Date());
	        sysUserService.update(user);
	    }
	    return new SimpleAuthenticationInfo(user, user.getPswd(), getName());
	}
	/**
	 * 当访问到页面的时候，链接配置了相应的权限或者shiro标签才会执行此方法否则不会执行，所以如果只是简单的身份认证没有权限的控制的话，那么这个方法可以不进行实现，直接返回null即可。

	在这个方法中主要是使用类：SimpleAuthorizationInfo
	
	进行角色的添加和权限的添加。
	
	authorizationInfo.addRole(role.getRole());
	
	authorizationInfo.addStringPermission(p.getPermission());
	
	当然也可以添加set集合：roles是从数据库查询的当前用户的角色，stringPermissions是从数据库查询的当前用户对应的权限
	
	authorizationInfo.setRoles(roles);
	
	authorizationInfo.setStringPermissions(stringPermissions);
	
	就是说如果在shiro配置文件中添加了filterChainDefinitionMap.put("/add", "perms[权限添加]");
	就说明访问/add这个链接必须要有“权限添加”这个权限才可以访问，
	
	如果在shiro配置文件中添加了filterChainDefinitionMap.put("/add", "roles[100002]，perms[权限添加]");
	就说明访问/add这个链接必须要有“权限添加”这个权限和具有“100002”这个角色才可以访问。

	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("权限认证方法：MyShiroRealm.doGetAuthenticationInfo()");
		UUser token = (UUser)SecurityUtils.getSubject().getPrincipal();
	    Long userId = token.getId();
	    SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();
	    //根据用户ID查询角色（role），放入到Authorization里。
	    List<URole> roleList = sysUserService.findByUserId(userId);
	    Set<String> roleSet = new HashSet<String>();
	    for(URole role : roleList){
	        roleSet.add(role.getType());
	    }
	    info.setRoles(roleSet);
	    //根据用户ID查询权限（permission），放入到Authorization里。 
	    List<UPermission> permissionList = uRoleService.findByRoleIds(roleList);
	    Set<String> permissionSet = new HashSet<String>();
	    for(UPermission Permission : permissionList){
	        permissionSet.add(Permission.getName());
	    }
	    info.setStringPermissions(permissionSet);
	    return info;
	}
}
