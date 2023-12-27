package com.teamsparta.todolist.domain.user.service

import com.teamsparta.todolist.domain.comment.dto.CommentResponse
import com.teamsparta.todolist.domain.comment.dto.CreateCommentRequest
import com.teamsparta.todolist.domain.comment.dto.DeleteCommentRequest
import com.teamsparta.todolist.domain.comment.dto.UpdateCommentRequest
import com.teamsparta.todolist.domain.todo.dto.CreateToDoRequest
import com.teamsparta.todolist.domain.todo.dto.GetToDoRequest
import com.teamsparta.todolist.domain.todo.dto.ToDoResponse
import com.teamsparta.todolist.domain.todo.dto.UpdateToDoRequest
import com.teamsparta.todolist.domain.user.dto.SignUpUserRequest
import com.teamsparta.todolist.domain.user.dto.UpdateUserRequest
import com.teamsparta.todolist.domain.user.dto.UserResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable

interface UserService {

    fun signUp(request: SignUpUserRequest): UserResponse
    fun updateUserProfile(userId: Long, request: UpdateUserRequest): UserResponse

    fun getToDoList(userId: Long): List<ToDoResponse>
    fun getToDoById(userId: Long, toDoId: Long): ToDoResponse
    fun createToDo(userId: Long, request: CreateToDoRequest): ToDoResponse
    fun updateToDo(userId: Long, toDoId: Long, request: UpdateToDoRequest): ToDoResponse
    fun deleteToDo(userId: Long, toDoId: Long)

    fun getCommentList(toDoId: Long): List<CommentResponse>
    fun getCommentById(toDoId: Long, commentId: Long): CommentResponse
    fun createComment(toDoId: Long, request: CreateCommentRequest): CommentResponse
    fun updateComment(toDoId: Long, commentId: Long, request: UpdateCommentRequest): CommentResponse
    fun deleteComment(toDoId: Long, commentId: Long, request: DeleteCommentRequest): CommentResponse

}