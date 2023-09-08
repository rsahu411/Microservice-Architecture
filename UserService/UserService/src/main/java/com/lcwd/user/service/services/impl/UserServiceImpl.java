package com.lcwd.user.service.services.impl;

import com.lcwd.user.service.entities.Hotel;
import com.lcwd.user.service.entities.Rating;
import com.lcwd.user.service.entities.User;
import com.lcwd.user.service.exceptions.ResourceNotFoundException;
import com.lcwd.user.service.external.services.HotelService;
import com.lcwd.user.service.repositories.UserRepository;
import com.lcwd.user.service.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;



    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    //create user
    @Override
    public User saveUser(User user) {
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    // Get all user
    @Override
    public List<User> getAllUser() {
        List<User> userId = userRepository.findAll();
        User user = null;
        for (int i = 0; i < userRepository.count(); i++) {
            user = userRepository.findById(userId.get(i).getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User with given id is not found on server !! :" + userId));
            ArrayList<Rating> ratingOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/" + userId.get(i).getUserId(), ArrayList.class);
            logger.info("{}",ratingOfUser);

            userId.get(i).setRatings(ratingOfUser);
        }
        return userRepository.findAll();
    }


    // Get a single user
    @Override
    public User getUser(String userId) {
        // get user from database with the help of user repository
        User user = userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User with given id is not found on server !! :"+userId));

        // Fetch rating of the above user from RATING SERVICE
        // http://localhost:8088/ratings/users/c5432396-6320-40a1-bb28-867c233330dc

        Rating[] ratingOfUser =restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getUserId(), Rating[].class);
        logger.info("{}",ratingOfUser);
        List<Rating> ratings = Arrays.stream(ratingOfUser).toList();


        List<Rating> ratingList = ratings.stream().map(rating -> {

            //api call to Hotel Service to get hotel
            // http://localhost:8087/hotels/8b801e60-edc6-4780-b806-55c51610aa75

//            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);
//            Hotel hotel = forEntity.getBody();

            // feign client
            Hotel hotel = hotelService.getHotel(rating.getHotelId());

//            logger.info("response status code: {} ",forEntity.getStatusCode());

            //set the hotel to rating
            rating.setHotel(hotel);

            //return the rating
            return  rating;
        }).collect(Collectors.toList());
        user.setRatings(ratingList);
        return user;
    }
}