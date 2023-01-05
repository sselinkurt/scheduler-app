package com.example.schedulerapp.service;

import com.example.schedulerapp.model.ConferenceResponse;
import com.example.schedulerapp.model.Talk;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ConferenceSchedulerService {

    public static DateTime dateTime = new DateTime();

    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormat.forPattern("HH:mm");

    private static final DateTime MORNING_START_TIME = dateTime.withHourOfDay(9).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
    private static final DateTime MORNING_END_TIME = dateTime.withHourOfDay(12).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
    private static final DateTime LUNCH_START_TIME = dateTime.withHourOfDay(12).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
    private static final DateTime LUNCH_END_TIME = dateTime.withHourOfDay(13).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
    private static final DateTime AFTERNOON_START_TIME = dateTime.withHourOfDay(13).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
    private static final DateTime AFTERNOON_END_TIME = dateTime.withHourOfDay(17).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
    private static final DateTime NETWORKING_START_TIME = dateTime.withHourOfDay(16).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
    private static final DateTime NETWORKING_END_TIME = dateTime.withHourOfDay(17).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);


    public List<ConferenceResponse> schedule(List<Talk> talks)  {
        List<ConferenceResponse> responses = new ArrayList<>();

        talks.sort(Comparator.comparingInt(Talk::getDuration).reversed());
        while (talks.size() > 0) {
            scheduleTalks(MORNING_START_TIME, MORNING_END_TIME, talks, responses);
            responses.add(ConferenceResponse.builder().title("Lunch").startTime(DATE_TIME_FORMAT.print(LUNCH_START_TIME)).endTime(DATE_TIME_FORMAT.print(LUNCH_END_TIME)).build());
            scheduleTalks(AFTERNOON_START_TIME, AFTERNOON_END_TIME, talks, responses);
            scheduleNetworkingTalks(responses);
        }
        return responses;
    }


    private void scheduleTalks(DateTime start, DateTime end, List<Talk> talks, List<ConferenceResponse> responses) {
        List<Talk> scheduledTalks = new ArrayList<>();
        for (Talk talk: talks) {
            if (start.plusMinutes(talk.getDuration()).isBefore(end) || start.plusMinutes(talk.getDuration()).isEqual(end)) {
                responses.add(ConferenceResponse.builder().
                        title(talk.getTitle()).startTime(DATE_TIME_FORMAT.print(start)).endTime(DATE_TIME_FORMAT.print(start.plusMinutes(talk.getDuration()))).build());
                start = start.plusMinutes(talk.getDuration());
                scheduledTalks.add(talk);
            }
        }
        talks.removeAll(scheduledTalks);
    }

    private void scheduleNetworkingTalks(List<ConferenceResponse> responses) {
        String lastAfternoonTalkFinish = responses.get(responses.size() - 1).getEndTime();
        DateTime lastAfternoonTalkFinishDT = DATE_TIME_FORMAT.parseDateTime(lastAfternoonTalkFinish);
        lastAfternoonTalkFinishDT = lastAfternoonTalkFinishDT.withYear(NETWORKING_END_TIME.getYear()).withMonthOfYear(NETWORKING_END_TIME.getMonthOfYear()).withDayOfYear(NETWORKING_END_TIME.getDayOfYear());
        if (lastAfternoonTalkFinishDT.isBefore(NETWORKING_END_TIME) && (lastAfternoonTalkFinishDT.isAfter(NETWORKING_START_TIME) || lastAfternoonTalkFinishDT.isEqual(NETWORKING_START_TIME))) {
            responses.add(ConferenceResponse.builder().title("NETWORKING EVENT").startTime(lastAfternoonTalkFinish).endTime(DATE_TIME_FORMAT.print(AFTERNOON_END_TIME)).build());
        }
    }

}
