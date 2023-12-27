package com.teamsparta.todolist.domain.user.dto

data class UserResponse(
        val id:Long?,
        val name:String,
        val email:String,
        val job:String
)
