package com.example.demo.controller

import com.example.demo.model.*
import com.example.demo.service.ProductService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/product")
@CrossOrigin
class ProductController(val service: ProductService) {

    @GetMapping("/getAll")
    fun getAllProducts() = service.getAll()

    @GetMapping("/get/{id}")
    fun getProductById(@PathVariable id: Long) = service.getById(id)

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    fun saveProduct(
        @Valid
        @RequestBody data: Product
    ): Product = service.create(data)

    @PostMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    fun updateProduct(
        @Valid
        @RequestBody data: Product
    ): Product = service.updateProduct(data)

    @PostMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    fun deleteUser(
        @Valid
        @RequestBody data: Product
    ) = service.deleteProduct(data)
}