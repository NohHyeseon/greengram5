package com.green.greengram4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;

@EnableJpaAuditing
@ConfigurationPropertiesScan
@SpringBootApplication
public class Greengram5Application {

    public static void main(String[] args) {
        SpringApplication.run(Greengram5Application.class, args);
    }
    @Bean
    public PageableHandlerMethodArgumentResolverCustomizer customizer(){
        return p -> p.setOneIndexedParameters(true);  //페이지너블 1로설정
    }


}
