package com.teamsparta.todolist.domain.exception

data class ModelNotFoundException(
        val modelName:String,
        val id:Long
):RuntimeException("${modelName} is not Found")