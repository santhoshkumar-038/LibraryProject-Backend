package com.santhosh.springbootlibrary.service;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.santhosh.springbootlibrary.dao.BookRepository;
import com.santhosh.springbootlibrary.dao.ReviewRepository;
import com.santhosh.springbootlibrary.entity.Review;
import com.santhosh.springbootlibrary.requestmodels.ReviewRequest;

@Service
public class ReviewService {

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	public void postReview(String userEmail, ReviewRequest reviewRequest) throws Exception{
		Review validateReview = reviewRepository.findByUserEmailAndBookId(userEmail, reviewRequest.getBookId());
		
		if(validateReview != null) {
			throw new Exception("Review Already Created");
		}
		
		Review review = new Review();
		review.setBookId(reviewRequest.getBookId());
		review.setUserEmail(userEmail);
		review.setRating(reviewRequest.getRating());
		if (reviewRequest.getReviewDescription().isPresent()){
			review.setReviewDescription(reviewRequest.getReviewDescription().map(
					Object::toString
					).orElse(null));
		}
		review.setDate(Date.valueOf(LocalDate.now()));
		reviewRepository.save(review);
	}
	
	public Boolean userReviewListed(String userEmail, Long bookId) {
		Review validateReview =reviewRepository.findByUserEmailAndBookId(userEmail, bookId);
		if(validateReview != null) {
			return true;
		}else {
			return false;
		}
	}
}
