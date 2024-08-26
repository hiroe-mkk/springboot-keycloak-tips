package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(DemoApplication.class);
        // デフォルトのプロファイルを dev に設定する。
        app.setDefaultProperties(Collections.singletonMap("spring.profiles.active", "dev"));
        app.run(args);
    }

}
