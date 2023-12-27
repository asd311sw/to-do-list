package com.teamsparta.todolist.domain.comment.dto
//
data class CommentResponse(
        val id:Long?,
        val title:String,
        val content:String,
        val writer:String,
        val date:String
)
