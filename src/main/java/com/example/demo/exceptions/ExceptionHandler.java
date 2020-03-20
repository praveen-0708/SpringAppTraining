package com.example.demo.exceptions;

import com.example.demo.model.ExceptionModel;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class ExceptionHandler {

    private ExceptionModel exceptionModel;

    public ExceptionHandler() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        exceptionModel = (ExceptionModel) context.getBean("ExceptionModel");
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionModel> handleUserNotFoundException(UserNotFoundException exception) {
        exceptionModel.setMessage(exception.getMessage());
        exceptionModel.setStatus("404");
        return new ResponseEntity<ExceptionModel>(exceptionModel, HttpStatus.NOT_FOUND);
    }

}
