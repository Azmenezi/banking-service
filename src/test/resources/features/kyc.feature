Feature: User KYC Management

  Scenario: Happy path - Submit KYC
    Given I have a valid JWT token for user "aziz" with password "123"
    When I send a POST request to "/users/v1/kyc" with body:
    """
    {
      "userId": 1,
      "dateOfBirth": "1990-01-01",
      "nationality": "Kuwaiti",
      "salary": 1500.00
    }
    """
    Then the response status code should be 200

  Scenario: Unhappy path - Submit KYC for invalid user
    Given I have a valid JWT token for user "aziz" with password "123"
    When I send a POST request to "/users/v1/kyc" with body:
    """
    {
      "userId": 99999,
      "dateOfBirth": "1980-12-12",
      "nationality": "Unknown",
      "salary": 100.00
    }
    """
    Then the response status code should be 200

  Scenario: Happy path - Fetch KYC data
    Given I have a valid JWT token for user "aziz" with password "123"
    When I fetch KYC data for user ID 1
    Then the response status code should be 200

  Scenario: Unhappy path - Fetch KYC for user with no data
    Given I have a valid JWT token for user "aziz" with password "123"
    When I fetch KYC data for user ID 99999
    Then the response status code should be 200
