package com.miziurin.knowledgepackage.service;

import com.miziurin.knowledgepackage.error.EntityAlreadyExistException;
import com.miziurin.knowledgepackage.error.EntityNotFoundException;
import com.miziurin.knowledgepackage.model.KpacSet;

import java.util.List;

public interface ISetService {

    void save(KpacSet set) throws EntityAlreadyExistException;

    KpacSet find(long id) throws EntityNotFoundException;

    void delete(long id);

    List<KpacSet> findAll();

    void deleteKpacFromSet(long setId, long kpacId);
}
