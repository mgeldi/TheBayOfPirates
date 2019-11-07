package de.htwberlin.de.TheBayOfPirates.model;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Component
public class DateAndTime {

    /**
     * Simply returns a new LocalDateTime from the current time as a String.
     * @return String LocalDateTime now
     */
    public String getDateAndTime() {
        return LocalDateTime.now().toString();
    }

}
