package com.teamsparta.todolist.domain.comment.repository

import com.teamsparta.todolist.domain.comment.model.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository:JpaRepository<Comment,Long> {

    fun findByTodoIdAndId(todoId:Long,commentId:Long):Comment?

}