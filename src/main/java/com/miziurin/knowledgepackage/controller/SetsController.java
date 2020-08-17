package com.miziurin.knowledgepackage.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miziurin.knowledgepackage.error.EntityAlreadyExistException;
import com.miziurin.knowledgepackage.model.Kpac;
import com.miziurin.knowledgepackage.model.KpacSet;
import com.miziurin.knowledgepackage.service.IKpacService;
import com.miziurin.knowledgepackage.service.ISetService;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.beans.PropertyEditorSupport;
import java.util.List;

import static com.miziurin.knowledgepackage.constants.Constants.ModelAttributes.KPACS_ATTRIBUTE;
import static com.miziurin.knowledgepackage.constants.Constants.ModelAttributes.SETS_ATTRIBUTE;
import static com.miziurin.knowledgepackage.constants.Constants.ModelAttributes.SET_ATTRIBUTE;

@Controller
@RequestMapping(value = "/sets")
public class SetsController {

    @Autowired
    private ISetService setService;

    @Autowired
    private IKpacService kpacService;

    @Autowired
    private ObjectMapper mapper;

    @GetMapping
    public String getAllSets(Model model) throws JsonProcessingException {
        List<KpacSet> allSets = setService.findAll();
        List<Kpac> allKpacs = kpacService.findAll();

        model.addAttribute(SETS_ATTRIBUTE, mapper.writeValueAsString(allSets));
        model.addAttribute(KPACS_ATTRIBUTE, allKpacs);
        return "sets";
    }

    @PostMapping
    public String saveSet(@ModelAttribute(SET_ATTRIBUTE) KpacSet set) throws EntityAlreadyExistException {
        if (!StringUtils.isNullOrEmpty(set.getTitle())) {
            setService.save(set);
        }
        return "redirect:/sets";
    }

    @GetMapping("/delete/{id}")
    public String deleteKpac(@PathVariable("id") long id) {
        setService.delete(id);
        return "redirect:/sets";
    }

    @InitBinder(SET_ATTRIBUTE)
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Kpac.class, KPACS_ATTRIBUTE, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(kpacService.find(Long.parseLong(text)));
            }
        });
    }
}
