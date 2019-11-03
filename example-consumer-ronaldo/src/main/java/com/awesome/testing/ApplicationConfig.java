package com.awesome.testing;

import com.awesome.testing.error.DisabledErrorHandler;
import com.awesome.testing.error.ErrorProxy;
import com.awesome.testing.util.InformationTransformer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class ApplicationConfig {

    @Bean
    public Docket swagger() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.errorHandler(new DisabledErrorHandler()).build();
    }

    @Bean
    public ErrorProxy errorProxy() {
        return new ErrorProxy();
    }

    @Bean
    public InformationTransformer informationTransformer() {
        return new InformationTransformer();
    }

}
