package com.hivescm.codeid.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * API文档生成工具；访问地址：
 *   http://localhost:端口/swagger-ui.html
 *
 * basePackage 必须包括**，否则与feign扫描冲突
 */
@Configuration
@EnableSwagger2
public class Swagger2 {
	private static final Logger LOGGER = LoggerFactory.getLogger(Swagger2.class);

	@Value("${enable.swagger}")
	private boolean enableSwagger=true;

	@Value("${base.package.swagger}")
	private String basePackage ;
	@Bean
	public Docket createRestApi() {
		LOGGER.info("Swagger2信息: enable.swagger=" + enableSwagger+"; base.package.swagger="+basePackage);
		return new Docket(DocumentationType.SWAGGER_2)//
				.enable(enableSwagger)//
				.apiInfo(apiInfo()).select()//
				.apis(RequestHandlerSelectors.basePackage(basePackage))//
				.paths(PathSelectors.any()).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("generatedCodeId-server系统的服务API文档")//
				.description("generatedCodeId-server系统的服务API文档")//
				.termsOfServiceUrl("")//
				.version("1.0").build();
	}

}