package com.example.demo.service

import com.example.demo.model.*
import com.example.demo.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class UserService(private val repository: UserRepository) {

    fun getAll(): List<UserControl> = repository.findAll().map {
        it.convertToUserControl()
    }

    fun create(data: UserIn): UserDB = repository.save(data.convertToDBModel())

    fun getUserPermissions(username: String): String? {
        // Retrieve the user's permissions
        val user = repository.findByUsername(username)
        val permissions = user?.userPermission

        // Return the user's permissions
        return permissions
    }

    fun findUser(username: String): UserDB? {
        return repository.findByUsername(username)
    }

    fun checkEmail(mail: String): Boolean {
        return repository.findByEmail(mail) == null
    }

    fun checkUsername(username: String): Boolean {
        return repository.findByUsername(username) == null
    }

    fun getById(id: Long): UserEditForm {
        return repository.findById(id).orElseThrow().convertToUserEditForm()
    }

    fun updateUser(data: UserEditForm): UserEditForm {
        var user = repository.findById(data.id).orElseThrow()
        if (data.fname != user.fname) {
            user.fname = data.fname
        }
        if (data.lname != user.lname) {
            user.lname = data.lname
        }
        if (data.username != user.username) {
            user.username = data.username
        }
        if (data.email != user.email) {
            user.email = data.email
        }
        return repository.save(user).convertToUserEditForm()
    }

    fun updateUserByUserId(userId: Long, newValue: String, field: String): UserEditForm {
        var user = repository.findById(userId).orElseThrow()
        if (field == "username") {
            user.username = newValue;
        } else if (field == "email") {
            user.email = newValue;
        } else if (field == "lname") {
            user.lname = newValue;
        } else if (field == "fname") {
            user.fname = newValue;
        }
        return repository.save(user).convertToUserEditForm()
    }

    fun updateUserPassword(data: NewUserPassword) : UserEditForm {
        var user = repository.findById(data.userId).orElseThrow()
        user.password = data.password
        return repository.save(user).convertToUserEditForm()
    }

    fun updateUserPasswordVia(data: NewUserPassword, userId: Long) : UserEditForm {
        var user = repository.findById(userId).orElseThrow()
        user.password = data.password
        return repository.save(user).convertToUserEditForm()
    }


    fun blockUser(username: String) {
        return repository.setLocked(true, username)
    }

    fun unblockUser(username: String) {
        return repository.setLocked(false, username)
    }

    fun deleteUser(username: String) {
        val user = repository.findByUsername(username)
        if (user == null) {
            return
        } else {
            return repository.deleteById(user.id!!)
        }
    }

    fun login(username: String, password: String): Boolean {
        // Check if the username and password are valid
        val user = repository.findByUsername(username)
        val isValid = (user != null) && (user.password == password)

        // Return whether the credentials are valid
        return isValid
    }

    fun isLocked(username: String): Boolean {
        val user = repository.findByUsername(username)
        return user?.locked ?: false
    }
}