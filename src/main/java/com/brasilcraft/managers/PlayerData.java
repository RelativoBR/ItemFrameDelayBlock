package com.brasilcraft.managers;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class PlayerData {
    private String playerUUID;
    private String playerName;
    private LocalDateTime lastTime;
    private ArrayList<String> eventName;

    public PlayerData(String playerUUID, String playerName) {
        this.playerUUID = playerUUID;
        this.playerName = playerName;
        this.lastTime = LocalDateTime.now();
        this.eventName = new ArrayList<String>();
    }

    public String getPlayerUUID() {
        return playerUUID;
    }

    public void setPlayerUUID(String playerUUID) {
        this.playerUUID = playerUUID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public LocalDateTime getLastTime() {
        return lastTime;
    }

    public void setLastTime(LocalDateTime lastTime) {
        this.lastTime = lastTime != null ? lastTime : LocalDateTime.now();
    }

    public ArrayList<String> getEventName() {
        return eventName;
    }

    public void setEventName(ArrayList<String> eventName) {
        this.eventName = eventName;
    }

    public boolean hasEventData(String eventName){
        for(String e : this.eventName){
            if(e.equals(eventName)){
                return true;
            }
        }
        return false;
    }

    public void resetAll(){
        eventName.clear();
    }

}
