package com.teamsparta.todolist.domain.user.dto

data class SigninUserRequest(
    val email:String,
    val password:String,
    val role:String
)
