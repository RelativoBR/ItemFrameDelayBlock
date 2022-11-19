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
    private Set<String> eventName;

    public boolean hasInEventData(String eventName){
        for(String e : this.eventName){
            if(e.equals(eventName)){
                return true;
            }
        }
        return false;
    }

    public boolean notHasInEventData(String eventName){
        return !hasInEventData(eventName);
    }

    public void addEventName(String event) {
        if(this.eventName == null){
            this.eventName = new HashSet<>();
        }
        eventName.add(event);
    }
}
