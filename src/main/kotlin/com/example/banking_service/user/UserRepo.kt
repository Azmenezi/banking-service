package com.example.banking_service.user

import jakarta.inject.Named
import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository

@Named
interface UserRepo : JpaRepository<UserEntity, Long> {
    fun findByUsername(username: String): UserEntity?
}


@Entity
@Table(name = "users")
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    
    @Column(unique = true)
    val username: String,
    val password: String,

    @Enumerated(EnumType.STRING)
    val role: Roles = Roles.USER

) {
    constructor() : this(0, "username", "password", Roles.USER)
}

enum class Roles {
    USER, ADMIN
}