package com.example.banking_service.steps

import com.example.banking_service.account.AccountRequest
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*
import org.springframework.test.context.ContextConfiguration
import java.math.BigDecimal

@ContextConfiguration
class AccountSteps {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Autowired
    lateinit var common: CommonSteps

    @When("I create an account with balance {double} and number {string}")
    fun createAccount(balance: Double, accountNumber: String) {
        val body = AccountRequest(
            userId = common.userId ?: error("User ID must be set in CommonSteps"),
            initialBalance = BigDecimal.valueOf(balance),
            accountNumber = accountNumber
        )

        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
            setBearerAuth(common.jwtToken)
        }

        val entity = HttpEntity(body, headers)
        common.response = restTemplate.postForEntity("/accounts/v1/accounts", entity, String::class.java)
    }

    @When("I close the account with number {string}")
    fun closeAccount(accountNumber: String) {
        val headers = HttpHeaders().apply {
            setBearerAuth(common.jwtToken)
        }

        val entity = HttpEntity(null, headers)
        common.response = restTemplate.exchange(
            "/accounts/v1/accounts/$accountNumber/close",
            HttpMethod.POST,
            entity,
            String::class.java
        )
    }

    @When("I list all accounts")
    fun listAllAccounts() {
        val headers = HttpHeaders().apply {
            setBearerAuth(common.jwtToken)
        }

        val entity = HttpEntity(null, headers)
        common.response = restTemplate.exchange(
            "/accounts/v1/accounts",
            HttpMethod.GET,
            entity,
            String::class.java
        )
    }
}
