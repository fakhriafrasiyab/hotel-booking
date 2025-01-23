package org.booking.service;

import org.booking.model.Booking;
import org.booking.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    public Booking createBooking(Booking booking) {
        // Check for duplicates
        Optional<Booking> existingBooking = bookingRepository.findAll().stream()
                .filter(b -> b.getCustomerName().equalsIgnoreCase(booking.getCustomerName())
                        && b.getHotelName().equalsIgnoreCase(booking.getHotelName()))
                .findFirst();

        if (existingBooking.isPresent()) {
            throw new IllegalArgumentException("A booking for this customer at this hotel already exists");
        }
        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public boolean cancelBooking(Long id) {
        if (bookingRepository.existsById(id)) {
            bookingRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }
}