package com.miziurin.knowledgepackage.model;

import java.util.List;

public class KpacSet {

    private long id;

    private String title;

    private List<Kpac> kpacs;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Kpac> getKpacs() {
        return kpacs;
    }

    public void setKpacs(List<Kpac> kpacs) {
        this.kpacs = kpacs;
    }
}
