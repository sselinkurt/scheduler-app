package com.example.schedulerapp.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class Talk {
    @NotBlank(message = "title is required")
    private String title;
    @NotNull(message = "duration is required")
    private int duration; // minute

}
