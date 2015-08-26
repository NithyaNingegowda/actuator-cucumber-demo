package com.emc.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class ThirdPartyServiceConfig {
  
  @Bean
  public ThirdPartyService thirdPartyService(){
    return new ThirdPartyService(restOperations());
  }

  @Bean
  public RestOperations restOperations() {
    RestTemplate rest = new RestTemplate();
    rest.getMessageConverters().add(0, mappingJacksonHttpMessageConverter());
    return rest;
  }

  @Bean
  public MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter() {
    ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().failOnUnknownProperties(true).build();
    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(objectMapper);
    return converter;
  }
}
