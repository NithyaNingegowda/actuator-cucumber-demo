Feature: sanity checks on app

  Background: the app has started with embedded tomcat
    Given the app has started

  Scenario: app has health
    When I invoke the health endpoint
    Then the status of the app is UP
    
  Scenario: app can connect to third party service
    When I invoke the third party service health check endpoint
    Then the status of the third party is UP
    And the host has been returned by their API
