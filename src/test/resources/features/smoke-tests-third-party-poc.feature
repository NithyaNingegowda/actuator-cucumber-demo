@poc
Feature: smoke tests on poc app including connecting to a third party REST API

  Background: full end to end test with app pre-deployed
    Given the app has started

  Scenario: app has health
    When I invoke the health endpoint
    Then the status of the app is UP

  Scenario: app can connect to third party service
    When I invoke the third party service health check endpoint
    Then the status of the third party is UP
    And their API is stable
