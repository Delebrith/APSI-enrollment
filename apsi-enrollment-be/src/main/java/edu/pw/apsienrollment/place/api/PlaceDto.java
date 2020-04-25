package edu.pw.apsienrollment.place.api;

import edu.pw.apsienrollment.place.db.Place;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PlaceDto {
    Long id;
    String name;
    String building;
    String address;
    Integer capacity;

    public static PlaceDto of(Place entity) {
        return PlaceDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .building(entity.getBuilding())
                .address(entity.getAddress())
                .capacity(entity.getCapacity())
                .build();
    }
}
