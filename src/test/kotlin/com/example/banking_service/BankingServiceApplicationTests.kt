package com.example.banking_service

import io.cucumber.spring.CucumberContextConfiguration
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import kotlin.test.assertEquals


@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BankingServiceApplicationTests {


	@Autowired
	lateinit var restTemplate: TestRestTemplate

	@Test
	fun testEndpoint() {
		val result = restTemplate.getForEntity("/auth/test", String::class.java)
		assertEquals(expected = "test", actual = result.body)
		assertEquals(expected = HttpStatus.OK, actual = result?.statusCode)
	}

}
