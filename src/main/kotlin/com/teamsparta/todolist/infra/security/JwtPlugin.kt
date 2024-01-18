package com.teamsparta.todolist.infra.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.time.Instant
import java.util.*



@Component
class JwtPlugin(
    @Value("\${auth.jwt.issuer}")
    private val issuer: String,
    @Value("\${auth.jwt.secret}")
    private val secret: String,
    @Value("\${auth.jwt.accessTokenExpirationHour}")
    private val accessTokenExpirationHour: Long
) {


    companion object {
        const val ISSUER = "team.sparta.com"
        const val SECRET = "PO4c8z41Hia5gJG3oeuFJMRYBB4Ws4aZ"
        const val ACCESS_TOKEN_EXPIRATION_HOUR: Long = 168
    }

    // kotlin에서는 try catch 대신에 아래와 같은 Result 형태로 exception 처리를 할 수 있습니다.
    fun validateToken(jwt: String): Result<Jws<Claims>> {
        return kotlin.runCatching {
            val key = Keys.hmacShaKeyFor(SECRET.toByteArray(StandardCharsets.UTF_8))
            // 우리의 key로 서명을 검증하고, 만료시간을 체크합니다.
            Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt)
        }
    }


    fun generateAccessToken(subject: String, email: String, role: String): String {
        // subject, 만료기간과 role을 설정합니다.
        return generateToken(subject, email, role, Duration.ofHours(ACCESS_TOKEN_EXPIRATION_HOUR))
    }


    private fun generateToken(subject: String, email: String, role: String, expirationPeriod: Duration): String {
        // custom claim을 설정합니다.
        val claims: Claims = Jwts.claims()
            .add(mapOf("role" to role, "email" to email))
            .build()

        val key = Keys.hmacShaKeyFor(SECRET.toByteArray(StandardCharsets.UTF_8))
        val now = Instant.now()

        return Jwts.builder()
            .subject(subject)
            .issuer(ISSUER)
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plus(expirationPeriod)))
            .claims(claims)
            .signWith(key)
            .compact()
    }
}