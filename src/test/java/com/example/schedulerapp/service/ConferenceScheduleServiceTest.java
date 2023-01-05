package com.example.schedulerapp.service;

import com.example.schedulerapp.model.ConferenceResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ConferenceScheduleServiceTest {
    @InjectMocks
    ConferenceSchedulerService service;

    private List<ConferenceResponse> response = new ArrayList<>();

    @Test
    public void when_noTimeForNetworkingEvent_ThenDontSchedule() {
        response.add(ConferenceResponse.builder().title("Last Talk").startTime("16:00").endTime("17:00").build());
        Assertions.assertFalse(service.scheduleNetworkingTalks(response));
    }

    @Test
    public void when_haveTimeForNetworkingEvent_ThenSchedule() {
        response.add(ConferenceResponse.builder().title("Last Talk").startTime("16:00").endTime("16:30").build());
        Assertions.assertTrue(service.scheduleNetworkingTalks(response));
    }
}
