package com.santhosh.springbootlibrary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.santhosh.springbootlibrary.entity.Message;
import com.santhosh.springbootlibrary.requestmodels.AdminQuestionRequest;
import com.santhosh.springbootlibrary.service.MessagesService;
import com.santhosh.springbootlibrary.utils.ExtractJWT;

@CrossOrigin("https://localhost:3000")
@RestController
@RequestMapping("/api/messages")
public class MessagesController {

	@Autowired
	private MessagesService messagesService;
	
    @PostMapping("/secure/add/message")
    public void postMessage(@RequestHeader(value="Authorization") String token,
                            @RequestBody Message messageRequest) {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        //System.out.println(userEmail);
        messagesService.postMessage(messageRequest, userEmail);
    }
    
    @PutMapping("/secure/admin/message")
    public void putMessage(@RequestHeader(value="Authorization") String token,
    		@RequestBody AdminQuestionRequest adminQuestionRequest) throws Exception {
    	String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
    	String admin = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
    	if(admin == null || !admin.equals("admin")) {
    		throw new Exception("Administartion page only.");
    	}
    	messagesService.putMessage(adminQuestionRequest, userEmail);
    }
}
