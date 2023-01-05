package com.example.schedulerapp.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ConferenceResponse {
    private String title;
    private String startTime;
    private String endTime;
}
