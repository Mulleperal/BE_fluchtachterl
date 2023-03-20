package com.example.demo.service

import com.example.demo.model.*
import com.example.demo.repository.AuctionRepository
import com.example.demo.repository.OfferRepository
import com.example.demo.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class OfferService(
    private val repository: OfferRepository,
    private val userRepository: UserRepository,
    private val auctionRepository: AuctionRepository
    ) {

    fun getAll(): List<Offer> = repository.findAll().map { it }

    fun getById(id: Long) : Offer {
        return repository.findById(id).orElseThrow()
    }

    fun getByUserId(id: Long) : List<OfferOut> {
        var offerList = repository.findByUserId(id)!!
        var OfferOutList = offerList.map { it.convertToOutModel() }
        return OfferOutList
    }

    fun getByAuctionId(id: Long) : List<OfferOut> {
        var offerList = repository.findByAuctionId(id)!!
        var OfferOutList = offerList.map { it.convertToOutModel() }
        return OfferOutList
    }

    fun rejectOffer(id: Long) {
        return repository.rejectByOfferId(OfferStatus.REJECTED, id)
    }

    fun acceptOffer(id: Long) {
        return repository.acceptByOfferId(OfferStatus.ACCEPTED, id)
    }

    fun create(data: OfferIn): Offer {
        var auction : Auction = auctionRepository.findById(data.auction).orElseThrow()
        var user : UserDB = userRepository.findById(data.user).orElseThrow()
        var offer : Offer = data.convertToDBModel()
        offer.user = user
        offer.auction = auction
        return repository.save(offer)
    }
}