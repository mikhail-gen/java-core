package lessons.lesson6.booking;

public interface Booking {

    /**
     * Eliminates booking data
     */
    void stopBooking();

    /**
     * Writes the booking data.
     */

    void provideBooking(String bookingDateTime, String bookingPersonFullName, Boolean bookingStatus);

    /**
     * Checks if the booking is expired (default expiration interval is 24min).
     *
     * @return
     */
    boolean checkBookingExpiration();
}
