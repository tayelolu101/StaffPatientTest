package com.example.hospitalcasestudy.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@ConfigurationProperties("app.auth")
@Configuration
@Getter
@Setter
public class AuthExclude {

    List<Path> exclude;

    @Getter
    @Setter
    public static class Path {
        String method;
        String path;
    }

}
