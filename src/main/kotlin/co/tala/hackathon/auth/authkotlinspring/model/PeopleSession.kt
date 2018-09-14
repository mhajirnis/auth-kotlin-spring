package co.tala.hackathon.auth.authkotlinspring.model

import java.sql.Timestamp
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(schema = "auth", name = "people_session")
data class PeopleSession(
        @Id @NotBlank @Column(name = "person_id") var personId : Int = 0,
        @NotBlank @Column(name = "device_id") var deviceId : String = "",
        @NotBlank @Column(name = "active") var active : Int = 0,
        @NotBlank @Column(name = "session_id") var sessionId : String = "",
        @Column(name = "created_at") var createdAt: Date = Timestamp(Date().time),
        @Column(name = "updated_at") var updatedAt: Date = Timestamp(Date().time)
)