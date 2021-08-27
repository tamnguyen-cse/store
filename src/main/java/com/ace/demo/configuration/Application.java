package com.ace.demo.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class Application {

    // private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // @Value("${server.port}")
    // private int port;
    //
    // @Bean
    // @Primary
    // public EurekaInstanceConfigBean eurekaInstanceConfig(InetUtils inetUtils) {
    // EurekaInstanceConfigBean config = new EurekaInstanceConfigBean(inetUtils);
    // String ip = null;
    // try {
    // ip = InetAddress.getLocalHost().getHostAddress();
    // } catch (UnknownHostException e) {
    // logger.error("Exception: {}", e);
    // }
    // config.setIpAddress(ip);
    // config.setNonSecurePort(port);
    // config.setPreferIpAddress(true);
    // return config;
    // }

}
