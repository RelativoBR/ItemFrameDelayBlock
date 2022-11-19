package com.github.relativobr.model;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Getter
@Setter
@Builder
public class PlayerData {

    private String playerUUID;
    private String playerName;
    private Map<String, EventData> eventName = new HashMap<>();

    public boolean hasInEventData(String eventName){
        return this.eventName.containsKey(eventName);
    }

    public boolean notHasInEventData(String eventName){
        return !hasInEventData(eventName);
    }

    public LocalDateTime getTimeFromEventData(String event) {
        if (this.eventName == null || !eventName.containsKey(event)) {
            return LocalDateTime.of(LocalDate.MIN, LocalTime.MIN);
        }
        return eventName.get(event).getEventTime();
    }

    public void addEventData(String event) {
        if (this.eventName == null) {
            this.eventName = new HashMap<>();
        }
        this.removeEventData(event);
        eventName.put(event,
                EventData.builder()
                        .eventName(event)
                        .eventTime(LocalDateTime.now())
                        .build());
    }

    public void removeEventData(String event) {
        if (this.eventName == null) {
            this.eventName = new HashMap<>();
        } else {
            eventName.remove(event);
        }
    }
}
