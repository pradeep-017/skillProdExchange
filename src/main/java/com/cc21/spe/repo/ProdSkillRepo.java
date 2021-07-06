package com.cc21.spe.repo;

import com.cc21.spe.entity.ProdSkill;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdSkillRepo extends CrudRepository<ProdSkill, Long> {
    
}