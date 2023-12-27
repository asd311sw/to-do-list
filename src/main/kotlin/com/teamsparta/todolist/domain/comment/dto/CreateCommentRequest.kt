package com.teamsparta.todolist.domain.comment.dto

data class CreateCommentRequest(
        val title:String,
        val content:String,
        val writer:String,
        val password:String,
        val date:String
)