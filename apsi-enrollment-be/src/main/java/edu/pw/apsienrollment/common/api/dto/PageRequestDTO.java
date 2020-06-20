package edu.pw.apsienrollment.common.api.dto;

import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {
    @Min(0)
    Integer page = 0;
    @Min(1)
    Integer size = 100;
}