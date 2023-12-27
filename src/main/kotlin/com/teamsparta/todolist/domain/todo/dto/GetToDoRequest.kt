package com.teamsparta.todolist.domain.todo.dto

import javax.swing.text.StyledEditorKit.BoldAction

data class GetToDoRequest(
        val writer:String,
        val ascending:Boolean
)
