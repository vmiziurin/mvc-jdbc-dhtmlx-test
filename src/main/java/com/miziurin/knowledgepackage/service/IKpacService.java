package com.miziurin.knowledgepackage.service;

import com.miziurin.knowledgepackage.error.EntityAlreadyExistException;
import com.miziurin.knowledgepackage.model.Kpac;

import java.util.List;

public interface IKpacService {

    void save(Kpac kpac) throws EntityAlreadyExistException;

    Kpac find(long id);

    void delete(long id);

    List<Kpac> findAll();
}
