package com.example.webflux;

import com.example.webflux.client.HotelWebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.webflux.model.Hotel;

import java.text.SimpleDateFormat;
import java.util.Date;

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
		webClient.getAll().subscribe();

		System.out.println("##### Save a Hotel");

		Hotel testHotel = new Hotel();
		testHotel.setName("TestHotel");
		testHotel.setAddress("TestAddress");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-dd-MM");
		Date testDate = simpleDateFormat.parse("2017-10-10");
		testHotel.setCreatedAt(testDate);

		testHotel.setId(webClient.save(testHotel).block().getId());

		System.out.println("### Get Specific Hotel Id");
		webClient.getById(testHotel.getId()).subscribe();


		System.out.println("### Update a Hotel");
		Hotel hotelUpdate = new Hotel();
		hotelUpdate.setId(testHotel.getId());
		hotelUpdate.setName("AnkaraHotel");
		hotelUpdate.setAddress("Ankara/Turkey");
		webClient.update(testHotel.getId(), hotelUpdate).subscribe();

		System.out.println("### Delete a Hotel");
		webClient.delete(testHotel.getId()).subscribe();
	}
}
