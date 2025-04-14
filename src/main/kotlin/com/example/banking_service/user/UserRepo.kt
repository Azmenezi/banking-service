package com.example.banking_service.user

import jakarta.inject.Named
import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository

@Named
interface UserRepo : JpaRepository<UserEntity, Long>


@Entity
@Table(name = "users")
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    val username: String,
    val password: String,

) {
    constructor() : this(null, "", "")
}
