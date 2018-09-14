package co.tala.hackathon.auth.authkotlinspring.repository

import co.tala.hackathon.auth.authkotlinspring.model.PeopleSession
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface PeopleSessionRepository : JpaRepository<PeopleSession, Int> {
    fun findBySessionId(sessionId: String): Optional<PeopleSession>
}