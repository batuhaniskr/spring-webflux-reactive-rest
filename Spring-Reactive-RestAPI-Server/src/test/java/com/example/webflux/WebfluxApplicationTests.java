package com.example.webflux;

import com.example.webflux.model.Hotel;
import com.example.webflux.repository.HotelRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebfluxApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Autowired
	HotelRepository hotelRepository;

	@Test
	public void contextLoads() {

		Hotel hotel = new Hotel("This is a Test Hotel");

		webTestClient.post().uri("/hotels")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(hotel), Hotel.class)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBody()
				.jsonPath("$.id").isNotEmpty()
				.jsonPath("$.name").isEqualTo("This is a Test Hotel");
	}


	@Test
	public void testGetAllHotels() {
		webTestClient.get().uri("/hotels")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBodyList(Hotel.class);
	}

	@Test
	public void testUpdateHotel() {
		Hotel hotel = hotelRepository.save(new Hotel("Initial Hotel")).block();

		Hotel newHotelData = new Hotel("Updated Hotel");

		webTestClient.put()
				.uri("/hotels/{id}", Collections.singletonMap("id", hotel.getId()))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(newHotelData), Hotel.class)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBody()
				.jsonPath("$.name").isEqualTo("Updated Hotel");
	}

	@Test
	public void testDeleteHotel() {
		Hotel hotel = hotelRepository.save(new Hotel("To be deleted")).block();

		webTestClient.delete()
				.uri("/hotels/{id}", Collections.singletonMap("id",  hotel.getId()))
				.exchange()
				.expectStatus().isOk();
	}
}
