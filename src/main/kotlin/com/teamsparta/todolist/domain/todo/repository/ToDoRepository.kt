package com.teamsparta.todolist.domain.todo.repository

import com.teamsparta.todolist.domain.todo.model.ToDo
import org.springframework.data.jpa.repository.JpaRepository

interface ToDoRepository:JpaRepository<ToDo,Long> {

    fun findByUserIdAndId(userId:Long,toDoId:Long):ToDo?

}