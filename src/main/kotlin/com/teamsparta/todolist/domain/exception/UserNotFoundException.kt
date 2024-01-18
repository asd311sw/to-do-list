package com.teamsparta.todolist.domain.exception

class UserNotFoundException:RuntimeException(
    "Email or Password is not correct. Please Retry!"
)