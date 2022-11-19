package com.github.relativobr.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class EventData {

    private String eventName;
    private LocalDateTime eventTime;

}
