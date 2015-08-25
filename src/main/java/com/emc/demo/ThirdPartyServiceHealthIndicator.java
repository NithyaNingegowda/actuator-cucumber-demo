package com.emc.demo;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Component
public class ThirdPartyServiceHealthIndicator extends AbstractHealthIndicator {

  private RestTemplate rest = new RestTemplate();

  public static final String THIRD_PARTY_HOST = "headers.jsontest.com";
  private static final String THIRD_PARTY_URL = "http://" + THIRD_PARTY_HOST + "/";

  @Override
  protected void doHealthCheck(Builder builder) throws Exception {
    ThirdPartyHeaderResponse headerResponse = rest.getForObject(THIRD_PARTY_URL, ThirdPartyHeaderResponse.class);
    builder.up().withDetail("thirdPartyHost", headerResponse.getHost());
  }
  
  @JsonIgnoreProperties(ignoreUnknown = true)
  static class ThirdPartyHeaderResponse {

    @JsonProperty("Host")
    String host;

    public String getHost() {
      return host;
    }

    public void setHost(String host) {
      this.host = host;
    }

  }
}
