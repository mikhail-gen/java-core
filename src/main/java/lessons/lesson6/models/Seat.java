package lessons.lesson6.models;

import lessons.lesson6.booking.Booking;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;

import static lessons.lesson6.utils.DateTimeUtils.*;

@NoArgsConstructor
@Getter
@Setter
public class Seat implements Booking {
    private String seatNumber;
    private SeatClass seatClass;
    private boolean bookingStatus;
    private String bookingPersonFullName;
    private String bookingDateTime;

    @Override
    public void stopBooking() {
        this.bookingStatus = false;
        this.bookingPersonFullName = null;
        this.bookingDateTime = null;
    }

    @Override
    public void provideBooking(String bookingDateTime, String bookingPersonFullName, Boolean bookingStatus) {
        this.bookingStatus = bookingStatus;
        this.bookingPersonFullName = bookingPersonFullName;
        this.bookingDateTime = bookingDateTime;
    }

    @Override
    public boolean checkBookingExpiration() {
        var bookingDateTime = getParsedDateTime(this.bookingDateTime);
        if (bookingDateTime == null) {
            return false;
        }

        Duration duration = Duration.between(bookingDateTime, LocalDateTime.now());
        boolean isExpired = duration.toMinutes() > 24;
        long minutesAgo = duration.toMinutes();

        if (isExpired) {
            System.out.printf("[INFO] Booking for seat %s expired (%d minutes ago)%n",
                this.getSeatNumber(), minutesAgo);
        }

        return isExpired;
    }

    @Override
    public String toString() {
        return "Seat {" +
            "seatNumber='" + seatNumber + '\'' +
            ", seatClass=" + seatClass +
            ", bookingStatus=" + bookingStatus +
            (bookingStatus ? ", bookingPersonFullName='" + bookingPersonFullName + '\'' : "") +
            (bookingStatus ? ", bookingDateTime='" + bookingDateTime + '\'' : "") +
            '}';
    }
}