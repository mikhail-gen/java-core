package lessons.lesson6.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Flight extends SeatBookingMap {
    private String flightID;
    private String flightDateTime;
}
