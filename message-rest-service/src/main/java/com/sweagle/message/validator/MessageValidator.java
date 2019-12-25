package com.sweagle.message.validator;

import com.sweagle.message.constant.MessageConstants;
import com.sweagle.message.exception.SystemException;
import com.sweagle.message.model.MessageEntity;

public class MessageValidator {
	
	public static boolean validateSendMessage(MessageEntity request) throws SystemException {
		if(request!=null 
				&& request.getContent()!=null 
				&& request.getReceiver()!=null 
				&& request.getSender()!=null 
				&& request.getSentDate()!=null
				&& request.getSubject()!=null) {
			return true;
		}else {
			throw new SystemException(MessageConstants.FAULT_INVALID_REQUEST);
		}
	}
	
	public static boolean validatePeriodParameters(String request) throws SystemException {
		if(MessageConstants.PERIOD_DAILY.equals(request) 
				|| MessageConstants.PERIOD_WEEKLY.equals(request)) {
			return true;
		}else {
			throw new SystemException(MessageConstants.FAULT_INVALID_PERIOD);
		}
	}

}
