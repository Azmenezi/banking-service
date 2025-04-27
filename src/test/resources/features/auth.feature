Feature: User Authentication and Registration

  Scenario: Happy path - Register a new user
    When I register a new user with username "john" and password "pass123"
    Then the response status code should be 200

  Scenario: Unhappy path - Register duplicate user
    When I try to register an already existing user with username "john"
    Then the response status code should be 409

  Scenario: Happy path - Login with correct credentials
    When I login with username "john" and password "pass123"
    Then the response status code should be 200
    And a JWT token should be returned

  Scenario: Unhappy path - Login with wrong credentials
    When I login with invalid credentials "john" and "wrongPass"
    Then the response status code should be 401
