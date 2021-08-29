package com.ace.demo.configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@EnableConfigServer
@SpringBootApplication
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Value("${server.port}")
    private int port;

    private static final String PUBLIC_IP_URL =
            "http://ec2-18-130-1-58.eu-west-2.compute.amazonaws.com:59152/";

    private static final String PUBLIC_IP = "public_ip";

    @Bean
    @Primary
    public EurekaInstanceConfigBean eurekaInstanceConfig(InetUtils inetUtils) {
        EurekaInstanceConfigBean config = new EurekaInstanceConfigBean(inetUtils);
        String publicIp = null;
        String privateIp = null;
        try {
            privateIp = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            logger.error("Exception: {}", e);
        }
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(PUBLIC_IP_URL, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            JSONObject json = new JSONObject(response.getBody());
            if (json.has(PUBLIC_IP))
                publicIp = json.getString(PUBLIC_IP);

        }
        config.setIpAddress(privateIp);
        config.setNonSecurePort(port);
        config.setPreferIpAddress(true);
        config.getMetadataMap().put(PUBLIC_IP, publicIp);
        return config;
    }

}
