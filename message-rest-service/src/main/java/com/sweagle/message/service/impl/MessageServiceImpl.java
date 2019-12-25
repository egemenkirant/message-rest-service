package com.sweagle.message.service.impl;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sweagle.message.constant.MessageConstants;
import com.sweagle.message.dao.MessageDao;
import com.sweagle.message.exception.SystemException;
import com.sweagle.message.model.MessageCountEntity;
import com.sweagle.message.model.MessageEntity;
import com.sweagle.message.repository.MessageRepository;
import com.sweagle.message.service.MessageService;
import com.sweagle.message.validator.MessageValidator;

@Service
public class MessageServiceImpl implements MessageService{
	
	@Autowired
    private MessageRepository messageRepository;
	
	@Autowired
    private MessageDao messageDao;
	
	private static final Logger log = LoggerFactory.getLogger(MessageServiceImpl.class); 
	
	@Override
	public String sendMessage(MessageEntity message) throws SystemException {
		MessageValidator.validateSendMessage(message);
		messageRepository.insert(message);
		return MessageConstants.MSG_SUCCESS;
	}

	@Override
	public List<MessageEntity> findIncomingMessages(String receiver) {
		List<MessageEntity> resultList = messageRepository.findByReceiverOrderBySentDateDesc(receiver); 
		resultList.forEach(result -> {
			log.debug(result.toString());
		});
		return resultList;
	}

	@Override
	public List<MessageEntity> findSentMessages(String sender) {
		List<MessageEntity> resultList = messageRepository
				.findBySenderOrderBySentDateDesc(sender); 
		resultList.forEach(result -> {
			log.debug(result.toString());
		});
		return resultList;
	}

	@Override
	public MessageEntity readMessageDetails(String id) {
		Optional<MessageEntity> optMessage = messageRepository.findById(id);		
		if(optMessage.isPresent()) {
			log.debug("readMessageDetails result is: " + optMessage.get().toString());
			return optMessage.get();
		}else {
			return null;
		}
	}

	@Override
	public String estimateMessageCount(String period) throws SystemException {			
		MessageValidator.validatePeriodParameters(period);
		String returnMessage="";		
		List<MessageCountEntity> messageCntList = messageDao.findMessageCountGroupByDatePeriod(period);		
		messageCntList.forEach(messageCnt -> {
			log.debug(messageCnt.toString());
		});
		if(!messageCntList.isEmpty()) {
			List<MessageCountEntity> cloneList = messageCntList.stream().collect(Collectors.toList());
			int lastPeriodCount =0;			
			Calendar cal = Calendar.getInstance();
			//-1 because of the week calculation difference between mongodb aggregation and Calendar
			int week = cal.get(Calendar.WEEK_OF_YEAR)-1;
			int today = cal.get(Calendar.DAY_OF_MONTH);
			
			//control if first element is today or in this week
			if(cloneList.get(0).getDayOfMonth()==today || cloneList.get(0).getWeek()==week) {				
				lastPeriodCount=cloneList.get(0).getCount();
				cloneList.remove(0);
			}			
			OptionalDouble averageDbl = cloneList.stream().mapToDouble(MessageCountEntity::getCount).average();
			//decided to round the average to estimate integer data.
			long estimatedRestCount = Math.round(averageDbl.getAsDouble()) - lastPeriodCount;	
			//for not return negative estimated count.
			if(estimatedRestCount<0) {
				estimatedRestCount=0;
			}		
			log.debug("estimatedRestCount: " + estimatedRestCount);
			returnMessage = MessageConstants.MSG_ESTIMATEDCOUNT + period + " is: " + estimatedRestCount ;
		}
		return returnMessage;		
	}

}
