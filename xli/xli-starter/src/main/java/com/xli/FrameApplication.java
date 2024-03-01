package com.xli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

@EnableAsync
@SpringBootApplication
@EnableScheduling
@EnableCaching
public class FrameApplication {
	protected final static Logger logger = LoggerFactory.getLogger(FrameApplication.class);

	private static final String RANDOM_PORT = "0";

	private static final String HTTP = "http://";

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(FrameApplication.class, args);
		printSystemLog(context);
	}

	public static void printSystemLog(ApplicationContext context) {
		Environment environment = context.getEnvironment();

		String port = getPort(context);
		String path = getPath(environment);
		String ip = getServerIp();
		String pid = ManagementFactory.getRuntimeMXBean().getSystemProperties().get("PID");
		String jre = System.getProperty("java.specification.version");
		String jvm = ManagementFactory.getRuntimeMXBean().getVmName();

		String localAccess = HTTP + "localhost:" + port + path;
		String externalAccess = HTTP + ip + ":" + port + path;
		String swaggerAccess = HTTP + ip + ":" + port + path + "/swagger-ui/index.html#";
		String knife4jAccess = HTTP + ip + ":" + port + path + "/doc.html#";

		logger.info("\n" + "⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜\n" + "\t:: "
				+ environment.getProperty("spring.application.name") + " :: 启动完毕\n" + "\t- PID：" + pid + "\n" + "\t- JRE："
				+ jre + "\n" + "\t- JVM：" + jvm + "\n" + "\t- 本地访问地址：" + localAccess + "\n" + "\t- 内网访问地址："
				+ externalAccess + "\n" + "\t- Swagger文档：" + swaggerAccess + "\n"+ "\t- Knife4j文档：" + knife4jAccess + "\n"
				+ "⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜");
	}

	private static String getPath(Environment environment) {
		String path = environment.getProperty("server.servlet.context-path");
		if (path == null) {
			path = "";
		}
		return path;
	}

	private static String getPort(ApplicationContext context) {
		String port = context.getEnvironment().getProperty("server.port");
		if (RANDOM_PORT.equals(port)) {
			if (context instanceof ServletWebServerApplicationContext) {
				// 获取真实端口
				WebServer webServer = ((ServletWebServerApplicationContext) context).getWebServer();
				port = String.valueOf(webServer.getPort());
			}
		}
		return port;
	}

	private static String getServerIp() {
		String localIp = "";
		try {
			Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip;
			boolean found = false;
			while (netInterfaces.hasMoreElements() && !found) {
				NetworkInterface ni = netInterfaces.nextElement();
				Enumeration<InetAddress> address = ni.getInetAddresses();
				while (address.hasMoreElements()) {
					ip = address.nextElement();
					if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && !ip.getHostAddress().contains(":")) {
						return ip.getHostAddress();
					}
				}
			}
		} catch (SocketException e) {
			logger.error(e.getMessage());
		}

		return localIp;
	}
}
