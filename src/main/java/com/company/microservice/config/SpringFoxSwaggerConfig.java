package com.company.microservice.config;

import com.google.common.collect.Sets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxSwaggerConfig {
    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .produces(Sets.newHashSet("application/json"))
                .consumes(Sets.newHashSet("application/json"))
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.company.microservice"))
                .paths(PathSelectors.ant("/**"))
                .build();
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo(
                "Spring-Data Jpa Service",
                "An example implementation using spring boot, data, jpa, springfox/swagger",
                "0.1.0",
                "none@no.no",
                new Contact("Rian Lowrey", "github.com/rianlowrey", "rian.lowrey@gmail.com"),
                "MIT",
                "https://opensource.org/licenses/MIT",
                Collections.emptyList()
        );
    }
}
