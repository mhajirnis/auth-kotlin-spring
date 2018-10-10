package co.tala.hackathon.auth.authkotlinspring.model

import java.sql.Timestamp
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(schema = "auth", name = "outgoing_message")
data class OutgoingMessage(
        @GeneratedValue(strategy = GenerationType.IDENTITY) @Id @NotBlank @Column(name = "id") var id : Int = 0,
        @NotBlank @Column(name = "message_type") var messageType : String = "sms",
        @Column(name = "template") var template : Int? = 110,
        @Column(name = "message") var message : String? = null,
        @NotBlank @Column(name = "to_phone") var toPhone : String = "",
        @Column(name = "params") var params : String? = null,
        @Column(name = "to_id") var toId : Int? = null,
        @Column(name = "project") var project : String? = null,
        @NotBlank @Column(name = "unique_id") var uniqueId : String = "",
        @Column(name = "created_at") var createdAt : Date? = Timestamp(Date().time),
        @NotBlank @Column(name = "processing_status") var processingStatus : Int = 0,
        @NotBlank @Column(name = "processing_tries") var processingTries : Int = 0,
        @Column(name = "updated_at") var updatedAt : Date = Timestamp(Date().time)
)
