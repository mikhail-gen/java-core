package lessons.lesson6;

import static lessons.lesson6.utils.YmlHandler.*;

import lessons.lesson6.models.Flight;
import lessons.lesson6.models.Seat;
import lessons.lesson6.models.SeatBookingMap;

import java.time.LocalDateTime;
import java.util.Scanner;

import static lessons.lesson6.utils.DateTimeUtils.*;

public class MainBooking {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Flight flight = readFromYmlFile();
        System.out.println("Flight " + flight.getFlightID() + " date and time: " + flight.getFlightDateTime());

        while (true) {
            printFlightBookingMenu();
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1" -> showAllBookings(flight);
                case "2" -> addBooking(flight);
                case "3" -> eraseBooking(flight);
                case "4" -> {
                    writeToYmlFile(flight);
                    System.out.println("Data saved. Exiting program.");
                    return;
                }
                default -> System.out.println("Invalid input. Please try again.");
            }
        }
    }

    private static void printFlightBookingMenu() {
        System.out.println("""
                            
            === Seat Booking System ===
            1. Show all seats
            2. Add a booking
            3. Cancel a booking
            4. Save and exit
            Enter your choice:
            """);
    }

    private static void showAllBookings(SeatBookingMap seatBookingMap) {
        System.out.println("\nCurrent seats and bookings:\n");
        for (Seat seat : seatBookingMap.getSeats().values()) {
            if (seat.checkBookingExpiration()) {
                seat.stopBooking();
            }
            System.out.println(seat);
        }
    }

    private static void addBooking(SeatBookingMap bookingList) {
        System.out.print("Enter seat number: ");
        String seatNumber = scanner.nextLine().trim();

        Seat seat = bookingList.getSeats().get(seatNumber);
        if (seat == null) {
            System.out.println("Seat not found.");
            return;
        }

        if (seat.isBookingStatus()) {
            System.out.println("This seat is already booked by: " + seat.getBookingPersonFullName());
            return;
        }

        System.out.print("Enter full name of the person booking: ");
        String name = scanner.nextLine().trim();

        seat.provideBooking(formatDateTimeToString(LocalDateTime.now()), name, true);
        System.out.println("Booking added successfully.");
    }

    private static void eraseBooking(SeatBookingMap bookingList) {
        System.out.print("Enter seat number to cancel booking: ");
        String seatNumber = scanner.nextLine().trim();

        Seat seat = bookingList.getSeats().get(seatNumber);
        if (seat == null) {
            System.out.println("Seat not found.");
            return;
        }

        if (!seat.isBookingStatus()) {
            System.out.println("This seat is already available.");
            return;
        }

        seat.stopBooking();
        System.out.println("Booking cancelled.");
    }
}