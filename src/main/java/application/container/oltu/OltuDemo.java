package application.container.oltu;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/oltu")
public class OltuDemo {

	private static Logger logger = LoggerFactory.getLogger(OltuDemo.class);

	@Autowired
	private AuthorizeService authorizeService;

	@RequestMapping(value = "login.html", method = RequestMethod.GET)
	public String oltu() {
		System.out.println("oltu login page");
		return "oltu/login";
	}

	@RequestMapping(value = "/authorize", method = RequestMethod.GET)
	public Object Authorize(Model model, HttpServletRequest request) throws Exception {

		try {
			//构建OAuth 授权请求  
			OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);
			//检查传入的客户端id是否正确  
			//			if (!authorizeService.checkClientId(oauthRequest.getClientId())) {
			//				OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
			//						.setError(OAuthError.TokenResponse.INVALID_CLIENT)
			//						.setErrorDescription(Constants.INVALID_CLIENT_DESCRIPTION).buildJSONMessage();
			//				return new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
			//			}

			Subject subject = SecurityUtils.getSubject();
			//如果用户没有登录，跳转到登陆页面  
			//			if (!subject.isAuthenticated()) {
			//				if (!login(subject, request)) {//登录失败时跳转到登陆页面  
			//					model.addAttribute("client", clientService.findByClientId(oauthRequest.getClientId()));
			//					return "oauth2login";
			//				}
			//			}

			String username = (String) subject.getPrincipal();
			//生成授权码  
			String authorizationCode = null;
			//responseType目前仅支持CODE，另外还有TOKEN  
			String responseType = oauthRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE);
			if (responseType.equals(ResponseType.CODE.toString())) {
				OAuthIssuerImpl oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
				authorizationCode = oauthIssuerImpl.authorizationCode();
				//				authorizeService.addAuthCode(authorizationCode, username);
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
			return "/oltu/result";
		} catch (Exception e) {
			throw e;
		}
	}
}
