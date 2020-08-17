package com.miziurin.knowledgepackage.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miziurin.knowledgepackage.error.EntityAlreadyExistException;
import com.miziurin.knowledgepackage.model.Kpac;
import com.miziurin.knowledgepackage.service.IKpacService;
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

@Controller
@RequestMapping(value = "/kpacs")
public class KpacsController {

    @Autowired
    private IKpacService kpacService;

    @Autowired
    private ObjectMapper mapper;

    @GetMapping
    public String getAllKpacs(Model model) throws JsonProcessingException {
        List<Kpac> allKpacs = kpacService.findAll();
        model.addAttribute(KPACS_ATTRIBUTE, mapper.writeValueAsString(allKpacs));
        return "kpacs";
    }

    @PostMapping
    public String saveKpac(@ModelAttribute(KPAC_ATTRIBUTE) Kpac kpac) throws EntityAlreadyExistException {
        if (!StringUtils.isEmptyOrWhitespaceOnly(kpac.getTitle())) {
            kpacService.save(kpac);
        }
        return "redirect:/kpacs";
    }

    @GetMapping("/delete/{id}")
    public String deleteKpac(@PathVariable("id") long id) {
        kpacService.delete(id);
        return "redirect:/kpacs";
    }
}
