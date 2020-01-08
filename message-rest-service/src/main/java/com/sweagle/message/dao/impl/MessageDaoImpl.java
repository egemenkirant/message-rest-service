package com.sweagle.message.dao.impl;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Repository;

import com.sweagle.message.constant.MessageConstants;
import com.sweagle.message.dao.MessageDao;
import com.sweagle.message.model.MessageCountEntity;
import com.sweagle.message.model.MessageEntity;

@Repository
public class MessageDaoImpl implements MessageDao{

	@Autowired
	MongoTemplate mongoTemplate;
	
	@Override
	public List<MessageCountEntity> findMessageCountGroupByDatePeriod(String period) {
		String expPeriod=period;
		if(MessageConstants.PERIOD_DAILY.equals(period)) {
			expPeriod = MessageConstants.EXP_DAY_OF_YEAR;
		}
		Aggregation agg = newAggregation(
			    project("sentDate")
			    	.andExpression("year(sentDate)").as("year")
			    	.andExpression(expPeriod+"(sentDate)").as(expPeriod),
			    group("year",expPeriod).count().as("count"),
			    sort(Sort.Direction.DESC, "_id")
			);
		//Convert the aggregation result into a List
		AggregationResults<MessageCountEntity> groupResults 
			= mongoTemplate.aggregate(agg, MessageEntity.class, MessageCountEntity.class);
		List<MessageCountEntity> messageCntList = groupResults.getMappedResults();
		return messageCntList;
	}

}
