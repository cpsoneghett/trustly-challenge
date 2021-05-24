package com.trustly.challenge.api.config;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Autowired(required = false)
	@Value("${version.number}")
	private String versionNumber;
	
	@SuppressWarnings("unchecked")
	@Bean
	public Docket tmsApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
		        .apis(Predicates.or(RequestHandlerSelectors.basePackage("com.trustly.challenge.api.controller")))
		        .paths(regex("/.*"))
		        .build()
		        .apiInfo(metaData());
	}

	private ApiInfo metaData() {
		return new ApiInfoBuilder().title("TRUSTLY CHALLENGE")
		        .description("Api Documentation")
		        .version(this.versionNumber)
		        .termsOfServiceUrl("urn:tos")
		        .contact(new Contact("", "", ""))
		        .license("Apache 2.0")
		        .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
		        .extensions(null)
		        .build();
	}
}
