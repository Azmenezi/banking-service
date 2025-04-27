package com.example.banking_service.steps

import com.example.banking_service.authentication.jwt.AuthenticationRequest
import com.example.banking_service.authentication.jwt.AuthenticationResponse
import com.example.banking_service.user.UserRequest
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.junit.jupiter.api.Assertions.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration
class AuthSteps {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Autowired
    lateinit var common: CommonSteps // ✅ Use shared response here

    @When("I register a new user with username {string} and password {string}")
    fun registerUser(username: String, password: String) {
        val req = UserRequest(username, password)
        println(req)
        common.response = restTemplate.postForEntity("/auth/register", req, String::class.java)
    }

    @When("I try to register an already existing user with username {string}")
    fun registerDuplicateUser(username: String) {
        val req = UserRequest(username, "samePass")
        restTemplate.postForEntity("/auth/register", req, String::class.java) // First
        common.response = restTemplate.postForEntity("/auth/register", req, String::class.java) // Duplicate
    }

    @When("I login with username {string} and password {string}")
    fun loginWithCorrect(username: String, password: String) {
        val req = AuthenticationRequest(username, password)
        common.response = restTemplate.postForEntity("/auth/login", req, AuthenticationResponse::class.java)
    }

    @When("I login with invalid credentials {string} and {string}")
    fun loginWithWrong(username: String, password: String) {
        val req = AuthenticationRequest(username, password)
        common.response = restTemplate.postForEntity("/auth/login", req, String::class.java)
    }

    @Then("a JWT token should be returned")
    fun checkTokenPresent() {
        val body = common.response?.body as? AuthenticationResponse
        assertNotNull(body?.token)
    }
}
