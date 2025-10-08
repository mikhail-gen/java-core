package lessons.lesson6.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
public class SeatBookingMap {
    private Map<String, Seat> seats = new HashMap<>();
}