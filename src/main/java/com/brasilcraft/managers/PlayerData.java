package com.brasilcraft.managers;

import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@Builder
public class PlayerData {

    private String playerUUID;
    private String playerName;
    private LocalDateTime lastTime;
    private List<String> eventName;

    public boolean hasInEventData(String eventName){
        return this.eventName.contains(eventName);
    }

    public boolean notHasInEventData(String eventName){
        return !hasInEventData(eventName);
    }

    public void addEventName(String event) {
        if(this.eventName == null){
            this.eventName = new ArrayList<>();
        }
        eventName.add(event);
    }
}
