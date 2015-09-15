package features.step_definitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Status;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestOperations;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Stepdefs {

  private final Logger log = LoggerFactory.getLogger(this.getClass());

  @Autowired
  RestOperations rest;

  private HealthStatus healthResponse;
  private ThirdPartyHealthStatus tpHealth;

  @Value("${targetRootUrl:http://localhost:8080}")
  private String baseUrl;

  @Given("^the app has started$")
  public void the_app_has_started() throws Throwable {
    System.err.println("BASE URL: " + baseUrl);
    String body;
    try {
      body = rest.getForObject(baseUrl, String.class);
    } catch (HttpClientErrorException e) {
      // Happy path with 404 expected
      log.warn(e.getMessage());
      return;
    }
    log.error("Unexpected response: " + body);
    fail("Should have been a 404 page not found");
  }
  
  
  
  
  
  
  
  
  /*
   * POC
   * STEPS
   * 
   */

  @When("^I invoke the health endpoint$")
  public void i_invoke_the_health_endpoint() throws Throwable {
    healthResponse = rest.getForObject(baseUrl + "/health", HealthStatus.class);
  }

  @Then("^the status of the app is UP$")
  public void the_status_of_the_app_is_UP() throws Throwable {
    assertEquals(Status.UP.toString(), healthResponse.getStatus());
  }

  @When("^I invoke the third party service health check endpoint$")
  public void i_invoke_the_third_party_service_health_check_endpoint() throws Throwable {
    tpHealth = rest.getForObject(baseUrl + "/health", ThirdPartyHealthStatus.class);
  }

  @Then("^the status of the third party is UP$")
  public void the_status_of_the_third_party_is_UP() throws Throwable {
    assertEquals(Status.UP.toString(), tpHealth.getData().getStatus());
  }

  @And("^their API is stable$")
  public void their_API_is_stable() throws Throwable {
    assertEquals(true, tpHealth.getData().isStable());
  }
  
  @JsonIgnoreProperties(ignoreUnknown = true)
  static class HealthStatus {
    String status;

    public String getStatus() {
      return status;
    }

    public void setStatus(String status) {
      this.status = status;
    }
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  static class ThirdPartyHealthStatus {

    @JsonProperty("thirdPartyService")
    private ThirdPartyAPIHealthStatusData data;

    public ThirdPartyAPIHealthStatusData getData() {
      return data;
    }

    public void setData(ThirdPartyAPIHealthStatusData data) {
      this.data = data;
    }

  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  @JsonInclude(Include.NON_EMPTY)
  static class ThirdPartyAPIHealthStatusData extends HealthStatus {

    @JsonProperty("hasStableAPI")
    private boolean isStable;

    public boolean isStable() {
      return isStable;
    }

    public void setStable(boolean isStable) {
      this.isStable = isStable;
    }

  }


}
