package com.example.demo.model

import jakarta.persistence.*
import org.springframework.web.multipart.MultipartFile

@Entity
@Table(name = "product")
data class Product(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    @Transient
    var file: MultipartFile? = null, // new field for file upload
    @Column(unique = true)
    var name: String,
    var img: String? = null,
    var description: String,
    var category: String
)

data class ProductSearch(
    var name: String
)

//data class ProductOut(
//    var name: String,
//    var img: String,
//    var description: String,
//    var category: String
//)
//
//fun Product.convertToOutModel() = ProductOut(
//    name = this.name,
//    img = this.img,
//    description = this.description,
//    category = this.category
//)