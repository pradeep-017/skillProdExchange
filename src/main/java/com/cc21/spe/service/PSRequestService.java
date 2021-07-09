package com.cc21.spe.service;

import java.util.List;

import com.cc21.spe.entity.PSRequest;
import com.cc21.spe.repo.PSRequestRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PSRequestService {

    @Autowired
    private PSRequestRepo psRequestRepo;

    public PSRequest createPSRequest(PSRequest psRequest) {
        return psRequestRepo.save(psRequest);
    }

    public PSRequest updatePSRequest(PSRequest psRequest) {
        return psRequestRepo.save(psRequest);
    }

    public PSRequest getRequest(String userOne, String userTwo, String status) {
        return psRequestRepo.findByUserOneAndUserTwoAndStatus(userOne, userTwo, status);
    }

    public List<PSRequest> getRequests(String user, String status) {
        return psRequestRepo.findByUserTwoAndStatus(user, status);
    }
}