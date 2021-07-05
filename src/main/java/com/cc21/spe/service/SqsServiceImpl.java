package com.cc21.spe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.amazonaws.services.sqs.model.Message;
import com.cc21.spe.repo.SqsRepo;

@Service
public class SqsServiceImpl implements SqsService {

	@Autowired
	private SqsRepo sqsRepo;

	@Override
	public void deleteMessage(List<Message> message, String queueName) {
		sqsRepo.deleteMessage(message, queueName);
	}

	@Override
	public CreateQueueResult createQueue(String queueName) {
		// TODO Auto-generated method stub
		CreateQueueResult createQueueResult = sqsRepo.createQueue(queueName);
		return createQueueResult;
	}

	public List<Message> receiveMessage(String queueName, Integer waitTime, Integer visibilityTimeout) {
		return sqsRepo.receiveMessage(queueName, waitTime, visibilityTimeout);
	}

	@Override
	public void sendMessage(String messageBody, String queueName, Integer delaySeconds) {
		sqsRepo.sendMessage(messageBody, queueName, delaySeconds);
	}

	@Override
	public Integer getApproxNumOfMsgs(String queueName) {
		return sqsRepo.getApproximateNumberOfMsgs(queueName);
	}

}
