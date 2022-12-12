package com.example.securejwt;

import com.example.securejwt.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class SecureJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecureJwtApplication.class, args);
    }

}
