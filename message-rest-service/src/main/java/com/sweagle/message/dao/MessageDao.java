package com.sweagle.message.dao;

import java.util.List;

import com.sweagle.message.model.MessageCountEntity;

public interface MessageDao{
	
	 List<MessageCountEntity> findMessageCountGroupByDatePeriod(String period);

}
