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

    String searchQuery = null;
    @Min(0)
    Integer page = 0;
    @Min(1)
    Integer size = 100;


    public Specification<Event> buildEventSpec() {
        if(searchQuery == null) {
            throw new RuntimeException("Trying to build specification from an empty query");
        }
        EventSpecificationBuilder builder = new EventSpecificationBuilder();
        Pattern pattern = Pattern.compile("([\\w.]+?)([:<>])(\\w+?),", Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = pattern.matcher(searchQuery + ",");
        while(matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        return builder.build();
    }
}
