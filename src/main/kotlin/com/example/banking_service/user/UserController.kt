package com.example.banking_service.user

import org.springframework.web.bind.annotation.*

@RestController
class UserController(
    private val userRepo: UserRepo
) {

    @GetMapping("/users")
    fun listAllUsers(): List<UserEntity> =
        userRepo.findAll()
}

data class UserRequest(
    val username: String,
    val password: String
)
