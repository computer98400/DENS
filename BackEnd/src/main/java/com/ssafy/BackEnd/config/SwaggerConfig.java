package com.ssafy.BackEnd.config;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private String title = "TeamZOI SNS Service";

    @Bean
    public Docket restApi() {
        Docket d = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ssafy.BackEnd.controller"))
                .paths(postPaths())
                .build();

        System.out.println(" doc : "+d.getGroupName());
        return d;
    }

    private Predicate<String> postPaths(){

        Predicate<String> path = PathSelectors.any();
        System.out.println("path : "+path.toString());
        return path;
    }

    private ApiInfo apiInfo() {
        ApiInfo info = new ApiInfoBuilder()
                .title(title)
                .description("팀 구성을 위한 SNS 웹페이지")
                .version("1.0")
                .license("TeamZOI")
                .build();
        System.out.println("info : "+info);

        return info;
    }


}
