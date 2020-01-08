package com.sweagle.message.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.sweagle.message.constant.MessageConstants;
import com.sweagle.message.dao.MessageDao;
import com.sweagle.message.exception.SystemException;
import com.sweagle.message.model.MessageCountEntity;
import com.sweagle.message.model.MessageEntity;
import com.sweagle.message.repository.MessageRepository;
import com.sweagle.message.service.impl.MessageServiceImpl;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MessageServiceTest {
	
	@TestConfiguration
    static class MessageServiceImplTestContextConfiguration {
        @Bean
        public MessageService messageService() {
            return new MessageServiceImpl();
        }
    }
	
	@Autowired
    private MessageService messageService;

    @MockBean
    private MessageRepository messageRepository;
    
    @MockBean
    private MessageDao messageDao;
    
    private MessageEntity paulMsg;
    private MessageEntity georgeMsg;
    private MessageEntity georgeExceptionMsg;
    private Optional<MessageEntity> paulMsgOpt = Optional.empty();
    private MessageCountEntity dailyMsgCount;
    private MessageCountEntity weeklyMsgCount;

    
    private String paulSender = "paul";
    private String georgeReceiver = "george";
    private String paulId = "4";
    private String weekPeriod = "week";
    private String dayPeriod = "day";
    private String wrongPeriod = "wrongPeriod";
    
    private String testEstimateMessageCountDaily = "Number of total messages that will be sent probably for the rest of the day";
    private String testEstimateMessageCountWeekly = "Number of total messages that will be sent probably for the rest of the week";
    
    private final List<MessageEntity> messages = new ArrayList<>();
    
     
    @BeforeEach
    public void setup() {
    	paulMsg = new MessageEntity();
    	paulMsg.setId("1");
    	paulMsg.setSender("paul");
    	paulMsg.setReceiver("adrian");
    	paulMsg.setSentDate(new Date());
    	paulMsg.setContent("content test1");
    	paulMsg.setSubject("subject test1");
    	
    	
    	georgeMsg = new MessageEntity();
    	georgeMsg.setId("2");
    	georgeMsg.setSender("paul");
    	georgeMsg.setReceiver("george");
    	georgeMsg.setSentDate(new Date());
    	georgeMsg.setContent("content test2");
    	georgeMsg.setSubject("subject test2");
    	
    	georgeExceptionMsg = new MessageEntity();
    	georgeExceptionMsg.setId("3");
    	georgeExceptionMsg.setSender("paul");
    	georgeExceptionMsg.setReceiver("george");
    	georgeExceptionMsg.setSentDate(null);
    	georgeExceptionMsg.setSubject("subject test3");
    	
    	MessageEntity paulMsg = new MessageEntity();
    	paulMsg.setId("4");
    	paulMsg.setSender("paul");
    	paulMsg.setReceiver("adrian");
    	paulMsg.setSentDate(new Date());
    	paulMsg.setContent("content test1");
    	paulMsg.setSubject("subject test1");
    	paulMsgOpt = Optional.of(paulMsg);
    	
    	messages.add(paulMsg);
    	messages.add(georgeMsg);
    	
    	dailyMsgCount = new MessageCountEntity();
    	dailyMsgCount.setCount(1);
    	dailyMsgCount.setDayOfYear(24);
    	
    	weeklyMsgCount = new MessageCountEntity();
    	weeklyMsgCount.setCount(5);
    	weeklyMsgCount.setWeek(25);
    	
    }
    
    @Test
    public void testFindBySender_thenPaulShouldBeReturned() {   
    	Mockito.when(messageRepository.
    			findBySenderOrderBySentDateDesc(paulSender)).thenReturn(Arrays.asList(paulMsg));
    	
        List<MessageEntity> actualList = messageService.findSentMessages(paulSender);
        
        assertNotNull(actualList);
        assertEquals(paulMsg.getSender(), actualList.get(0).getSender());        
    }
    
    @Test
    public void testFindByReceiver_thenGeorgeShouldBeReturned() {        	
    	Mockito.when(messageRepository.
    			findByReceiverOrderBySentDateDesc(georgeReceiver)).thenReturn(Arrays.asList(georgeMsg));
    	
        List<MessageEntity> actualList = messageService.findIncomingMessages(georgeReceiver);
        
        assertNotNull(actualList);
        assertEquals(georgeMsg.getReceiver(), actualList.get(0).getReceiver());        
    }
    
    @Test
    public void testSendMessage_thenMsgShouldBeReturned() throws SystemException {        	
    	Mockito.when(messageRepository.
    			insert(georgeMsg)).thenReturn(georgeMsg);
    	
    	String actual = messageService.sendMessage(georgeMsg);
        
    	assertEquals(MessageConstants.MSG_SUCCESS, actual);        
    }
    
    @Test
    public void testSendMessage_thenExceptionShouldBeReturned() throws SystemException {
    	Assertions.assertThrows(Exception.class, () -> {
    		messageService.sendMessage(georgeExceptionMsg);
    	  });
    }
    
    @Test
    public void testEstimateMessageCountDaily_thenMsgShouldBeReturned() throws SystemException { 
    	
    	Mockito.when(messageDao.findMessageCountGroupByDatePeriod(dayPeriod))
    				.thenReturn(Arrays.asList(dailyMsgCount));
   	
        String message = messageService.estimateMessageCount(dayPeriod);
        
        assertTrue(message.contains(testEstimateMessageCountDaily));        
    }
    
    @Test
    public void testEstimateMessageCountWeekly_thenMsgShouldBeReturned() throws SystemException {        	
    	
    	Mockito.when(messageDao.findMessageCountGroupByDatePeriod(weekPeriod))
    			.thenReturn(Arrays.asList(weeklyMsgCount));
    	    	
        String message = messageService.estimateMessageCount(weekPeriod);
        
        assertTrue(message.contains(testEstimateMessageCountWeekly));        
    }
    
    @Test
    public void testReadMessageDetails_thenPaulMsgShouldBeReturned() {        	
    	Mockito.when(messageRepository.
    			findById(paulId)).thenReturn(paulMsgOpt);
    	
        MessageEntity actual = messageService.readMessageDetails(paulId);
        
        assertEquals(paulMsgOpt.get().getReceiver() , actual.getReceiver());        
    }
    
    @Test
    public void testEstimateMessageCount_thenExceptionShouldBeReturned() throws SystemException {
    	Assertions.assertThrows(Exception.class, () -> {
    		messageService.estimateMessageCount(wrongPeriod);
    	  });
    }
    
   

}
