package features.step_definitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Status;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.emc.demo.ThirdPartyServiceHealthIndicator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Stepdefs {

//  private static final String BASE_URL = "http://localhost:8080";

  private final Logger log = LoggerFactory.getLogger(this.getClass());

  RestTemplate rest = new RestTemplate();

  private HealthStatus healthResponse;
  private ThirdPartyHealthStatus tpHealth;

  @Value( "${target.url}" )
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
  
  @And("^the host has been returned by their API$")
  public void the_host_has_been_returned_by_their_API() throws Throwable {
    assertEquals(ThirdPartyServiceHealthIndicator.THIRD_PARTY_HOST, tpHealth.getData().getThirdPartyHost());
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
    private ThirdPartyHealthStatusData data;

    public ThirdPartyHealthStatusData getData() {
      return data;
    }

    public void setData(ThirdPartyHealthStatusData data) {
      this.data = data;
    }

  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  @JsonInclude(Include.NON_EMPTY)
  static class ThirdPartyHealthStatusData extends HealthStatus {

    private String thirdPartyHost;

    public String getThirdPartyHost() {
      return thirdPartyHost;
    }

    public void setThirdPartyHost(String thirdPartyHost) {
      this.thirdPartyHost = thirdPartyHost;
    }

  }


}
