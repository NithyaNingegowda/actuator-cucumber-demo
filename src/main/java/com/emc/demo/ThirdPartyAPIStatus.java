package com.emc.demo;

public final class ThirdPartyAPIStatus {
  
  private boolean isStable;
  
  private String message;

  public ThirdPartyAPIStatus(boolean isStable) {
    this.isStable = isStable;
    this.message = "Third Party service currently stable";
  }
  
  public ThirdPartyAPIStatus(boolean isStable, String message) {
    this.isStable = isStable;
    this.message = message;
  }

  public boolean isStable() {
    return isStable;
  }

  public String getMessage() {
    return message;
  }

}
