package com.example.demo.repository

import com.example.demo.model.UserDB
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository


@Repository
interface UserRepository: JpaRepository<UserDB, Long> {
    fun findByUsername(username: String): UserDB?
    fun findByEmail(username: String): UserDB?

    @Modifying
    @Transactional
    @Query("update UserDB u set u.locked = ?1 where u.username = ?2")
    fun setLocked(locked: Boolean, username: String)
}