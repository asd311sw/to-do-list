package com.teamsparta.todolist.domain.comment.controller

import com.teamsparta.todolist.domain.comment.dto.CommentResponse
import com.teamsparta.todolist.domain.comment.dto.CreateCommentRequest
import com.teamsparta.todolist.domain.comment.dto.DeleteCommentRequest
import com.teamsparta.todolist.domain.comment.dto.UpdateCommentRequest
import com.teamsparta.todolist.domain.user.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RequestMapping("/todos/{toDoId}/comments")
@RestController
class CommentController(private val userService: UserService){

    @GetMapping
    fun getCommentList(@PathVariable toDoId: Long):ResponseEntity<List<CommentResponse>>{
        return ResponseEntity.status(HttpStatus.OK).body(userService.getCommentList(toDoId))
    }

    @GetMapping("/{commentId}")
    fun getCommentById(@PathVariable toDoId: Long,@PathVariable commentId: Long):ResponseEntity<CommentResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getCommentById(toDoId,commentId))
    }

    @PostMapping
    fun createComment(@PathVariable toDoId:Long,@RequestBody createCommentRequest: CreateCommentRequest):ResponseEntity<CommentResponse>{

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createComment(toDoId,createCommentRequest))
    }

    @PutMapping("/{commentId}")
    fun updateComment(@PathVariable toDoId:Long,@PathVariable commentId:Long,@RequestBody updateCommentRequest: UpdateCommentRequest):ResponseEntity<CommentResponse>{

        return ResponseEntity.status(HttpStatus.OK).body(userService.updateComment(toDoId,commentId,updateCommentRequest))
    }

    @DeleteMapping("/{commentId}")
    fun deleteComment(@PathVariable toDoId:Long,@PathVariable commentId:Long,@RequestBody deleteCommentRequest: DeleteCommentRequest):ResponseEntity<CommentResponse> {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(userService.deleteComment(toDoId,commentId,deleteCommentRequest))
    }




}