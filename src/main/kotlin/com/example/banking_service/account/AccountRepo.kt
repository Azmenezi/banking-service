package com.example.banking_service.account

import jakarta.inject.Named
import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import java.math.BigDecimal

@Named
interface AccountRepo : JpaRepository<AccountEntity, Long>

@Entity
@Table(name = "accounts")
data class AccountEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    // Foreign key to user
    val userId: Long,

    val balance: BigDecimal,
    val isActive: Boolean,
    val accountNumber: String
) {
    constructor(): this(null, 0L, BigDecimal.ZERO, true, "")
}
