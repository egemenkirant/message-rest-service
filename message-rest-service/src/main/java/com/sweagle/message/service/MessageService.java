package com.sweagle.message.service;

import java.util.List;

import com.sweagle.message.exception.SystemException;
import com.sweagle.message.model.MessageEntity;

public interface MessageService {
	
	String sendMessage(MessageEntity message) throws SystemException;
    
    List<MessageEntity> findIncomingMessages(String receiver);
    
    List<MessageEntity> findSentMessages(String sender);

    MessageEntity readMessageDetails(String id);
    
    String estimateMessageCount(String period) throws SystemException;

}
