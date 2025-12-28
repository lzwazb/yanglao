package com.administrator.config;


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
                        .title("新城社区居家养老服务平台后端接口文档")
                        .version("v0.0.1")
                        .description("新城社区居家养老服务平台后端服务 API 文档.")
                );
    }
}
