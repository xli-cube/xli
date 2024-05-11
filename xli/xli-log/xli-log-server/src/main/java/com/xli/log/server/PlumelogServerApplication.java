package com.xli.log.server;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * plumelog应用程序启动类
 *
 */
@SpringBootApplication
@EnableAsync
@EnableScheduling
@EnableAdminServer
@ComponentScan(basePackages = {"com.xli"})
public class PlumelogServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlumelogServerApplication.class, args);
    }

}
