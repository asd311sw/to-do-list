package com.teamsparta.todolist.domain.todo.dto

data class UpdateToDoRequest(
        var title:String,
        var content: String,
        var status:String,
        var date: String,
        var writer: String,
)
