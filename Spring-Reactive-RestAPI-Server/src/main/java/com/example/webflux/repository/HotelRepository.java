package com.example.webflux.repository;

import com.example.webflux.model.Hotel;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends ReactiveMongoRepository<Hotel, String> {
}
