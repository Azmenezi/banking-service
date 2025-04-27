Feature: Account Management

  Background:
    Given I have a valid JWT token for user "john" with password "pass123"


  Scenario: Happy path - Create a new account
    When I create an account with balance 1000.0 and number "ACC123"
    Then the response status code should be 200

  Scenario: Unhappy path - Create an account with missing account number
    When I create an account with balance 500.0 and number ""
    Then the response status code should be 400

  Scenario: Unhappy path - Create an account with negative balance
    When I create an account with balance -100.0 and number "NEG001"
    Then the response status code should be 400


  Scenario: Happy path - Close an existing account
    When I create an account with balance 1000.0 and number "ToClose02"
    And I close the account with number "ToClose02"
    Then the response status code should be 200

  Scenario: Unhappy path - Close a non-existing account
    When I close the account with number "None123"
    Then the response status code should be 500


  Scenario: Happy path - List accounts after creation
    When I create an account with balance 1500.0 and number "ACC_LIST_1"
    And I list all accounts
    Then the response status code should be 200

  Scenario: Happy path - List accounts with no accounts
    When I list all accounts
    Then the response status code should be 200
