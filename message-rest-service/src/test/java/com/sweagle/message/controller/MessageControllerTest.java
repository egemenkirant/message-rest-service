package com.sweagle.message.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sweagle.message.constant.MessageConstants;
import com.sweagle.message.model.MessageEntity;
import com.sweagle.message.service.MessageService;


@ExtendWith(SpringExtension.class)
@WebMvcTest
public class MessageControllerTest {
	
	@Autowired
    private MockMvc mvc;
	
	@MockBean
    private MessageService messageService;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	private MessageEntity paulMsg;
    private MessageEntity georgeMsg;
    private MessageEntity msgDetail;
    
    private String paulSender = "paul";
    private String georgeReceiver = "george";
    private String adrianId = "3";    
    
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
    	
    	msgDetail = new MessageEntity();
    	msgDetail.setId("3");
    	msgDetail.setSender("adrian");
    	msgDetail.setReceiver("valerie");
    	msgDetail.setSentDate(new Date());
    	msgDetail.setContent("content test3");
    	msgDetail.setSubject("subject test3");
    }
    
    @Test
    public void testFindbySender_thenReturnJsonArray() throws Exception {
    	
        when(messageService.findSentMessages(paulSender)).thenReturn(Arrays.asList(paulMsg));

        mvc.perform(get("/messages/bySender/{sender}", paulSender)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].sender", is(paulMsg.getSender())));
    }
    
    @Test
    public void testFindbyReceiver_thenReturnJsonArray() throws Exception {
    	
        when(messageService.findIncomingMessages(georgeReceiver)).thenReturn(Arrays.asList(georgeMsg));

        mvc.perform(get("/messages/byReceiver/{receiver}", georgeReceiver)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].receiver", is(georgeMsg.getReceiver())));
    }
    
    @Test
    public void testReadMessageDetails_thenReturnJson() throws Exception {
    	
        when(messageService.readMessageDetails(adrianId)).thenReturn(msgDetail);

        mvc.perform(get("/messages/byId/{id}", adrianId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(msgDetail.getId())));
    }
    
    @Test
    public void testSendMessage_thenReturnStatusOk() throws Exception {
    	
    	when(messageService.sendMessage(georgeMsg)).thenReturn(MessageConstants.MSG_SUCCESS);

        String jsonString = objectMapper.writeValueAsString(georgeMsg);

        mvc.perform(post("/messages/")
                .contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andExpect(status().isOk());
    }
	
	

}
