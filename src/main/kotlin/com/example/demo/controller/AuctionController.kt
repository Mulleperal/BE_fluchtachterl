package com.example.demo.controller

import com.example.demo.model.AuctionIn
import com.example.demo.model.AuctionOut
import com.example.demo.service.AuctionService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/auction")
@CrossOrigin
class AuctionController(val service: AuctionService) {

    @GetMapping("getAll")
    fun getAllAuctions() = service.getAll()

    @GetMapping("/get/{id}")
    fun getAuctionById(@PathVariable id: Long) = service.getById(id)

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    fun saveAuction(
        @Valid
        @RequestBody data: AuctionIn
    ): AuctionOut = service.create(data)
}