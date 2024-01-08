package com.santhosh.springbootlibrary.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jayway.jsonpath.Option;
import com.santhosh.springbootlibrary.dao.BookRepository;
import com.santhosh.springbootlibrary.dao.CheckoutRepository;
import com.santhosh.springbootlibrary.dao.ReviewRepository;
import com.santhosh.springbootlibrary.entity.Book;
import com.santhosh.springbootlibrary.requestmodels.AddBookRequest;

@Service
@Transactional
public class AdminService {

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private CheckoutRepository checkoutRepository;
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	public void increaseBookQuantity(Long bookId) throws Exception {

        Optional<Book> book = bookRepository.findById(bookId);

        if (!book.isPresent()) {
            throw new Exception("Book not found");
        }

        book.get().setCopiesAvailable(book.get().getCopiesAvailable() + 1);
        book.get().setCopies(book.get().getCopies() + 1);

        bookRepository.save(book.get());
    }
	

    public void decreaseBookQuantity(Long bookId) throws Exception {

        Optional<Book> book = bookRepository.findById(bookId);

        if (!book.isPresent() || book.get().getCopiesAvailable() <= 0 || book.get().getCopies() <= 0) {
            throw new Exception("Book not found or quantity locked");
        }

        book.get().setCopiesAvailable(book.get().getCopiesAvailable() - 1);
        book.get().setCopies(book.get().getCopies() - 1);

        bookRepository.save(book.get());
    }
	
	public void postBook(AddBookRequest addBookRequest) {
		Book book = new Book();
		book.setTitle(addBookRequest.getTitle());
		book.setAuthor(addBookRequest.getAuthor());
		book.setCategory(addBookRequest.getCategory());
		book.setCopies(addBookRequest.getCopies());
		book.setCopiesAvailable(addBookRequest.getCopies());
		book.setDescription(addBookRequest.getDescription());
		book.setImg(addBookRequest.getImg());
		bookRepository.save(book);
	}
	
	public void deleteBook(Long bookId) throws Exception{
		Optional<Book> book = bookRepository.findById(bookId);
		if(!book.isPresent()) {
			throw new Exception("Book not present");
		}
		bookRepository.delete(book.get());
		checkoutRepository.deleteAllByBookId(bookId);
		reviewRepository.deleteAllByBookId(bookId);
	}
}
