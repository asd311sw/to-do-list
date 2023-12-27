package com.teamsparta.todolist.domain.todo.dto

import com.teamsparta.todolist.domain.todo.model.ToDoStatus

data class ToDoResponse(
        var id: Long?,
        var title: String,
        var content: String,
        var status:String,
        var date: String,
        var writer: String,
)
