package com.miziurin.knowledgepackage.repository;

import com.miziurin.knowledgepackage.model.KpacSet;

import java.util.List;

public interface ISetRepository {

    void save(KpacSet set);

    KpacSet find(long id);

    void delete(long id);

    List<KpacSet> findAll();

    void deleteKpacFromSet(long setId, long kpacId);
}
