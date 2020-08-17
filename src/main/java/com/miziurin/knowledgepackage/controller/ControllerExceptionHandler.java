package com.miziurin.knowledgepackage.controller;

import com.miziurin.knowledgepackage.error.EntityAlreadyExistException;
import com.miziurin.knowledgepackage.error.EntityNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {EntityAlreadyExistException.class, EntityNotFoundException.class})
    public ModelAndView handleSQLException(Exception ex) {
        ModelAndView error = new ModelAndView("error");
        error.addObject("exception", ex.getMessage());
        return error;
    }
}
