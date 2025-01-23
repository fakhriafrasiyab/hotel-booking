package org.booking.repository;

import org.booking.model.Booking;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookingRepositoryTest {

    @Autowired
    private BookingRepository bookingRepository;

    @Test
    void testFindAll() {
        Booking booking1 = new Booking(null, "Fakhri", "London Hotel");
        Booking booking2 = new Booking(null, "Aytan", "New York Hotel");

        bookingRepository.save(booking1);
        bookingRepository.save(booking2);

        List<Booking> bookings = bookingRepository.findAll();

        assertThat(bookings).hasSize(2);
        assertThat(bookings.get(0).getCustomerName()).isEqualTo("Fakhri");
        assertThat(bookings.get(1).getHotelName()).isEqualTo("New York Hotel");
    }
}