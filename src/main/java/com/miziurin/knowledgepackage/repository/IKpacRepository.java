package com.miziurin.knowledgepackage.repository;

import com.miziurin.knowledgepackage.model.Kpac;

import java.util.List;

public interface IKpacRepository {

    void save(Kpac kpac);

    Kpac find(long id);

    void delete(long id);

    List<Kpac> findAll();
}
