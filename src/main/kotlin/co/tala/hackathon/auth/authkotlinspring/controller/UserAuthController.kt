package co.tala.hackathon.auth.authkotlinspring.controller

import co.tala.hackathon.auth.authkotlinspring.model.OutgoingMessage
import co.tala.hackathon.auth.authkotlinspring.model.People
import co.tala.hackathon.auth.authkotlinspring.model.PeopleSession
import co.tala.hackathon.auth.authkotlinspring.repository.OutgoingMessageRepository
import co.tala.hackathon.auth.authkotlinspring.repository.PeopleRepository
import co.tala.hackathon.auth.authkotlinspring.repository.PeopleSessionRepository
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

/**
 * User authentication related endpoints
 */
@RestController
@EnableTransactionManagement
@RequestMapping("/v1/auth")
class UserAuthController(private val peopleRepository: PeopleRepository,
                         private val peopleSessionRepository: PeopleSessionRepository,
                         private val outgoingMessageRepository: OutgoingMessageRepository) {

    @Transactional
    @PostMapping("/session")
    fun createNewSession(@Valid @RequestBody requestBody: Map<String, Any>): ResponseEntity<Map<String, Any>> {
        val person = People()
        val savedPerson = peopleRepository.save(person)
        val metadataMap = requestBody["metadata"] as Map<String, Any>
        val deviceInfo = metadataMap["device"] as Map<String, String>
        val peopleSession = PeopleSession(personId = savedPerson.id,
                active = 0,
                deviceId = deviceInfo["androidId"]!!,
                sessionId = RandomStringUtils.randomAlphanumeric(32))
        return ResponseEntity.ok(mapOf("status" to "success", "sessionId" to peopleSessionRepository.save(peopleSession).sessionId))
    }

    @Transactional
    @PostMapping("/otp/send")
    fun sendOtp(@Valid @RequestHeader(required = true, name = "X-AUTH-SESSION-ID") sessionId: String,
                @Valid @RequestBody requestBody: Map<String, Any>): ResponseEntity<Map<String, Any>> {
        //Retrieve phone number
        val phoneNum = (requestBody["data"] as Map<String, Any>)["loginValue"]

        val peopleSession = peopleSessionRepository.findBySessionId(sessionId)!!
        if(!peopleSession.isPresent)
            return ResponseEntity.notFound().build()

        //TODO: Send 4-digit OTP via SMS toPhone client

        //Add record in outgoing_message table
        val outgoingMessage = OutgoingMessage(message = RandomStringUtils.randomNumeric(4),
                toPhone = phoneNum as String,
                toId = peopleSession.get().personId,
                uniqueId = RandomStringUtils.randomAlphanumeric(32))
        outgoingMessageRepository.save(outgoingMessage)

        return ResponseEntity.ok(mapOf("status" to "success", "statusDescription" to  "We have sent you a one time PIN"))
    }

    @Transactional
    @GetMapping("/otp/phone/{phoneNumber}")
    fun getOtp(@PathVariable(value = "phoneNumber") phoneNumber: String): ResponseEntity<Map<String, String>>    {
        val outgoingMessage = outgoingMessageRepository.findByToPhone(phoneNumber)
        return ResponseEntity.ok().body(mapOf("otp" to outgoingMessage.get().message!!))
    }

    @Transactional
    @PostMapping("/otp/verify")
    fun verifyOtp(@Valid @RequestHeader(required = true, name = "X-AUTH-SESSION-ID") sessionId: String,
                @Valid @RequestBody requestBody: Map<String, Any>): ResponseEntity<Map<String, Any>> {
        //Retrieve phone number
        val phoneNum = (requestBody["data"] as Map<String, Any>)["loginValue"]

        val peopleSession = peopleSessionRepository.findBySessionId(sessionId)!!
        if(!peopleSession.isPresent)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()

        //Retrieve OTP
        val otp = (requestBody["data"] as Map<String, Any>)["authValue"]
        val outgoingMessage = outgoingMessageRepository.findByToPhone(phoneNum as String)
        if(otp != outgoingMessage.get().message)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()

        return ResponseEntity.ok(mapOf("status" to "success", "statusDescription" to "Valid One Time PIN provided"))
    }

    @Transactional
    @PostMapping("/credentials")
    fun setPin(@Valid @RequestHeader(required = true, name = "X-AUTH-SESSION-ID") sessionId: String,
                  @Valid @RequestBody requestBody: Map<String, Any>): ResponseEntity<Map<String, Any>> {
        //Retrieve phone number
        val phoneNum = (requestBody["data"] as Map<String, Any>)["loginValue"]

        val peopleSession = peopleSessionRepository.findBySessionId(sessionId)!!
        if(!peopleSession.isPresent)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()

        return ResponseEntity.ok(mapOf("status" to "success",
                "statusDescription" to "Signup successful",
                "data" to mapOf("sessionId" to sessionId, "personId" to peopleSession.get().personId)))
    }
}
