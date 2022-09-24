Feature: API ping
  In order to know if the API is up and running
  As API consumer
  I want to ping the api

  Scenario: Ping the api
    When I send a "GET" request to "/ping"
    Then the response status code is 200
    And the response content is:
    """json
    {
      "status": "OK"
    }
    """