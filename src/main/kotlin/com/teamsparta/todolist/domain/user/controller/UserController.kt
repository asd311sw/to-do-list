package com.teamsparta.todolist.domain.user.controller

import com.teamsparta.todolist.domain.user.dto.*
import com.teamsparta.todolist.domain.user.service.UserService
import org.apache.coyote.Response
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*


@RestController
class UserController(private val userService: UserService) {

    @PostMapping("/signup")
    fun signUp(@RequestBody signUpUserRequest: SignUpUserRequest): ResponseEntity<UserResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.signUp(signUpUserRequest))
    }


    @PutMapping("/users/{userId}/profile")
    @PreAuthorize("hasRole('ADMIN')")
    fun updateUser(@PathVariable userId: Long, @RequestBody updateUserRequest: UpdateUserRequest): ResponseEntity<UserResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUserProfile(userId,updateUserRequest))
    }

    @PostMapping("/signin")
    fun signin(@RequestBody singinUserRequest: SigninUserRequest):ResponseEntity<SigninResponse> {

        return ResponseEntity.status(HttpStatus.OK).body(userService.signin(singinUserRequest))
    }


}