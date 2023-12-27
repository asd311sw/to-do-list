package com.teamsparta.todolist.domain.comment.dto

data class UpdateCommentRequest(
        val title:String,
        val content:String,
        val writer:String,
        val password:String,
        val date:String
)
