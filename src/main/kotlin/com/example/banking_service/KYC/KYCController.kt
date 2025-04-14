package com.example.banking_service.KYC

import org.springframework.web.bind.annotation.*
import java.math.BigDecimal
import java.time.LocalDate

@RestController
class KYCController(
    private val kycRepo: KYCRepo
) {
    @PostMapping("/users/v1/kyc")
    fun createOrUpdateKyc(@RequestBody request: KYCRequest): KYCEntity {


        val kyc = kycRepo.findAll().firstOrNull { it.userId == request.userId }
            ?: KYCEntity(
                userId = request.userId,
                dateOfBirth = request.dateOfBirth,
                nationality = request.nationality,
                salary = request.salary
            )

        return kycRepo.save(
            kyc.copy(
                userId = request.userId,
                dateOfBirth = request.dateOfBirth,
                nationality = request.nationality,
                salary = request.salary
            )
        )
    }

    @GetMapping("/users/v1/kyc/{userId}")
    fun getKycByUserId(@PathVariable userId: Long): List<KYCEntity> {
        return kycRepo.findAll().filter { it.userId == userId }
    }
}

data class KYCRequest(
    val userId: Long,
    val dateOfBirth: LocalDate,
    val nationality: String,
    val salary: BigDecimal
)
