package com.cc21.spe.service;

import java.util.List;

import com.cc21.spe.entity.MessageUser;
import com.cc21.spe.entity.PSRequest;
import com.cc21.spe.repo.MessageUserRepo;
import com.cc21.spe.repo.PSRequestRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageUserService {

    @Autowired
    private MessageUserRepo msgRepo;

    public MessageUser createMessage(MessageUser messageUser) {
        return msgRepo.save(messageUser);
    }

    public MessageUser updatePSRequest(MessageUser messageUser) {
        return msgRepo.save(messageUser);
    }

    // public PSRequest getRequest(String userOne, String userTwo, String status) {
    //     return msgRepo.findByUserOneAndUserTwoAndStatus(userOne, userTwo, status);
    // }

public List<MessageUser> getMessages(String user) {
        return msgRepo.findByUserTwo(user);
    }
}