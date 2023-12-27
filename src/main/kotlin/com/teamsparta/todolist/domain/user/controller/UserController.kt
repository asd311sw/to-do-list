package com.teamsparta.todolist.domain.user.controller

import com.teamsparta.todolist.domain.user.dto.SignUpUserRequest
import com.teamsparta.todolist.domain.user.dto.UpdateUserRequest
import com.teamsparta.todolist.domain.user.dto.UserResponse
import com.teamsparta.todolist.domain.user.service.UserService
import org.apache.coyote.Response
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
class UserController(private val userService: UserService) {

    @PostMapping("/signup")
    fun signUp(@RequestBody signUpUserRequest: SignUpUserRequest): ResponseEntity<UserResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.signUp(signUpUserRequest))
    }


    @PutMapping("/users/{userId}/profile")
    fun updateUser(@PathVariable userId: Long, @RequestBody updateUserRequest: UpdateUserRequest): ResponseEntity<UserResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUserProfile(userId,updateUserRequest))
    }


}