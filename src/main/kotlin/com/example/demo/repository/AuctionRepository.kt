package com.example.demo.repository

import com.example.demo.model.Auction
import com.example.demo.model.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuctionRepository: JpaRepository<Auction, Long> {
}