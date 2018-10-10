package co.tala.hackathon.auth.authkotlinspring.model

import java.sql.Timestamp
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(schema = "auth", name = "people")
data class People(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @NotBlank @Column(name = "id") var id : Int = 0,
        @NotBlank @Column(name = "auth_type") var authType : String = "phone",
        @NotBlank @Column(name = "name") var name : String? = null,
        @Column(name = "first_name") var firstName : String? = null,
        @Column(name = "last_name") var lastName : String? = null,
        @Column(name = "phone") var phone : Long? = null,
        @Column(name = "dob") var dob : Date? = null,
        @Column(name = "email") var email : String ?= null,
        @Column(name = "email_username") var emailUsername: String?= null,
        @Column(name = "nid") var nid : String? = null,
        @Column(name = "nid_date") var nidDate : Date? = null,
        @Column(name = "sex") var sex : String? = null,
        @NotBlank @Column(name = "country") var country : String = "KE",
        @Column(name = "referrer") var referrer : Int? = 0,
        @NotBlank @Column(name = "signup_date") var signupDate : Date = Date(),
        @NotBlank @Column(name = "signup_source") var signupSource : String = "safari",
        @Column(name = "signup_source_type") var signupSourceType : String? = null,
        @Column(name = "middle_name") var middleName : String? = null,
        @NotBlank @Column(name = "lang") var lang : String = "en-KE",
        @NotBlank @Column(name = "updated_at") var updatedAt : Timestamp = Timestamp(Date().time)
)
