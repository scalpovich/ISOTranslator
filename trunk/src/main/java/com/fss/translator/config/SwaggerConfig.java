package com.fss.translator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger Configuration for the Translator project.
 * 
 * @author ravinaganaboyina
 *
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.fss.translator.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaData());
             
    }
    
    @SuppressWarnings("deprecation")
	private ApiInfo metaData() {

        return new ApiInfo(
                " Translator  REST API",
                "",
                "1.0",
                "",
                "",
                "", null);
  	
    }

}
