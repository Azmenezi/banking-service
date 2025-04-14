package com.example.banking_service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BankingServiceApplication

fun main(args: Array<String>) {
	runApplication<BankingServiceApplication>(*args)
}
