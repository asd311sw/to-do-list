package com.teamsparta.todolist.domain.todo.model

import com.teamsparta.todolist.domain.comment.model.Comment
import com.teamsparta.todolist.domain.todo.dto.ToDoResponse
import com.teamsparta.todolist.domain.user.model.User
import jakarta.persistence.*

@Entity
@Table(name = "todo")
class ToDo(
        @Column(name = "title", nullable = false)
        var title: String,
        @Column(name = "content", nullable = false)
        var content: String,
        @Enumerated(EnumType.STRING)
        @Column(name = "status", nullable = false)
        var status: ToDoStatus = ToDoStatus.NOT_STARTED,
        @Column(name = "date", nullable = false)
        var date: String,
        @Column(name = "writer", nullable = false)
        var writer: String,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        var user: User,
        @OneToMany(mappedBy = "todo", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
        val comments:MutableList<Comment> = mutableListOf()
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null



}


fun ToDo.toResponse():ToDoResponse{

    return ToDoResponse(
            id,
            title,
            content,
            status.name,
            date,
            writer
    )
}