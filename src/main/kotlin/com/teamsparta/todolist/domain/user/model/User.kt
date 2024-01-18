package com.teamsparta.todolist.domain.user.model

import com.teamsparta.todolist.domain.todo.model.ToDo
import com.teamsparta.todolist.domain.user.dto.UserResponse
import jakarta.persistence.*

@Entity
@Table(name = "todo_user")
class User(
    @Column(name = "name", nullable = false)
    var name: String,
    @Column(name = "email")
    var email: String,

    @Column(name = "password", nullable = false)
    var password: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "job", nullable = false)
    val job: UserRole = UserRole.USER,


    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    val todos: MutableList<ToDo> = mutableListOf()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null


    fun addToDo(toDo: ToDo) {
        todos.add(toDo)

    }

    fun removeToDo(toDo: ToDo) {
        todos.remove(toDo)
    }

}


fun User.toResponse(): UserResponse {
    return UserResponse(
        id,
        name,
        email,
        job.name
    )
}