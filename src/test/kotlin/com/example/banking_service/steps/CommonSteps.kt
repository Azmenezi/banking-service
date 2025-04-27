package com.example.banking_service.steps

import com.example.banking_service.authentication.jwt.AuthenticationRequest
import com.example.banking_service.authentication.jwt.AuthenticationResponse
import com.example.banking_service.user.UserRequest
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration
class CommonSteps {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    var jwtToken: String = ""
    var response: ResponseEntity<*>? = null
    var userId: Long? = null

    @Given("I have a valid JWT token for user {string} with password {string}")
    fun generateJwtForUser(username: String, password: String) {
        val userReq = UserRequest(username, password)
        val registerResponse = restTemplate.postForEntity("/auth/register", userReq, String::class.java)

        if (registerResponse.statusCode == HttpStatus.OK || registerResponse.statusCode == HttpStatus.CONFLICT) {
            val loginReq = AuthenticationRequest(username, password)
            val loginResponse = restTemplate.postForEntity("/auth/login", loginReq, AuthenticationResponse::class.java)

            jwtToken = loginResponse.body?.token ?: error("Failed to login and retrieve JWT token")

            val rawResponse = restTemplate.exchange(
                "/users",
                HttpMethod.GET,
                HttpEntity(null, HttpHeaders().apply { setBearerAuth(jwtToken) }),
                String::class.java
            )

            val rawJson = rawResponse.body ?: return
            val userRegex = Regex("""\{"id":(\d+),"username":"$username""".trimIndent())
            userId = userRegex.find(rawJson)?.groupValues?.get(1)?.toLong()
        } else {
            error("Unexpected response from registration: ${registerResponse.statusCode}")
        }
    }

    @Then("the response status code should be {int}")
    fun checkStatus(expected: Int) {
        assertEquals(expected, response?.statusCode?.value())
    }
}