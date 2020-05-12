package edu.pw.apsienrollment.event.api.dto;

import lombok.Builder;
import lombok.Value;
import lombok.NonNull;

import edu.pw.apsienrollment.event.db.EventType;

import java.util.List;

@Value
@Builder
public class EventTypeListDto {
    List<EventType> eventTypeList;

    public static EventTypeListDto of(@NonNull List<EventType> eventTypeList) {
        return EventTypeListDto.builder()
                .eventTypeList(eventTypeList)
                .build();
    }
}