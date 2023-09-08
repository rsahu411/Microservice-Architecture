package com.lcwd.user.service.external.services;

import com.lcwd.user.service.entities.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name="RATING-SERVICE")
public interface RatingService {


    // Post
    @PostMapping("/ratings")
    public ResponseEntity<Rating> createRating(Rating rating);

    // Put
    @PutMapping("/ratings/{ratingId}")
    public ResponseEntity<Rating> updateRating(@PathVariable("ratingId") String ratingId,Rating rating);


    // Delete
    @DeleteMapping("/ratings/{ratingId}")
    public void deleteRating(@PathVariable("ratingId") String ratingId);

}
