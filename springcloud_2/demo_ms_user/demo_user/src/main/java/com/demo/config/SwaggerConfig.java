package com.demo.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket createRestApi() {

		// 在配置好的配置类中增加此段代码即可
		ParameterBuilder ticketPar = new ParameterBuilder();
		List<Parameter> pars = new ArrayList<Parameter>();
		ticketPar.name("Authorization").description("登录校验")// name表示名称，description表示描述
				.modelRef(new ModelRef("string")).parameterType("header").required(false).defaultValue("Bearer ")
				.build();// required表示是否必填，defaultvalue表示默认值
		pars.add(ticketPar.build());// 添加完此处一定要把下边的带***的也加上否则不生效

		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)) // 这里采用包含注解的方式来确定要显示的接口
				// .apis(RequestHandlerSelectors.basePackage("com.demo.user"))
				.paths(PathSelectors.any()).build().globalOperationParameters(pars);// ************把消息头添加

	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("用户微服务 RESTful API")
				.contact(new Contact("zteye@163.com", "http://demo.com", "xx")).version("1.0").description("API文档")
				.build();
	}
}
