package co.tala.hackathon.auth.authkotlinspring.repository

import co.tala.hackathon.auth.authkotlinspring.model.People
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PeopleRepository : JpaRepository<People, Int> {

    /**
     * Returns maximum id
     */
    @Query("SELECT coalesce(max(t.id), 0) FROM People t")
    fun findByMaxId() : Optional<Int>

    /**
     * Returns the Person entry whose phone is given  as a method parameter.
     */
    fun findByPhone(phone : Long): Optional<People>

}
