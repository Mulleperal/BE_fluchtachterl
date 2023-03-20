package com.example.demo.model

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Entity
@Table(name = "user")
data class UserDB(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    var fname: String,
    var lname: String,
    @Column(unique = true)
    var username: String,
    var email: String,
    var password: String,
    var locked: Boolean = false,
    var userPermission: String = "User"
)

// Req/Resp Marshalling
open class UserIn(
    @field: NotBlank
    open val fname: String,
    @field: NotBlank
    open val lname: String,
    @field: Size(min = 3, max=15)
    @field: NotBlank
    open val username: String,
    @field: Email
    @field: NotBlank
    open val email: String,
    @field: Size(min=8)
    @field: NotBlank
    val password: String,
)

data class UserBase(
    var email: String?,
    var username: String?
)

data class UserEditForm(
    var id: Long,
    var fname: String,
    var lname: String,
    var username: String,
    var email: String
)

data class UserControl(
    var id: Long,
    var fname: String,
    var lname: String,
    var username: String,
    var email: String,
    var locked: Boolean,
    var userPermission: String

)

data class NewUserPassword(
    var userId: Long,
    var currentPassword: String,
    var password: String,
    var passwordCheck: String,
)


fun UserDB.convertToUserControl() = UserControl(
    id = this.id!!,
    fname = this.fname,
    lname = this.lname,
    username = this.username,
    email = this.email,
    locked = this.locked,
    userPermission = this.userPermission,
)

fun UserDB.convertToUserEditForm() = UserEditForm(
    id = this.id!!,
    fname = this.fname,
    lname = this.lname,
    username = this.username,
    email = this.email,
)

fun UserDB.convertToOutModel() = UserBase(
    email = this.email,
    username = this.username
)

fun UserIn.convertToDBModel() = UserDB(
    fname = this.fname,
    lname = this.lname,
    username = this.username,
    email = this.email,
    password = this.password,
)