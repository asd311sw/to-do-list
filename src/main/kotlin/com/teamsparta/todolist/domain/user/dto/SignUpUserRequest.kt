package com.teamsparta.todolist.domain.user.dto

data class SignUpUserRequest(
        val email:String,
        val name:String,
        val password:String,
        val role:String
)
