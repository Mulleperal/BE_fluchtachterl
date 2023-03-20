package com.example.demo.service
import com.example.demo.model.*
import com.example.demo.repository.AuctionRepository
import com.example.demo.repository.ProductRepository
import com.example.demo.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class AuctionService(
    private val repository: AuctionRepository,
    private val productRepository: ProductRepository,
    private val userRepository: UserRepository
    ) {

    fun getAll(): List<AuctionOut> = repository.findAll().map { it.convertToOutModel() }

    fun getById(id: Long) : AuctionOut {
        return repository.findById(id).orElseThrow().convertToOutModel()
    }

    fun create(data: AuctionIn): AuctionOut {
        var product : Product = productRepository.findById(data.product).orElseThrow()
        var user : UserDB = userRepository.findById(data.user).orElseThrow()
        var auction : Auction = data.convertToDBModel()
        auction.user = user
        auction.product = product
        return repository.save(auction).convertToOutModel()
    }

}