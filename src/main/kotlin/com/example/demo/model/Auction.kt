package com.example.demo.model

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "auction")
data class Auction(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    var minPrice: Float,
    var maxPrice: Float,
    var minQuantity: Int,
    var maxQuantity: Int,
    var minDeliveryDate: LocalDate,
    var maxDeliveryDate: LocalDate,
    var startDate: LocalDateTime,
    var endDate: LocalDateTime,

    @JoinColumn(name = "product_id")
    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var product: Product? = null,

    @JoinColumn(name = "offer_id")
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var offers: List<Offer>? = null,

    @ManyToOne
    @JoinColumn(name = "user_id",  referencedColumnName = "id")
    var user: UserDB? = null

)

// Req/Resp Marshalling
open class AuctionIn(
    var minPrice: Float,
    var maxPrice: Float,
    var minQuantity: Int,
    var maxQuantity: Int,
    var minDeliveryDate: LocalDate,
    var maxDeliveryDate: LocalDate,
    var startDate: LocalDateTime,
    var endDate: LocalDateTime,
    var product: Long,
    var user: Long
)

fun AuctionIn.convertToDBModel() = Auction(
    minPrice = this.minPrice,
    maxPrice = this.maxPrice,
    minQuantity = this.minQuantity,
    maxQuantity = this.maxQuantity,
    minDeliveryDate = this.minDeliveryDate,
    maxDeliveryDate = this.maxDeliveryDate,
    startDate = this.startDate,
    endDate = this.endDate
)

open class AuctionOutViaOffer(
    var id: Long,
    var endDate: LocalDateTime,
    var product: Product,
    var user: UserBase,
)

fun Auction.convertToOutViewOfferModel() = AuctionOutViaOffer(
    id = this.id!!,
    endDate = this.endDate,
    product = this.product!!,
    user = this.user!!.convertToOutModel(),
)

open class AuctionOut(
    var id: Long,
    var minPrice: Float,
    var maxPrice: Float,
    var minQuantity: Int,
    var maxQuantity: Int,
    var minDeliveryDate: LocalDate,
    var maxDeliveryDate: LocalDate,
    var startDate: LocalDateTime,
    var endDate: LocalDateTime,
    var product: Product,
    var user: UserBase,
    var offers: List<Offer>?
)

fun Auction.convertToOutModel() = AuctionOut(
    id = this.id!!,
    minPrice = this.minPrice,
    maxPrice = this.maxPrice,
    minQuantity = this.minQuantity,
    maxQuantity = this.maxQuantity,
    minDeliveryDate = this.minDeliveryDate,
    maxDeliveryDate = this.maxDeliveryDate,
    startDate = this.startDate,
    endDate = this.endDate,
    product = this.product!!,
    user = this.user!!.convertToOutModel(),
    offers = this.offers

)