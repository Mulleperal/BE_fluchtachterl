package com.example.demo.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "offer")
data class Offer(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    val price: Float,
    val quantity: Long,
    val deliveryDate: LocalDate,
    val status: Enum<OfferStatus> = OfferStatus.PENDING,
    val created: LocalDateTime? = LocalDateTime.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_id",  referencedColumnName = "id")
    var auction: Auction? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",  referencedColumnName = "id")
    var user: UserDB? = null
)


enum class OfferStatus(val offerStatus: String) {
    PENDING("pending"),
    REJECTED("rejected"),
    ACCEPTED("accepted")

}

open class OfferIn(
    val price: Float,
    val quantity: Long,
    val deliveryDate: LocalDate,
    var user: Long,
    var auction: Long
)

fun OfferIn.convertToDBModel() = Offer(
    price = this.price,
    quantity = this.quantity,
    deliveryDate = this.deliveryDate
)

open class OfferOut(
    val id: Long,
    val price: Float,
    val quantity: Long,
    val deliveryDate: LocalDate,
    val status: Enum<OfferStatus>,
    val created: LocalDateTime,
    var auction: AuctionOutViaOffer,
    var user: UserBase
)

fun Offer.convertToOutModel() = OfferOut(
    id = this.id!!,
    price = this.price,
    quantity = this.quantity,
    deliveryDate = this.deliveryDate,
    status = this.status,
    created = this.created!!,
    auction= this.auction!!.convertToOutViewOfferModel(),
    user = this.user!!.convertToOutModel()
)