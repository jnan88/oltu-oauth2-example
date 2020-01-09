package cn.zetark.oauth2.client;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

import org.apache.http.util.EntityUtils;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class OauthClient {
	public static String substringBetween(final String str, final String open, final String close) {
		if (str == null || open == null || close == null) {
			return null;
		}
		final int start = str.indexOf(open);
		if (start != -1) {
			final int end = str.indexOf(close, start + open.length());
			if (end != -1) {
				return str.substring(start + open.length(), end);
			}
		}
		return null;
	}

	/**
	 * 获取授权码
	 * 
	 * @return
	 * @throws OAuthSystemException
	 * @throws MalformedURLException
	 * @throws URISyntaxException
	 */
	private static Response makeAuthCodeRequest()
			throws OAuthSystemException, MalformedURLException, URISyntaxException {

		// 创建表单，模拟填充表单并提交表单
		Form form = new Form();
		form.param("username", ClientParams.USERNAME);
		form.param("password", ClientParams.PASSWORD);
		form.param("scope", ClientParams.SCOPE);
		form.param("state", "state");
		form.param("client_id", ClientParams.CLIENT_ID);
		form.param("response_type", ResponseType.CODE.toString());
		form.param("redirect_uri", ClientParams.OAUTH_SERVER_REDIRECT_URI);

		ResteasyClient client = new ResteasyClientBuilder().build();
		Response response = client.target(ClientParams.OAUTH_SERVER_URL).request().post(Entity.form(form));
		System.out.println("Status = " + response.getStatus());
		System.out.println("Location = " + response.getLocation());

		String location = response.getLocation().toURL().toString();
		String authCode = substringBetween(location, "code=", "&");
		System.out.println("访问资源：makeAuthCodeRequest :" + authCode);
		System.out.println(ClientParams.OAUTH_SERVER_URL);
		try {
			System.out.println(authCode);
			makeTokenRequestWithAuthCode(authCode);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	/**
	 * 根据授权码获取accessToken
	 * 
	 * @param authCode
	 * @return
	 * @throws OAuthProblemException
	 * @throws OAuthSystemException
	 */
	private static OAuthAccessTokenResponse makeTokenRequestWithAuthCode(String authCode)
			throws OAuthProblemException, OAuthSystemException {
		System.out.println("访问资源：makeTokenRequestWithAuthCode :" + authCode);
		System.out.println(ClientParams.OAUTH_SERVER_TOKEN_URL);
		OAuthClientRequest request = OAuthClientRequest.tokenLocation(ClientParams.OAUTH_SERVER_TOKEN_URL)
				.setClientId(ClientParams.CLIENT_ID).setClientSecret(ClientParams.CLIENT_SECRET)
				.setGrantType(GrantType.AUTHORIZATION_CODE).setCode(authCode)
				.setRedirectURI(ClientParams.OAUTH_SERVER_REDIRECT_URI).buildBodyMessage();

		OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());

		OAuthAccessTokenResponse oauthResponse = oAuthClient.accessToken(request);
		System.out.println("Access Token: " + oauthResponse.getAccessToken());
		System.out.println("Expires In: " + oauthResponse.getExpiresIn());
		System.out.println(oauthResponse.getBody());

		getClientCredentials(oauthResponse.getAccessToken());
		// getAuthedService(oauthResponse.getAccessToken());

		return oauthResponse;
	}

	public static final MediaType	MEDIATYPE_JSON	= MediaType.parse("application/json; charset=utf-8");

	private static OkHttpClient		okHttpClient	= new OkHttpClient();

	private static String post(String url, String json) throws IOException {
		RequestBody body = RequestBody.create(MEDIATYPE_JSON, json);
		Request request = new Request.Builder().url(url).post(body).build();
		String resp = okHttpClient.newCall(request).execute().body().string();
		return resp;
	}

	private static void getClientCredentials(String accessToken) throws OAuthProblemException, OAuthSystemException {
		String url = ClientParams.OAUTH_SERVER_TOKEN_URL;
		 

		// 创建表单，模拟填充表单并提交表单
		Form form = new Form();
		form.param("client_id", ClientParams.CLIENT_ID);
		form.param("client_secret", ClientParams.CLIENT_SECRET);
		form.param("scope", ClientParams.SCOPE);
		form.param("grant_type", "client_credentials");

		ResteasyClient client = new ResteasyClientBuilder().build();
		Response response = client.target(url).request().post(Entity.form(form));
		System.out.println("访问资源：getClientCredentials :" + accessToken);
		System.out.println(url);
		System.out.println(response.readEntity(String.class));
	}

	/**
	 * 测试开放接口服务
	 */
	private static void getAuthedService(String accessToken) {
		System.out.println("访问资源：" + ClientParams.OAUTH_SERVICE_API + "?access_token=" + accessToken);
		// ResteasyClient client = new ResteasyClientBuilder().build();
		// Response response =
		// client.target(ClientParams.OAUTH_SERVICE_API).queryParam("access_token",
		// accessToken)
		// .request().get();
		// System.out.println(response.getStatus());
		// System.out.println(response.readEntity(String.class));
	}

	public static void main(String[] args) throws Exception {

		makeAuthCodeRequest();

	}

}
