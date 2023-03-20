package com.example.demo.repository

import com.example.demo.model.Offer
import com.example.demo.model.OfferStatus
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface OfferRepository: JpaRepository<Offer, Long> {

    @Query(value = "SELECT * FROM offer o WHERE o.auction_id=?1", nativeQuery = true)
    fun findByAuctionId(auctionId: Long) : List<Offer>?

    @Query(value = "SELECT * FROM offer o WHERE o.user_id=?1", nativeQuery = true)
    fun findByUserId(auctionId: Long) : List<Offer>?

    @Modifying
    @Transactional
    @Query("update Offer o set o.status = ?1 where o.id = ?2")
    fun acceptByOfferId(status: OfferStatus, offerId: Long)

    @Modifying
    @Transactional
    @Query("update Offer o set o.status = ?1 where o.id = ?2")
    fun rejectByOfferId(status: OfferStatus, offerId: Long)
}