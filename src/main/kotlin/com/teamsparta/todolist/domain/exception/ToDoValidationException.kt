package com.teamsparta.todolist.domain.exception

data class ToDoValidationException(val element:String):RuntimeException(
        "${element}에서 조건을 충족하지 않습니다!"
)
