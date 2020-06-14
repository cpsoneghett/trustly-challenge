package com.trustly.challenge.api.config;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.ModelRendering;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.TagsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket( DocumentationType.SWAGGER_2 ).select().apis( RequestHandlerSelectors.basePackage( "com.queroserpaguer.api.controller" ) )
			.paths( PathSelectors.any() ).build().pathMapping( "/" ).directModelSubstitute( LocalDate.class, String.class )
			.genericModelSubstitutes( ResponseEntity.class ).useDefaultResponseMessages( false ).globalResponseMessage( RequestMethod.GET, responseMessages() )
			.globalResponseMessage( RequestMethod.POST, responseMessages() ).globalResponseMessage( RequestMethod.PUT, responseMessages() )
			.globalResponseMessage( RequestMethod.DELETE, responseMessages() )
			.tags( new Tag( "QueroSerPaguer API Service", "Documentação do Swagger para a API de Apresentação" ) );

	}

	@Bean
	UiConfiguration uiConfig() {
		return UiConfigurationBuilder.builder().deepLinking( true ).displayOperationId( false ).defaultModelsExpandDepth( 1 ).defaultModelExpandDepth( 1 )
			.defaultModelRendering( ModelRendering.EXAMPLE ).displayRequestDuration( false ).docExpansion( DocExpansion.NONE ).filter( false ).maxDisplayedTags( null )
			.operationsSorter( OperationsSorter.ALPHA ).showExtensions( false ).tagsSorter( TagsSorter.ALPHA )
			.supportedSubmitMethods( UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS ).validatorUrl( null ).build();
	}

	private List<ResponseMessage> responseMessages() {

		return new ArrayList<ResponseMessage>() {

			private static final long serialVersionUID = 1L;

			{
				add( new ResponseMessageBuilder().code( 500 ).message( "Erro interno!" ).responseModel( new ModelRef( "Error" ) ).build() );
				add( new ResponseMessageBuilder().code( 403 ).message( "Acesso negado!" ).build() );
				add( new ResponseMessageBuilder().code( 201 ).message( "Recurso criado." ).build() );
			}
		};
	}
}
