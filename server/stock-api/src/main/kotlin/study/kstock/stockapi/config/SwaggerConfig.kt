package study.kstock.stockapi.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.util.ArrayList


@Configuration
@EnableSwagger2
class SwaggerConfig : WebMvcConfigurationSupport() {
    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/")
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/")
    }

    @Bean
    fun apiV1(): Docket {
        val version = "V1"
        val title = "product API $version"
        return Docket(DocumentationType.SWAGGER_2)
            .useDefaultResponseMessages(false)
            .groupName(version)
            .apiInfo(apiInfo(title, version))
            .select()
            .apis(RequestHandlerSelectors.basePackage("study.kstock.stockapi"))
            .paths(PathSelectors.ant("/**"))
            .build()
    }

    private fun apiInfo(title: String, version: String): ApiInfo {
        return ApiInfo(
            title,
            "Swagger로 생성한 API Docs",
            version,
            "https://github.com/boring-km/k-stock",
            Contact("Contact Me", "https://github.com/boring-km/k-stock", "ts4840644804@gmail.com"),
            "Licenses",
            "https://github.com/boring-km/k-stock",
            ArrayList()
        )
    }
}