package com.miziurin.knowledgepackage.service.impl;

import com.miziurin.knowledgepackage.error.EntityAlreadyExistException;
import com.miziurin.knowledgepackage.error.EntityNotFoundException;
import com.miziurin.knowledgepackage.model.KpacSet;
import com.miziurin.knowledgepackage.repository.ISetRepository;
import com.miziurin.knowledgepackage.service.ISetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SetService implements ISetService {

    @Autowired
    private ISetRepository setRepository;

    @Transactional
    @Override
    public void save(KpacSet set) throws EntityAlreadyExistException {
        try {
            setRepository.save(set);
        } catch (DataIntegrityViolationException e) {
            throw new EntityAlreadyExistException("Set with specified title already exist.");
        }
    }

    @Override
    public KpacSet find(long id) throws EntityNotFoundException {
        try {
            return setRepository.find(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Set with specified id does not exist.");
        }
    }

    @Transactional
    @Override
    public void delete(long id) {
        setRepository.delete(id);
    }

    @Override
    public List<KpacSet> findAll() {
        return setRepository.findAll();
    }

    @Override
    public void deleteKpacFromSet(long setId, long kpacId) {
        setRepository.deleteKpacFromSet(setId, kpacId);
    }
}
