package com.emc.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.stereotype.Component;

@Component
public class ThirdPartyServiceHealthIndicator extends AbstractHealthIndicator {

  @Autowired
  public ThirdPartyServiceHealthIndicator(ThirdPartyService tpService) {
    this.tpService = tpService;
  }

  private ThirdPartyService tpService;

  @Override
  protected void doHealthCheck(Builder builder) throws Exception {
    try {
      ThirdPartyAPIStatus status = tpService.invoke();
      builder.up().withDetail("hasStableAPI", status.isStable()).withDetail("message", status.getMessage());
    } catch (Exception e) {
      builder.down().withException(e);
    }
  }

}
