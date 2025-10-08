package lessons.lesson6.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Seat {
    private String seatNumber;
    private SeatClass seatClass;
    private boolean bookingStatus;
    private String bookingPersonFullName;

    public void stopBooking() {
        this.bookingStatus = false;
        this.bookingPersonFullName = null;
    }

    @Override
    public String toString() {
        return "Seat {" +
            "seatNumber='" + seatNumber + '\'' +
            ", seatClass=" + seatClass +
            ", bookingStatus=" + bookingStatus +
            (bookingStatus ? ", bookingPersonFullName='" + bookingPersonFullName + '\'' : "") +
            '}';
    }
}