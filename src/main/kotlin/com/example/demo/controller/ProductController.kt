package com.example.demo.controller

import com.example.demo.model.*
import com.example.demo.service.ProductService
import jakarta.validation.Valid
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.core.io.ResourceLoader
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.io.File
import java.nio.file.Paths
import java.util.*

@RestController
@RequestMapping("api/v1/product")
@CrossOrigin
class ProductController(val service: ProductService, val resourceLoader: ResourceLoader) {

    @GetMapping("/getAll")
    fun getAllProducts() = service.getAll()

    @GetMapping("/get/{id}")
    fun getProductById(@PathVariable id: Long) = service.getById(id)

//    @PostMapping("/create")
//    @ResponseStatus(HttpStatus.CREATED)
//    fun saveProduct(
//        @Valid
//        @RequestBody data: Product
//    ): Product = service.create(data)

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    fun saveProduct(
        @Valid
        @ModelAttribute data: Product // use @ModelAttribute to bind form data to the Product object
    ): Product {

        val file = data.file // get the uploaded file from the Product object
        if (file != null && !file.isEmpty) {
            val fileName = "${UUID.randomUUID()}.${file.originalFilename!!.substringAfterLast(".")}"
            print(fileName)
            val path = Paths.get("").toAbsolutePath().toString()
            println("Working Directory = $path")
//            file.transferTo(File("$path/src/main/resources/static/$fileName")) // save the file to the pictures folder

            file.transferTo(File("C:/Users/mlind/WebstormProjects/FE_Fluchtachterl/src/assets/$fileName")) // save the file to the pictures folder


//            file.transferTo(File("C:\\Users\\mlind\\WebstormProjects\\FE_Fluchtachterl\\src\\assets\\$fileName")) // save the file to the pictures folder
//            file.transferTo(File("C:\\Users\\mlind\\WebstormProjects\\FE_Fluchtachterl\\src\\assets\\$fileName")) // save the file to the pictures folder
            data.img = fileName // set the img field of the Product object to the file name
        }

        return service.create(data) // save the Product object to the repository
    }

//    @GetMapping("/products/{productId}/image-url")
//fun getProductImageUrl(@PathVariable productId: Long): ResponseEntity<Resource> {
//    val product = service.getById(productId)
////    val imageUrl = "http://localhost:8080/pictures/${product.img}"
//    val resource: Resource = resourceLoader.getResource("classpath:static/${product.img}")
//        return ResponseEntity.ok()
//                .contentType(MediaType.IMAGE_JPEG)
//                .body(resource)
//}

//    @GetMapping("/products/{productId}/image-url")
//    fun getProductImageUrl(@PathVariable productId: Long): ResponseEntity<Resource> {
//        print("asdfasdfasdfasdf")
//        val product = service.getById(productId)
//        val imagePath = "classpath:static/${product.img}"
//        print(imagePath)
//        print("asdfasdfasdf")
//        val resource: Resource = resourceLoader.getResource(imagePath)
//        val contentType = if (product.img!!.endsWith(".jpg")) {
//            MediaType.IMAGE_JPEG
//        } else {
//            MediaType.IMAGE_PNG
//        }
//
//        print(imagePath)
//        return ResponseEntity.ok()
//            .contentType(contentType)
//            .body(resource)
//    }


    //    @GetMapping("/image/{imageName}")
    @GetMapping("/products/{productId}/image-url")

    @ResponseBody
    fun getImage(@PathVariable productId: Long): ResponseEntity<ByteArrayResource> {
        val product = service.getById(productId)
        val resource = resourceLoader.getResource("classpath:static/${product.img}")
        val inputStream = resource.inputStream
        val bytes = inputStream.readAllBytes()
        val byteArrayResource = ByteArrayResource(bytes)

        return ResponseEntity
            .ok()
            .contentLength(bytes.size.toLong())
            .contentType(MediaType.IMAGE_PNG)
            .body(byteArrayResource)
    }


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