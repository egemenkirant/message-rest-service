package com.sweagle.message.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sweagle.message.exception.SystemException;
import com.sweagle.message.model.MessageEntity;
import com.sweagle.message.service.MessageService;

@RestController
@RequestMapping("/messages")
public class MessageController {

	@Autowired
    private MessageService messageService;
	
	@PostMapping(value = "/")
	@ExceptionHandler(value = SystemException.class)
    public String sendMessage(@RequestBody MessageEntity message){
		return messageService.sendMessage(message);
    }

	@GetMapping(value = "/byReceiver/{receiver}")
    public List<MessageEntity> findIncomingMessages(@PathVariable("receiver") String receiver) {
        return messageService.findIncomingMessages(receiver);
    }
	
	@GetMapping(value = "/bySender/{sender}")
    public List<MessageEntity> findSentMessages(@PathVariable("sender") String sender) {
        return messageService.findSentMessages(sender);
    }
	
	@GetMapping(value = "/byId/{id}")
	@ExceptionHandler(value = SystemException.class)
    public MessageEntity readMessageDetails(@PathVariable("id") String id) {
		return messageService.readMessageDetails(id);
    }
	
	@GetMapping(value = "/estimateMessageCount/{period}")
	@ExceptionHandler(value = SystemException.class)
    public String estimateMessageCount(@PathVariable("period") String period) {
		return messageService.estimateMessageCount(period);
    }
	
}
