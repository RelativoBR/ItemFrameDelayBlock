package com.github.relativobr.model;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class PlayerData {

    private String playerUUID;
    private String playerName;
    private LocalDateTime time;

}
