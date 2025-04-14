package com.example.banking_service.user

import org.springframework.web.bind.annotation.*

@RestController
class UserController(
    private val userRepo: UserRepo
) {

    @PostMapping("/users/v1/register")
    fun createUser(@RequestBody request: UserRequest): UserEntity {
        return userRepo.save(
            UserEntity(
                username = request.username,
                password = request.password
            )
        )
    }

    @GetMapping("/users")
    fun listAllUsers(): List<UserEntity> =
        userRepo.findAll()
}

data class UserRequest(
    val username: String,
    val password: String
)
