package com.example.schedulerapp.controller;

import com.example.schedulerapp.model.Talk;
import com.example.schedulerapp.service.ConferenceSchedulerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
public class ConferenceSchedulerController {

    private final ConferenceSchedulerService service;

    public ConferenceSchedulerController(ConferenceSchedulerService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity addConferences(@RequestBody List<@Valid Talk> talks) {
        try {
            return new ResponseEntity(service.schedule(talks), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Request failed!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
