package com.example.webflux.controller;

import com.example.webflux.exception.HotelNotFoundException;
import com.example.webflux.model.Hotel;
import com.example.webflux.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private HotelRepository hotelRepository;

    /** Listing all the Hotels
     *
     * @return
     */
    @GetMapping()
    public Flux<Hotel> getAllHotels(){
        return hotelRepository.findAll();
    }

    /**
     * Creating hotel
     * @param hotel
     * @return
     */
    @PostMapping()
    public Mono<Hotel> createHotels(@Valid @RequestBody Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    /**
     * Listing hotel by id
     * @param hotelId
     * @return
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Hotel>> getHotelById(@PathVariable(value = "id") String hotelId) {
        return hotelRepository.findById(hotelId)
                .switchIfEmpty(Mono.error(new HotelNotFoundException("Hotel Id not found.")))
                .map(getHotel -> ResponseEntity.ok(getHotel));
    }

    /**
     * Updating hotel
     * @param hotelId
     * @param hotel
     * @return
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Hotel>> updateHotel(@PathVariable(value = "id") String hotelId,
                                                   @Valid @RequestBody Hotel hotel) {
        return hotelRepository.findById(hotelId)
                .flatMap(existingHotel -> {
                    existingHotel.setName(hotel.getName());
                    existingHotel.setAddress(hotel.getAddress());
                    existingHotel.setCreatedAt(hotel.getCreatedAt());
                    return hotelRepository.save(existingHotel);
                })
                .map(updatedHotel -> new ResponseEntity<>(updatedHotel, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Deleting hotel
     * @param hotelId
     * @return
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteHotel(@PathVariable(value = "id") String hotelId) {

        return hotelRepository.findById(hotelId)
                .flatMap(existingHotel ->
                        hotelRepository.delete(existingHotel)
                                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Hotels are Sent to the client as Server Sent Events
     * @return
     */
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Hotel> streamAllTweets() {
        return hotelRepository.findAll();
    }
}


