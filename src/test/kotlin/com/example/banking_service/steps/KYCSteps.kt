package com.example.banking_service.steps

import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration
class KYCSteps {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Autowired
    lateinit var common: CommonSteps

    @When("I send a POST request to {string} with body:")
    fun sendPostWithJson(endpoint: String, body: String) {
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
            setBearerAuth(common.jwtToken)
        }

        val entity = HttpEntity(body, headers)
        common.response = restTemplate.exchange(endpoint, HttpMethod.POST, entity, String::class.java)
    }

    @When("I fetch KYC data for user ID {long}")
    fun fetchKyc(id: Long) {
        val headers = HttpHeaders().apply {
            setBearerAuth(common.jwtToken)
        }

        val entity = HttpEntity(null, headers)
        common.response = restTemplate.exchange(
            "/users/v1/kyc/$id",
            HttpMethod.GET,
            entity,
            String::class.java
        )
    }


}
