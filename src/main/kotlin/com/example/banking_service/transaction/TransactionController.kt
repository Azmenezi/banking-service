package com.example.banking_service.transaction

import com.example.banking_service.account.AccountRepo
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
class TransactionController(
    private val transactionRepo: TransactionRepo,
    private val accountRepo: AccountRepo
) {

    @PostMapping("/accounts/v1/accounts/transfer")
    fun transferFunds(@RequestBody request: TransferRequest): TransactionEntity {
        val sourceAcc = accountRepo.findById(request.sourceAccountId)
            .orElseThrow { RuntimeException("Source account not found") }
        val destAcc = accountRepo.findById(request.destinationAccountId)
            .orElseThrow { RuntimeException("Destination account not found") }

        if (sourceAcc.balance < request.amount) {
            throw RuntimeException("Insufficient funds")
        }

        accountRepo.save(sourceAcc.copy(balance = sourceAcc.balance - request.amount))
        accountRepo.save(destAcc.copy(balance = destAcc.balance + request.amount))

        val transaction = TransactionEntity(
            sourceAccountId = request.sourceAccountId,
            destinationAccountId = request.destinationAccountId,
            amount = request.amount
        )
        return transactionRepo.save(transaction)
    }

    @GetMapping("/transactions")
    fun listTransactions(): List<TransactionEntity> =
        transactionRepo.findAll()
}

data class TransferRequest(
    val sourceAccountId: Long,
    val destinationAccountId: Long,
    val amount: BigDecimal
)
