package com.cc21.spe.repo;

import java.util.List;

import com.cc21.spe.entity.PSRequest;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PSRequestRepo extends CrudRepository<PSRequest, Long>{
    @Query(value = "SELECT d FROM psrequest d WHERE d.user_two = ?1 AND status=?2", nativeQuery = true)
    public List<PSRequest> findRequestsForUser(String userName, String status);

    public List<PSRequest> findByUserTwoAndStatus(String userTwo, String status);

    public PSRequest findByUserOneAndUserTwoAndStatus(String userOne, String userTwo, String status);
}