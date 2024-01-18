package com.teamsparta.todolist.domain.user.dto

data class SigninResponse(
    val name:String,
    val email:String,
    val job:String,
    val token:String
)
