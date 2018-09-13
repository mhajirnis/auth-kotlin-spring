package co.tala.hackathon.auth.authkotlinspring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AuthKotlinSpringApplication

fun main(args: Array<String>) {
    runApplication<AuthKotlinSpringApplication>(*args)
}
