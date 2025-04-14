package com.example.banking_service.account

import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
class AccountController(
    private val accountRepo: AccountRepo
) {

    @PostMapping("/accounts/v1/accounts")
    fun createAccount(@RequestBody request: AccountRequest): AccountEntity {
        val newAccount = AccountEntity(
            userId = request.userId,
            balance = request.initialBalance,
            isActive = true,
            accountNumber = request.accountNumber
        )
        return accountRepo.save(newAccount)
    }

    @PostMapping("/accounts/v1/accounts/{accountNumber}/close")
    fun closeAccount(@PathVariable accountNumber: String) {
        val account = accountRepo.findAll().firstOrNull { it.accountNumber == accountNumber }
            ?: throw RuntimeException("Account not found")

        // Update isActive to false
        accountRepo.save(account.copy(isActive = false))
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
