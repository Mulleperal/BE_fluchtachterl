package com.example.demo.controller

import com.example.demo.model.*
import com.example.demo.service.OfferService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/offer")
@CrossOrigin
class OfferController(val service: OfferService)  {

    @GetMapping("getAll")
    fun getAllOffers() = service.getAll()

    @GetMapping("/get/user/{id}")
    fun getAuctionByUserId(@PathVariable id: Long) = service.getByUserId(id)

    @GetMapping("/get/auction/{id}")
    fun getAuctionByAuctionId(@PathVariable id: Long) = service.getByAuctionId(id)

    @GetMapping("/reject/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun rejectOffer(@PathVariable id: Long) = service.rejectOffer(id)

    @GetMapping("/accept/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun acceptOffer(@PathVariable id: Long) = service.acceptOffer(id)

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    fun saveOffer(
        @Valid
        @RequestBody data: OfferIn
    ): Offer = service.create(data)
}