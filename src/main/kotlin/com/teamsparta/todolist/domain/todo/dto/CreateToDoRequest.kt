package com.teamsparta.todolist.domain.todo.dto

data class CreateToDoRequest(
        var title:String,
        var content: String,
        var date: String,
        var writer: String
)
