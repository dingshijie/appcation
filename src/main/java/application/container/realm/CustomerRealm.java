package application.container.realm;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerRealm extends AuthorizingRealm {
	private static Logger logger = LoggerFactory.getLogger(CustomerRealm.class);

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		if (principals == null) {
			throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
		}
		String username = (String) getAvailablePrincipal(principals);

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		try {
			// Retrieve roles and permissions from database

			//添加角色和权限
			if (username.equals("admin")) {
				info.addRole("admin");
			} else if (username.equals("user")) {
				info.addRole("user");
			} else {
				info.addRole("anonymous");
			}
			//			info.setRoles(roles);
			//			info.setStringPermissions(permissions);
		} catch (Exception e) {
			final String message = "There was a error while authorizing user [" + username + "]";
			logger.error(message, e);
			throw new AuthorizationException(message, e);
		}
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String username = upToken.getUsername();

		String password = String.valueOf(upToken.getPassword());

		//如果项目中密码或用户等有特殊处理（如加密等），可以在这里将输入的密码用户等upToken中，做相应的处理

		if (username == null) {
			throw new AccountException("Null usernames are not allowed by this realm.");
		}
		if (password == null) {
			throw new UnknownAccountException("No account found for user [" + username + "]");
		}

		SimpleAuthenticationInfo info = null;
		try {
			//这里为正确的用户对于的密码
			//或从文件配置或从数据库取出
			info = new SimpleAuthenticationInfo(username, "123456", getName());

		} catch (Exception e) {
			final String message = "There was a error while authenticating user [" + username + "]";
			logger.error(message, e);
			// Rethrow any SQL errors as an authentication exception
			throw new AuthenticationException(message, e);
		}
		return info;
	}

}
