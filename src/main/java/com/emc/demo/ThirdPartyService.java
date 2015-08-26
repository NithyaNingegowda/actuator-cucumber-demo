package com.emc.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NestedRuntimeException;
import org.springframework.web.client.RestOperations;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

public class ThirdPartyService {

  private RestOperations restOperations;

  private static final String THIRD_PARTY_URL = "http://jsonplaceholder.typicode.com/posts/1";

  private final Logger log = LoggerFactory.getLogger(this.getClass());

  public ThirdPartyService(RestOperations restOperations) {
    this.restOperations = restOperations;
  }

  public ThirdPartyAPIStatus invoke() {
    log.info("Using REST endpoint of third party");
    try {
      restOperations.getForObject(THIRD_PARTY_URL, ThirdPartyResponse.class);
    } catch (NestedRuntimeException e) {
      Throwable rootCause = e.getRootCause();
      if (rootCause instanceof UnrecognizedPropertyException) {
        String msg = "Third Party API changed: " + rootCause.getMessage();
        log.error(msg, rootCause);
        return new ThirdPartyAPIStatus(false, msg);
      } else {
        log.error("Third party connection issue: " + e.getMessage(), e);
        throw e;
      }
    } 
    log.info("REST endpoint returned. Phew!");
    return new ThirdPartyAPIStatus(true);
  }
  
  static class ThirdPartyResponse {
    private int userId;
    private int id;
    private String title;
    private String body;
    
    public int getUserId() {
      return userId;
    }
    public void setUserId(int userId) {
      this.userId = userId;
    }
    public int getId() {
      return id;
    }
    public void setId(int id) {
      this.id = id;
    }
    public String getTitle() {
      return title;
    }
    public void setTitle(String title) {
      this.title = title;
    }
    public String getBody() {
      return body;
    }
    public void setBody(String body) {
      this.body = body;
    }
  }


}
