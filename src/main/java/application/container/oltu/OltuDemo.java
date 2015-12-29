package application.container.oltu;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * apache oltu 服务端示例
 * @author ding_shijie
 *
 */
@Controller
@RequestMapping("/oltu")
public class OltuDemo {

	private static Logger logger = LoggerFactory.getLogger(OltuDemo.class);
	//client id
	private static String CLIENT_ID = "test";
	//client_secert
	private static String CLIENT_SECERT = "HJ$Ggd54";
	//生成的code ,暂时存到内存
	private static String RESPONSE_CODE = "";
	//生成的accessToken ,暂时放到内存
	private static String ACCESS_TOKEN = "";
	//过期时间一个月
	private int EXPIRE_IN = 30 * 24 * 60 * 60;

	@Autowired
	private AuthorizeService authorizeService;

	@RequestMapping(value = "login.html", method = RequestMethod.GET)
	public String oltu() {
		System.out.println("oltu login page");
		return "oltu/login";
	}

	@RequestMapping(value = "logout.html", method = RequestMethod.GET)
	public String logout() {
		System.out.println("login page");

		SecurityUtils.getSubject().logout();
		return "oltu/login";
	}

	@RequestMapping(value = "oauth2login.html", method = RequestMethod.GET)
	public String oauth2login() {
		System.out.println("oauth2 login page");

		return "oltu/oauth2login";
	}

	@RequestMapping(value = "authorize.html", method = RequestMethod.GET)
	public String authorize() {
		System.out.println("authorize page");

		return "oltu/authorize";
	}

	@RequestMapping(value = "result.html", method = RequestMethod.GET)
	public String result() {
		System.out.println("result page");

		return "oltu/result";
	}

	@RequestMapping(value = "/authorize", method = RequestMethod.POST)
	public Object Authorize(Model model, HttpServletRequest request) throws Exception {

		try {
			//构建OAuth 授权请求  
			OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);
			//检查传入的客户端id是否正确,暂时默认为TEST本应该去数据库判断的
			if (!oauthRequest.getClientId().equals(CLIENT_ID)) {
				OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
						.setError(OAuthError.TokenResponse.INVALID_CLIENT)
						/*.setErrorDescription(Constants.INVALID_CLIENT_DESCRIPTION)*/.buildJSONMessage();
				return new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
			}

			Subject subject = SecurityUtils.getSubject();
			//如果用户没有登录，跳转到登陆页面  
			if (!subject.isAuthenticated()) {
				if (!login(subject, request)) {//登录失败时跳转到登陆页面  
					return "oltu/oauth2login";
				}
			}

			String username = (String) subject.getPrincipal();
			//生成授权码  
			String authorizationCode = null;
			//responseType目前仅支持CODE，另外还有TOKEN  
			String responseType = oauthRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE);
			if (responseType.equals(ResponseType.CODE.toString())) {
				OAuthIssuerImpl oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
				authorizationCode = oauthIssuerImpl.authorizationCode();
				//应将authorizationCode存入到数据库，这里暂时用变量代替
				RESPONSE_CODE = authorizationCode;
			}
			//进行OAuth响应构建  
			OAuthASResponse.OAuthAuthorizationResponseBuilder builder = OAuthASResponse.authorizationResponse(request,
					HttpServletResponse.SC_FOUND);
			//设置授权码  
			builder.setCode(authorizationCode);
			//得到到客户端重定向地址  
			String redirectURI = oauthRequest.getParam(OAuth.OAUTH_REDIRECT_URI);

			//构建响应  
			final OAuthResponse response = builder.location(redirectURI).buildQueryMessage();
			//根据OAuthResponse返回ResponseEntity响应  
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(new URI(response.getLocationUri()));
			return new ResponseEntity(headers, HttpStatus.valueOf(response.getResponseStatus()));
		} catch (OAuthProblemException e) {
			//出错处理  
			String redirectUri = e.getRedirectUri();
			if (OAuthUtils.isEmpty(redirectUri)) {
				//告诉客户端没有传入redirectUri直接报错  
				return new ResponseEntity("OAuth callback url needs to be provided by client!!!", HttpStatus.NOT_FOUND);
			}
			//返回错误消息（如?error=）  
			final OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_FOUND).error(e)
					.location(redirectUri).buildQueryMessage();
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(new URI(response.getLocationUri()));
			return new ResponseEntity(headers, HttpStatus.valueOf(response.getResponseStatus()));
		}
	}

	@RequestMapping(value = "/accessToken", method = RequestMethod.POST)
	public HttpEntity token(HttpServletRequest request) throws URISyntaxException, OAuthSystemException {
		try {
			//构建OAuth请求  
			OAuthTokenRequest oauthRequest = new OAuthTokenRequest(request);

			//检查提交的客户端id是否正确  
			if (!oauthRequest.getClientId().equals(CLIENT_ID)) {
				OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
						.setError(OAuthError.TokenResponse.INVALID_CLIENT)
						/*.setErrorDescription(Constants.INVALID_CLIENT_DESCRIPTION)*/.buildJSONMessage();
				return new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
			}

			// 检查客户端安全KEY是否正确  
			if (!oauthRequest.getClientSecret().equals(CLIENT_SECERT)) {
				OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
						.setError(OAuthError.TokenResponse.UNAUTHORIZED_CLIENT).buildJSONMessage();
				return new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
			}

			String authCode = oauthRequest.getParam(OAuth.OAUTH_CODE);
			// 检查验证类型，此处只检查AUTHORIZATION_CODE类型，其他的还有PASSWORD或REFRESH_TOKEN  
			if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE).equals(GrantType.AUTHORIZATION_CODE.toString())) {
				if (!authCode.equals(RESPONSE_CODE)) {
					OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
							.setError(OAuthError.TokenResponse.INVALID_GRANT).buildJSONMessage();
					return new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
				}
			}

			//生成Access Token  
			OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
			final String accessToken = oauthIssuerImpl.accessToken();
			//记录accessToken 到内存或者数据库
			ACCESS_TOKEN = accessToken;

			String redirectURI = oauthRequest.getParam(OAuth.OAUTH_REDIRECT_URI);
			//生成OAuth响应  ,设置过期时间，理论上过期时间应该放到从数据库取
			//OAuthResponse response = OAuthASResponse.tokenResponse(HttpServletResponse.SC_OK)
			//		.setAccessToken(accessToken).setExpiresIn(String.valueOf(EXPIRE_IN)).buildJSONMessage();

			//根据OAuthResponse生成ResponseEntity  

			//			HttpHeaders headers = new HttpHeaders();
			//			headers.setLocation(new URI(response.getLocationUri()));

			//return new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
			//进行OAuth响应构建  
			OAuthASResponse.OAuthAuthorizationResponseBuilder builder = OAuthASResponse.authorizationResponse(request,
					HttpServletResponse.SC_FOUND);
			//设置授权码  
			builder.setExpiresIn(String.valueOf(EXPIRE_IN)).setAccessToken(ACCESS_TOKEN);
			//得到到客户端重定向地址  

			//构建响应  
			final OAuthResponse response = builder.location(redirectURI).buildQueryMessage();
			//根据OAuthResponse返回ResponseEntity响应  
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(new URI(response.getLocationUri()));
			return new ResponseEntity(headers, HttpStatus.valueOf(response.getResponseStatus()));
		} catch (OAuthProblemException e) {
			//构建错误响应  
			OAuthResponse res = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST).error(e)
					.buildJSONMessage();
			return new ResponseEntity(res.getBody(), HttpStatus.valueOf(res.getResponseStatus()));
		}
	}

	public boolean login(Subject subject, HttpServletRequest request) {

		if ("get".equalsIgnoreCase(request.getMethod())) {
			return false;
		}
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			return false;
		}

		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		try {
			subject.login(token);
			return true;
		} catch (Exception e) {
			logger.info("Login failed");
			return false;
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(Model model, String username, String password) {

		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			model.addAttribute("result", "failed");
			return "/oltu/result";
		}

		Subject subject = SecurityUtils.getSubject();

		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		try {
			subject.login(token);
			model.addAttribute("result", "success");
		} catch (Exception e) {
			logger.info("Login failed");
			model.addAttribute("result", "Login failed");
		}
		return "/oltu/result";
	}
}
