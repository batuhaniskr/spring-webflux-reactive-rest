package com.example.webflux;

import com.example.webflux.client.HotelWebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.webflux.model.Hotel;

@SpringBootApplication
public class ClientApplication implements CommandLineRunner {

	@Autowired
	private HotelWebClient webClient;

	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		System.out.println("### Get All Hotels");
		webClient.getAll().subscribe(System.out::println);

		System.out.println("### Get Specific Hotel Id");
		webClient.getById("5a869d51b7772f10a7086e17").subscribe(System.out::println);

		System.out.println("##### Save a Hotel");
		webClient.save(new Hotel("TestHotel")).subscribe();

		System.out.println("### Update a Hotel");
		Hotel hotelUpdate = new Hotel();
		hotelUpdate.setId("5a869d51b7772f10a7086e17");
		hotelUpdate.setName("TestClientHotel");
		hotelUpdate.setAddress("Ankara/Türkiye");
		webClient.update("5a869d51b7772f10a7086e17", hotelUpdate).subscribe();

		System.out.println("### Delete a Hotel");
		webClient.delete("5a8a8d61ec598e0cf915c7ec");
	}
}
