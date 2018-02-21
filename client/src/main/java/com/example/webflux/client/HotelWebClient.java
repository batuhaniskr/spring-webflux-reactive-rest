package com.example.webflux.client;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.example.webflux.model.Hotel;

@Service
public class HotelWebClient {

    private WebClient webClient;

    public HotelWebClient(WebClient.Builder webClient) {
        this.webClient = webClient.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .baseUrl("http://localhost:8080/hotels").build();
    }

    public Mono<Hotel> getById(String id) {
        return webClient.get().uri("/{id}", id)
                .retrieve().bodyToMono(Hotel.class);
    }

    public Flux<Hotel> getAll() {
        return webClient.get().retrieve().bodyToFlux(Hotel.class);
    }

    public Mono<Hotel> save(Hotel hotel) {
        return webClient.post()
                .body(BodyInserters.fromObject(hotel))
                .exchange().flatMap(clientResponse -> clientResponse.bodyToMono(Hotel.class));
    }

    public Mono<Hotel> update(String id, Hotel hotel) {

        Mono<Hotel> hotelMono = Mono.just(hotel);
        Mono<Hotel> hotelMonoUpdate = webClient.put().uri("/{id}",id).accept(MediaType.APPLICATION_JSON)
                .body(hotelMono, Hotel.class).retrieve().bodyToMono(Hotel.class);

        return hotelMonoUpdate;
    }

    public Mono<Void> delete(String id) {
        return webClient.delete()
                .uri("/{id}", id).retrieve().bodyToMono(Void.class);
    }
}
