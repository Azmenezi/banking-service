package com.example.banking_service.account

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.math.BigDecimal

@RestController
class AccountController(
    private val accountRepo: AccountRepo
) {

    @PostMapping("/accounts/v1/accounts")
    fun createAccount(@RequestBody request: AccountRequest): AccountEntity {
        if (request.accountNumber.isBlank()) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Account number must not be empty")
        }

        if (request.initialBalance < BigDecimal.ZERO) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Initial balance must not be negative")
        }

        val newAccount = AccountEntity(
            userId = request.userId,
            balance = request.initialBalance,
            isActive = true,
            accountNumber = request.accountNumber
        )
        return accountRepo.save(newAccount)
    }

    @PostMapping("/accounts/v1/accounts/{accountNumber}/close")
    fun closeAccount(@PathVariable accountNumber: String): AccountEntity {
        val account: AccountEntity? = accountRepo.findByAccountNumber(accountNumber)
        return account?.let {
            accountRepo.save(it.copy(isActive = false))
        } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found")
    }

    @GetMapping("/accounts/v1/accounts")
    fun listAllAccounts(): List<AccountEntity> =
        accountRepo.findAll()
}

data class AccountRequest(
    val userId: Long,
    val initialBalance: BigDecimal,
    val accountNumber: String
)
