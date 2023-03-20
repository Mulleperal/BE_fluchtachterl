package com.example.demo.model

import jakarta.persistence.*

@Entity
@Table(name = "product")
data class Product(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
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