package com.teamsparta.todolist.domain.user.service

import com.teamsparta.todolist.domain.comment.dto.CommentResponse
import com.teamsparta.todolist.domain.comment.dto.CreateCommentRequest
import com.teamsparta.todolist.domain.comment.dto.DeleteCommentRequest
import com.teamsparta.todolist.domain.comment.dto.UpdateCommentRequest
import com.teamsparta.todolist.domain.comment.model.Comment
import com.teamsparta.todolist.domain.comment.model.toResponse
import com.teamsparta.todolist.domain.comment.repository.CommentRepository
import com.teamsparta.todolist.domain.exception.ModelNotFoundException
import com.teamsparta.todolist.domain.exception.ToDoValidationException
import com.teamsparta.todolist.domain.exception.UserNotFoundException
import com.teamsparta.todolist.domain.todo.dto.CreateToDoRequest
import com.teamsparta.todolist.domain.todo.dto.GetToDoRequest
import com.teamsparta.todolist.domain.todo.dto.ToDoResponse
import com.teamsparta.todolist.domain.todo.dto.UpdateToDoRequest
import com.teamsparta.todolist.domain.todo.model.ToDo
import com.teamsparta.todolist.domain.todo.model.ToDoStatus
import com.teamsparta.todolist.domain.todo.model.toResponse
import com.teamsparta.todolist.domain.todo.repository.ToDoRepository
import com.teamsparta.todolist.domain.user.dto.*
import com.teamsparta.todolist.domain.user.model.User
import com.teamsparta.todolist.domain.user.model.UserRole
import com.teamsparta.todolist.domain.user.model.toResponse
import com.teamsparta.todolist.domain.user.repository.UserRepository
import com.teamsparta.todolist.infra.security.JwtPlugin
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val toDoRepository: ToDoRepository,
    private val commentRepository: CommentRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtPlugin: JwtPlugin
) : UserService {


    @Transactional
    override fun signin(request: SigninUserRequest): SigninResponse {
        val user = userRepository.findByEmail(request.email) ?: throw UserNotFoundException()

        if (!passwordEncoder.matches(request.password,user.password))
            throw UserNotFoundException()



        return SigninResponse(
            user.name,
            user.email,
            user.job.name,
            jwtPlugin.generateAccessToken(
                user.id.toString(),
                user.email,
                user.job.name
            )
        )
    }


    @Transactional
    override fun signUp(request: SignUpUserRequest): UserResponse {

        if (userRepository.existsUserByEmail(request.email))
            throw IllegalArgumentException("Email is already in use. Please use another email!")

        //email이 이미 존재하는 경우 예외 처리


        return userRepository.save(
            User(
                request.name,
                request.email,
                passwordEncoder.encode(request.password),
                when (request.role) {
                    "USER" -> UserRole.USER
                    "ADMIN" -> UserRole.ADMIN
                    else -> UserRole.USER
                }
            )
        ).toResponse()

    }

    @Transactional
    override fun updateUserProfile(userId: Long, request: UpdateUserRequest): UserResponse {
        var user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException(request.name, userId)

        user.name = request.name
        user.email = request.email

        return userRepository.save(user).toResponse()
    }

    override fun getToDoList(userId: Long): List<ToDoResponse> {
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User", userId)

        var res: List<ToDo>? = null
        res = user.todos.sortedByDescending { it.date }

        //res = user.todos.sortedBy { it.date }

        return res.map { it.toResponse() }

    }

    override fun getToDoById(userId: Long, toDoId: Long): ToDoResponse {
        val todo = toDoRepository.findByUserIdAndId(userId, toDoId) ?: throw ModelNotFoundException("ToDo", toDoId)

        return todo.toResponse()

    }


    @Transactional
    override fun createToDo(userId: Long, request: CreateToDoRequest): ToDoResponse {
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User", userId)

        val title_len = request.title.length
        val content_len = request.content.length

        if (!(title_len >= 1 && title_len <= 200))
            throw ToDoValidationException("titie")

        if (!(content_len >= 1 && content_len <= 1000))
            throw ToDoValidationException("content")

        val todo = ToDo(
            title = request.title,
            content = request.content,
            date = request.date,
            writer = request.writer,
            user = user
        )

        user.addToDo(todo)
        userRepository.save(user)

        return todo.toResponse()
    }

    @Transactional
    override fun updateToDo(userId: Long, toDoId: Long, request: UpdateToDoRequest): ToDoResponse {
        var todo = toDoRepository.findByUserIdAndId(userId, toDoId) ?: throw ModelNotFoundException("ToDo", toDoId)

        val title_len = request.title.length
        val content_len = request.content.length

        if (!(title_len >= 1 && title_len <= 200))
            throw ToDoValidationException("titie")

        if (!(content_len >= 1 && content_len <= 1000))
            throw ToDoValidationException("content")


        todo.apply {
            title = request.title
            content = request.content
            date = request.date
            status = when (request.status) {
                "ongoing" -> ToDoStatus.ONGOING
                "not_started" -> ToDoStatus.NOT_STARTED
                "finished" -> ToDoStatus.FINISHED
                else -> ToDoStatus.NOT_STARTED
            }
            writer = request.writer
        }


        return toDoRepository.save(todo).toResponse()
    }

    @Transactional
    override fun deleteToDo(userId: Long, toDoId: Long) {
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("ToDo", toDoId)

        val todo = toDoRepository.findByUserIdAndId(userId, toDoId) ?: throw ModelNotFoundException("ToDo", toDoId)

        user.removeToDo(todo)

        userRepository.save(user)
    }

    override fun getCommentList(toDoId: Long): List<CommentResponse> {

        val todo = toDoRepository.findByIdOrNull(toDoId) ?: throw ModelNotFoundException("ToDo", toDoId)


        return todo.comments.map { it.toResponse() }
    }

    override fun getCommentById(toDoId: Long, commentId: Long): CommentResponse {
        val comment =
            commentRepository.findByTodoIdAndId(toDoId, commentId) ?: throw ModelNotFoundException("Comment", commentId)

        return comment.toResponse()
    }

    @Transactional
    override fun createComment(toDoId: Long, request: CreateCommentRequest): CommentResponse {
        val todo = toDoRepository.findByIdOrNull(toDoId) ?: throw ModelNotFoundException("ToDo", toDoId)

        val comment = Comment(
            title = request.title,
            content = request.content,
            writer = request.writer,
            date = request.date,
            password = request.password,
            todo = todo
        )

        todo.comments.add(comment)

        toDoRepository.save(todo)

        return comment.toResponse()
    }

    @Transactional
    override fun updateComment(toDoId: Long, commentId: Long, request: UpdateCommentRequest): CommentResponse {
        val comment =
            commentRepository.findByTodoIdAndId(toDoId, commentId) ?: throw ModelNotFoundException("Comment", commentId)

        if (comment.writer != request.writer || comment.password != request.password)
            throw IllegalStateException("Forbidden,접근 권한이 없습니다!")

        comment.apply {
            title = request.title
            content = request.content
            writer = request.writer
            date = request.date
            password = request.password
        }

        return commentRepository.save(comment).toResponse()


    }


    @Transactional
    override fun deleteComment(toDoId: Long, commentId: Long, request: DeleteCommentRequest): CommentResponse {
        val todo = toDoRepository.findByIdOrNull(toDoId) ?: throw ModelNotFoundException("ToDo", toDoId)
        val comment =
            commentRepository.findByTodoIdAndId(toDoId, commentId) ?: throw ModelNotFoundException("Comment", commentId)

        if (comment.writer != request.writer || comment.password != request.password)
            throw IllegalStateException("Forbidden,접근 권한이 없습니다!")

        todo.comments.remove(comment)

        toDoRepository.save(todo)

        return comment.toResponse()
    }


}