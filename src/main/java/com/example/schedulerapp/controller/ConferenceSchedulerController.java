package com.example.schedulerapp.controller;

import com.example.schedulerapp.model.ConferenceResponse;
import com.example.schedulerapp.model.Talk;
import com.example.schedulerapp.model.TalksCreationDto;
import com.example.schedulerapp.service.ConferenceSchedulerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@Validated
public class ConferenceSchedulerController {

    private static final int NUM_OF_TALKS = 10;
    private final ConferenceSchedulerService service;

    public ConferenceSchedulerController(ConferenceSchedulerService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public String addConferences(@ModelAttribute @Valid TalksCreationDto talk, Model model) {
        List<ConferenceResponse> talks = service.schedule(talk.getTalks());
        model.addAttribute("talks", talks);
        return "conference";
    }

    @GetMapping
    public String index(Model model) {
        TalksCreationDto talks = new TalksCreationDto();
        for (int i = 1; i <= NUM_OF_TALKS; i++) {
            talks.addTalk(new Talk());
        }
        model.addAttribute("talks", talks);
        return "index";
    }
}
