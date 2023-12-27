package com.teamsparta.todolist.domain.todo.controller

import com.teamsparta.todolist.domain.todo.dto.CreateToDoRequest
import com.teamsparta.todolist.domain.todo.dto.GetToDoRequest
import com.teamsparta.todolist.domain.todo.dto.ToDoResponse
import com.teamsparta.todolist.domain.todo.dto.UpdateToDoRequest
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


@RequestMapping("/users/{userId}/todos")
@RestController
class ToDoController(private val userService: UserService) {

    @GetMapping
    fun getToDoList(@PathVariable userId: Long):ResponseEntity<List<ToDoResponse>> {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getToDoList(userId))
    }

    @GetMapping("/{toDoId}")
    fun getToDoById(@PathVariable userId: Long,@PathVariable toDoId:Long):ResponseEntity<ToDoResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getToDoById(userId,toDoId))
    }


    @PostMapping
    fun createToDo(@PathVariable userId: Long,@RequestBody createToDoRequest: CreateToDoRequest):ResponseEntity<ToDoResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createToDo(userId,createToDoRequest))
    }

    @PutMapping("/{toDoId}")
    fun updateToDo(@PathVariable userId: Long,@PathVariable toDoId: Long,@RequestBody updateToDoRequest: UpdateToDoRequest):ResponseEntity<ToDoResponse>{
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateToDo(userId,toDoId,updateToDoRequest))
    }


    @DeleteMapping("/{toDoId}")
    fun deleteToDo(@PathVariable userId: Long,@PathVariable toDoId: Long):ResponseEntity<Unit>{
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(userService.deleteToDo(userId,toDoId))
    }

}



