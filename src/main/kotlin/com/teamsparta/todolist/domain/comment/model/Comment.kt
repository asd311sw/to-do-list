package com.teamsparta.todolist.domain.comment.model

import com.teamsparta.todolist.domain.comment.dto.CommentResponse
import com.teamsparta.todolist.domain.todo.model.ToDo
import jakarta.persistence.*


@Entity
@Table(name = "comment")
class Comment(
        @Column(name = "title", nullable = false)
        var title:String,
        @Column(name = "content", nullable = false)
        var content:String,
        @Column(name = "writer", nullable = false)
        var writer:String,
        @Column(name = "password", nullable = false)
        var password:String,
        @Column(name = "date", nullable = false)
        var date:String,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "todo_id")
        var todo:ToDo
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Long? = null

}


fun Comment.toResponse():CommentResponse =
        CommentResponse(
                id,
                title,
                content,
                writer,
                date
        )