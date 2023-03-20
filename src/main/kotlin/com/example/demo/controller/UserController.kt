package com.example.demo.controller

import com.example.demo.model.*
import com.example.demo.service.UserService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/user")
@CrossOrigin
class UserController(val service: UserService) {

    data class Credentials(val username: String, val password: String)

    @GetMapping("/getAll")
    fun getAllUsers() = service.getAll()

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Long) = service.getById(id)

    @GetMapping("/available/email/{email}")
    fun checkEmail(@PathVariable email: String): Boolean = service.checkEmail(email)

    @GetMapping("/available/username/{username}")
    fun checkUsername(@PathVariable username: String): Boolean = service.checkUsername(username)

    //http://localhost:8080/api/v1/user/edit/${userid}/${this.inputName}

    @GetMapping("/edit/{field}/{userId}/{newValue}")
    fun changeUser(@PathVariable userId: Long, @PathVariable newValue: String, @PathVariable field: String) = service.updateUserByUserId(userId,newValue,field)

    @PostMapping("/edit/password")
    @ResponseStatus(HttpStatus.CREATED)
    fun changePassword(
        @Valid
        @RequestBody data: NewUserPassword
    ) = service.updateUserPassword(data)

    @PostMapping("/edit/password/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    fun changePasswordvia(
        @Valid
        @RequestBody data: NewUserPassword,
        @PathVariable userId: Long
    ) = service.updateUserPasswordVia(data,userId)

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    fun saveUser(
        @Valid
        @RequestBody data: UserIn
    ): UserDB = service.create(data)

    @PostMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    fun updateUser(
        @Valid
        @RequestBody data: UserEditForm
    ) = service.updateUser(data)

    @GetMapping("/blockUser/{username}")
    fun blockUser(@PathVariable username: String) = service.blockUser(username)

//    @PostMapping("/blockUser")
//    @ResponseStatus(HttpStatus.OK)
//    fun blockUser(
//        @Valid
//        @RequestBody data: UserEdit
//    ) = service.blockUser(data)

    @GetMapping("/unblockUser/{username}")
    fun unblockUser(@PathVariable username: String) = service.unblockUser(username)

//    @PostMapping("/unblockUser")
//    @ResponseStatus(HttpStatus.OK)
//    fun unblockUser(
//        @Valid
//        @RequestBody data: UserEdit
//    ) = service.unblockUser(data)


    @GetMapping("/deleteUser/{username}")
    fun deleteUser(@PathVariable username: String) = service.deleteUser(username)

    @PostMapping("/login")
    fun handleLogin(@RequestBody credentials: Credentials): ResponseEntity<Any> {
        val username = credentials.username
        val password = credentials.password

        // Attempt to log the user in
        val locked = service.isLocked(username)
        if (locked) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build()
        }
        val success = service.login(username, password)

        // Return a response indicating whether the login was successful
        if (success) {
            val userPermissions = service.getUserPermissions(username)
            val user_id = service.findUser(username)?.id
            val jwt = Jwts.builder()
                .claim("username", username)
                .claim("permissions", userPermissions)
                .claim("user_id", user_id)
                .signWith(SignatureAlgorithm.HS256, "59fe218a8ce94d1f975260fab259d22a599751a8b47c4fbd8dc94ff4738658e8")
                .compact()

            // Return the JWT token in the response
            return ResponseEntity.ok(mapOf("token" to jwt))
        } else {
            // Return an error response if the login failed
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }

}