package com.sweagle.message.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sweagle.message.model.MessageEntity;

public interface MessageRepository extends MongoRepository<MessageEntity, String> {
	    
	    List<MessageEntity> findByReceiverOrderBySentDateDesc(String receiver);
	    
	    List<MessageEntity> findBySenderOrderBySentDateDesc(String sender);
	    	    
}


