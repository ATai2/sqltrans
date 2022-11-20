package com.ppx.sbsql;

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
 * Swagger配置类
 * @author zhang_peng_lc
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				//按照固定路径去相应的路径扫描 RequestHandlerSelectors.any()扫描所有路径				
				.apis(RequestHandlerSelectors.basePackage("com.ppx.sbsql"))
				.paths(PathSelectors.any())
				.build();
	}
	/**
	 * 生成一个API文档的描述
	* @return 
	* @return ApiInfo
	* @author zhuanghuan
	* @date 2019年7月1日上午9:05:38
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("springboot利用swagger构建api文档")
				.description("简单而又优雅的描述")
				.termsOfServiceUrl("http://www.baidu.com")
				.version("1.0")
				.build();
	}
}