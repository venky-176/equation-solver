
package com.Venkat.equationsolver.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Algebraic Equation Solver API")
                        .description("REST API for storing, retrieving, and evaluating algebraic equations using expression trees.")
                        .version("1.0.0"));
    }
}
