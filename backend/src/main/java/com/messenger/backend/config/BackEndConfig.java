package com.messenger.backend.config;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BackEndConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}