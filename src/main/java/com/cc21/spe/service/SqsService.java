package com.cc21.spe.service;

import java.util.List;

import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.amazonaws.services.sqs.model.Message;

public interface SqsService {
	
	public CreateQueueResult createQueue(String queueName);

	public List<Message> receiveMessage(String queueName, Integer waitTime, Integer visibilityTimeout);

	public void sendMessage(String messageBody, String queueName, Integer delaySeconds);
	
	public Integer getApproxNumOfMsgs(String queueName);

	void deleteMessage(List<Message> message, String queueName);

}
