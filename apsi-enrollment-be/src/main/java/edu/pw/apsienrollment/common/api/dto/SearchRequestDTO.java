package edu.pw.apsienrollment.common.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequestDTO {

    String searchQuery;
    @Min(0)
    Integer page = 0;
    @Min(1)
    Integer size = 100;
}
