package com.emc.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
  public static final String FAILOVER_THIRD_PARTY_HOST = "jsonplaceholder.typicode.com";
  private static final String THIRD_PARTY_URL = "http://" + THIRD_PARTY_HOST + "/";
  private static final String FAILOVER_THIRD_PARTY_URL = "http://" + FAILOVER_THIRD_PARTY_HOST + "/posts/1";
  
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  @Override
  protected void doHealthCheck(Builder builder) throws Exception {
    log.info("Using REST endpoint of third party");
    try{
      ThirdPartyHeaderResponse headerResponse = rest.getForObject(THIRD_PARTY_URL, ThirdPartyHeaderResponse.class);
      builder.up().withDetail("thirdPartyHost", headerResponse.getHost());
    }catch (Exception e){
      log.error("Oh dear, failing over due to " + e.getMessage());
      rest.getForObject(FAILOVER_THIRD_PARTY_URL, String.class);
      builder.up().withDetail("failoverRestAPIUsed", true).withDetail("errorWithHost", THIRD_PARTY_HOST).withException(e);
    }
    log.info("REST endpoint returned");
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
