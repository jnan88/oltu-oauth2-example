package cn.zetark.oauth2;

/**
 * 
 */

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * @author qizai
 *
 */
public class StartOauth2Server {
	public static void main(String[] args) {
		new StartOauth2Server().run("/", 8080, ENV_DEV);
	}

	private static final String	DEFAULT_WEBAPP_PATH	= "src/main/webapp";
	private static final String	ACTIVE_PROFILE		= "spring.profiles.active";
	static final String			ENV_DEV				= "dev";
	static final String			ENV_TEST			= "test";
	private Server				server				= null;
	private String				ctx					= "/";
	private int					port				= 8080;
	private String				profile				= ENV_DEV;

	private void run(String ctx, int port, String profile) {
		this.ctx = ctx;
		this.port = port;
		if (null != profile) {
			this.profile = profile;
		}
		try {
			start0();
			while (true) {
				int c = System.in.read();
				if (107 == c) {// k
					stop0();
					System.exit(-1);
					break;
				}
				if (c == '\n') {
					stop0();
					start0();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void info() {
		System.out.println("运行环境 ：" + profile);
		System.out.println("服务地址 ：http://localhost:" + port + ctx);
		System.out.println("按回车键重新启动服务.");
		System.out.println("按k+回车键停止服务.");
	}

	private void stop0() throws Exception {

		server.stop();
		server.destroy();
		server = null;
		System.out.println("Stop Ok!");
	}

 
	private void start0() throws Exception {
		System.setProperty(ACTIVE_PROFILE, profile);
		server = new Server(port);
		WebAppContext webapp = new WebAppContext();
		webapp.setDescriptor(DEFAULT_WEBAPP_PATH + "/WEB-INF/web.xml");
		
		webapp.setParentLoaderPriority(true);
		webapp.setContextPath(ctx);
		webapp.setWar(DEFAULT_WEBAPP_PATH);
		server.setHandler(webapp);
		server.setStopAtShutdown(true);
		server.start();
		// server.join();// 如果server没有起来，这里使线程阻塞,保证你的server真正的起来,但是使用了就无法便捷重启！！！
		info();
	}
}
