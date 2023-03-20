package com.example.demo.service

import com.example.demo.model.*
import com.example.demo.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(private val repository: ProductRepository) {

    fun getAll(): List<Product> = repository.findAll().map { it }

    fun getById(id: Long) : Product {
        return repository.findById(id).orElseThrow()
    }

    fun create(data: Product): Product = repository.save(data)

    fun updateProduct(product: Product): Product {
        return repository.save(product)
    }

    fun deleteProduct(product: Product) {
        val product = repository.findByName(product.name)
        if (product == null) {
            return
        } else {
            return repository.deleteById(product.id!!)
        }
    }

}