package com.santhosh.springbootlibrary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.santhosh.springbootlibrary.requestmodels.ReviewRequest;
import com.santhosh.springbootlibrary.service.ReviewService;
import com.santhosh.springbootlibrary.utils.ExtractJWT;

@CrossOrigin("https://localhost:3000")
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

	@Autowired
	private ReviewService reviewService;
	
	@GetMapping("/secure/user/book")
	public Boolean reviewBookByUser(@RequestHeader("Authorization") String token, @RequestParam("bookId") Long bookId) throws Exception{
		String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
		
		if(userEmail == null) {
			throw new Exception("User email is missing");
		}
		return reviewService.userReviewListed(userEmail, bookId);
	}
	
	@PostMapping("/secure")
	public void PostReview(@RequestHeader(value = "Authorization") String token, @RequestBody ReviewRequest reviewRequest) throws Exception{
		String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
		if (userEmail == null) {
			throw new Exception("User Email is Missing");
		}
		reviewService.postReview(userEmail, reviewRequest);
	}
}
