package com.lcwd.user.service;

import com.lcwd.user.service.entities.Rating;
import com.lcwd.user.service.external.services.RatingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceApplicationTests {

	@Autowired
	private RatingService ratingService;

	@Test
	void contextLoads() {
	}

//	@Test
//	void createRating(){
//
//		Rating rating = Rating
//				.builder()
//				.rating(5)
//				.userId("")
//				.hotelId("")
//				.feedback("this is created using feign client")
//				.build();
//		Rating rating1 = ratingService.createRating(rating);
//	}

}
