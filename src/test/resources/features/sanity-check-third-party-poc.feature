Feature: sanity checks on poc app which connects to a third party REST API

  Background: full end to end test with app pre-deployed
    Given the app has started

  @health
  Scenario: app has health
    When I invoke the health endpoint
    Then the status of the app is UP

  @custom-health
  Scenario: app can connect to third party service
    When I invoke the third party service health check endpoint
    Then the status of the third party is UP
    And their API is stable
