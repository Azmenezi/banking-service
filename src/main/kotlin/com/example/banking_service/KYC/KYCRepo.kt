package com.example.banking_service.KYC

import jakarta.inject.Named
import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import java.math.BigDecimal
import java.time.LocalDate

@Named
interface KYCRepo : JpaRepository<KYCEntity, Long>

@Entity
@Table(name = "kyc")
data class KYCEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    // Foreign key to user
    val userId: Long,

    val dateOfBirth: LocalDate,
    val nationality: String,
    val salary: BigDecimal
) {
    constructor(): this(null, 0L, LocalDate.now(), "", BigDecimal.ZERO)
}
