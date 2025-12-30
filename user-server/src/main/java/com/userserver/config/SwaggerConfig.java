package com.userserver.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("养老平台用户与权限服务接口文档")
                        .version("v0.0.1")
                        .description("用户个人中心、家属关联老人等用户与权限相关接口")
                );
    }
}


