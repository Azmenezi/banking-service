package com.example.banking_service.transaction

import jakarta.inject.Named
import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import java.math.BigDecimal

@Named
interface TransactionRepo : JpaRepository<TransactionEntity, Long>

@Entity
@Table(name = "transactions")
data class TransactionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    // Foreign keys referencing accounts
    val sourceAccountId: Long,
    val destinationAccountId: Long,

    val amount: BigDecimal
) {
    constructor(): this(null, 0L, 0L, BigDecimal.ZERO)
}
