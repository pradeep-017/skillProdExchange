package com.cc21.spe.repo;

import java.util.List;

import com.cc21.spe.entity.MessageUser;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageUserRepo extends CrudRepository<MessageUser, Long>{

    public List<MessageUser> findByUserTwo(String userTwo);

}