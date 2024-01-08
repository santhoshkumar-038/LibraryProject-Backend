package com.santhosh.springbootlibrary.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.santhosh.springbootlibrary.dao.MessageRepository;
import com.santhosh.springbootlibrary.entity.Message;
import com.santhosh.springbootlibrary.requestmodels.AdminQuestionRequest;

@Service
@Transactional
public class MessagesService {

	@Autowired
	private MessageRepository messageRepository;
	
	public void postMessage(Message messageRequest, String userEmail) {
		Message message = new Message(messageRequest.getTitle(), messageRequest.getQuestion());
		message.setUserEmail(userEmail);
		messageRepository.save(message);
	}
	
	public void putMessage(AdminQuestionRequest adminQuestionRequest, String userEmail) throws Exception {
		Optional<Message> message = messageRepository.findById(adminQuestionRequest.getId());
		if(!message.isPresent()) {
			throw new Exception("Message not found");
		}
		message.get().setAdminEmail(userEmail);
		message.get().setResponse(adminQuestionRequest.getResponse());
		message.get().setClosed(true);
		messageRepository.save(message.get());
	}
}
