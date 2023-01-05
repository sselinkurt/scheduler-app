package com.example.schedulerapp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TalksCreationDto {
    private List<@Valid Talk> talks = new ArrayList<>();

    public void addTalk(Talk talk) {
        this.talks.add(talk);
    }
}
