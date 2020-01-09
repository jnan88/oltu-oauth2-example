package cn.zetark.oauth2.client;

/**
 * 封装OAuth Server端认证需要的参数
 */
public class ClientParams {

	public static final String SCOPE = "read write"; // 应用id CLIENT_ID
	
//	public static final String CLIENT_ID = "mobile"; // 应用id CLIENT_ID
//    public static final String CLIENT_SECRET = "mobile"; // 应用secret CLIENT_SECRET
    public static final String CLIENT_ID = "test"; // 应用id CLIENT_ID
    public static final String CLIENT_SECRET = "test"; // 应用secret CLIENT_SECRET
	
	public static final String USERNAME = "test"; // 用户名
	public static final String PASSWORD = "test"; // 密码

	public static final String OAUTH_SERVER_URL =  "http://localhost:8081/oauth/authorize"; // 授权地址
	public static final String OAUTH_SERVER_TOKEN_URL =  "http://localhost:8081/oauth/token"; // ACCESS_TOKEN换取地址
    public static final String OAUTH_SERVER_REDIRECT_URI =  "http://localhost:8082/oauth/authorization_code_callback"; // 回调地址

    public static final String OAUTH_SERVICE_API =  "http://localhost:8084/mobile/system_time"; // 测试开放数据api
    public static final String OAUTH_SERVICE_API_RS =  "http://localhost:8084/rs/username"; // 测试开放数据api

}
