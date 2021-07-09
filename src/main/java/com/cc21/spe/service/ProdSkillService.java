package com.cc21.spe.service;

import com.cc21.spe.entity.ProdSkill;
import com.cc21.spe.repo.ProdSkillRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdSkillService {

    @Autowired
    private ProdSkillRepo prodSkillRepo;

    public ProdSkill addProdSkill(ProdSkill prodSkill) {
        return prodSkillRepo.save(prodSkill);
    }

}