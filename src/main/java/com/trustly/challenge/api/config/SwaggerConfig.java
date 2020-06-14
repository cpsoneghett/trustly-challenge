package com.trustly.challenge.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicates;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

	@Bean
	public Docket productApi() {
		return new Docket( DocumentationType.SWAGGER_2 ).useDefaultResponseMessages( false ).select().apis( RequestHandlerSelectors.any() )
			.paths( Predicates.not( PathSelectors.regex( "/actuator.*" ) ) ).paths( Predicates.not( PathSelectors.regex( "/error.*" ) ) ).paths( PathSelectors.any() )
			.build();
	}
}
