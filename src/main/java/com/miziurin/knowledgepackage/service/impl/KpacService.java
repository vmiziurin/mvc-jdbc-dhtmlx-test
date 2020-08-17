package com.miziurin.knowledgepackage.service.impl;

import com.miziurin.knowledgepackage.error.EntityAlreadyExistException;
import com.miziurin.knowledgepackage.model.Kpac;
import com.miziurin.knowledgepackage.repository.IKpacRepository;
import com.miziurin.knowledgepackage.service.IKpacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class KpacService implements IKpacService {

    @Autowired
    private IKpacRepository kpacRepository;

    @Transactional
    @Override
    public void save(Kpac kpac) throws EntityAlreadyExistException {
        try {
            kpacRepository.save(kpac);
        } catch (DataIntegrityViolationException e) {
            throw new EntityAlreadyExistException("Knowledge package with specified title already exist.");
        }
    }

    @Override
    public Kpac find(long id) {
        return kpacRepository.find(id);
    }

    @Transactional
    @Override
    public void delete(long id) {
        kpacRepository.delete(id);
    }

    @Override
    public List<Kpac> findAll() {
        return kpacRepository.findAll();
    }
}
