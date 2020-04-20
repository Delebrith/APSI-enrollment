package edu.pw.apsienrollment.event.api;

import edu.pw.apsienrollment.event.db.Event;
import edu.pw.apsienrollment.event.specification.EventSpecificationBuilder;
import lombok.*;
import org.springframework.data.jpa.domain.Specification;

import javax.validation.constraints.Min;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventRequestDTO {

    String searchQuery;
    @Min(0)
    Integer page = 0;
    @Min(1)
    Integer size = 100;
}
