package com.miziurin.knowledgepackage.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miziurin.knowledgepackage.error.EntityAlreadyExistException;
import com.miziurin.knowledgepackage.error.EntityNotFoundException;
import com.miziurin.knowledgepackage.model.Kpac;
import com.miziurin.knowledgepackage.model.KpacSet;
import com.miziurin.knowledgepackage.service.IKpacService;
import com.miziurin.knowledgepackage.service.ISetService;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.miziurin.knowledgepackage.constants.Constants.ModelAttributes.KPACS_ATTRIBUTE;
import static com.miziurin.knowledgepackage.constants.Constants.ModelAttributes.KPAC_ATTRIBUTE;
import static com.miziurin.knowledgepackage.constants.Constants.ModelAttributes.SET_ATTRIBUTE;
import static java.util.Collections.singletonList;

@Controller
@RequestMapping(value = "/set/{id}")
public class SetController {

    @Autowired
    private IKpacService kpacService;

    @Autowired
    private ISetService setService;

    @Autowired
    private ObjectMapper mapper;

    @GetMapping
    public String getKpacsOfSet(@PathVariable("id") long id, Model model)
            throws JsonProcessingException, EntityNotFoundException {
        KpacSet set = setService.find(id);
        List<Kpac> kpacsOfSet = set.getKpacs();
        model.addAttribute(SET_ATTRIBUTE, set);
        model.addAttribute(KPACS_ATTRIBUTE, mapper.writeValueAsString(kpacsOfSet));
        return "set";
    }

    @GetMapping("/kpacs/delete-kpac/{kpac-id}")
    public String deleteKpacFromSet(@PathVariable("id") long id, @PathVariable("kpac-id") long kpacId) {
        setService.deleteKpacFromSet(id, kpacId);
        return "redirect:/set/" + id;
    }

    @PostMapping
    public String addKpacToSet(@ModelAttribute(SET_ATTRIBUTE) KpacSet set, @ModelAttribute(KPAC_ATTRIBUTE) Kpac kpac)
            throws EntityAlreadyExistException {
        kpac.setSets(singletonList(set));
        if (!StringUtils.isEmptyOrWhitespaceOnly(kpac.getTitle())) {
            kpacService.save(kpac);
        }
        return "redirect:/set/" + set.getId();
    }
}
