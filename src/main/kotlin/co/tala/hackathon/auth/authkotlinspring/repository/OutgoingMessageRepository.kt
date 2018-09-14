package co.tala.hackathon.auth.authkotlinspring.repository

import co.tala.hackathon.auth.authkotlinspring.model.OutgoingMessage
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface OutgoingMessageRepository : JpaRepository<OutgoingMessage, Int>   {
    fun findByToPhone(phoneNum: String): Optional<OutgoingMessage>
}