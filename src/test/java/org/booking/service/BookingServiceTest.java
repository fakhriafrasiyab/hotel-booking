package org.booking.service;

import org.booking.model.Booking;
import org.booking.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class BookingServiceTest {

    @InjectMocks
    private BookingService bookingService;

    @Mock
    private BookingRepository bookingRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBooking() {
        Booking booking = new Booking(null, "Fakhri", "Paris Hotel");
        when(bookingRepository.save(any(Booking.class))).thenReturn(new Booking(1L, "Fakhri", "Paris Hotel"));

        Booking result = bookingService.createBooking(booking);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getCustomerName()).isEqualTo("Fakhri");
        assertThat(result.getHotelName()).isEqualTo("Paris Hotel");

        verify(bookingRepository, times(1)).save(booking);
    }

    @Test
    void testGetAllBookings() {
        List<Booking> bookings = Arrays.asList(
                new Booking(1L, "Faxri Sabuhi", "Amsterdam Hotel"),
                new Booking(2L, "Aytan Sabuhi", "London Hotel")
        );

        when(bookingRepository.findAll()).thenReturn(bookings);

        List<Booking> result = bookingService.getAllBookings();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getCustomerName()).isEqualTo("Faxri Sabuhi");
        assertThat(result.get(1).getHotelName()).isEqualTo("London Hotel");

        verify(bookingRepository, times(1)).findAll();
    }

    @Test
    void testGetBookingById_Found() {
        Booking booking = new Booking(1L, "Faxri", "Paris Hotel");
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        Optional<Booking> result = bookingService.getBookingById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
        assertThat(result.get().getCustomerName()).isEqualTo("Faxri");

        verify(bookingRepository, times(1)).findById(1L);
    }

    @Test
    void testGetBookingById_NotFound() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Booking> result = bookingService.getBookingById(1L);

        assertThat(result).isNotPresent();

        verify(bookingRepository, times(1)).findById(1L);
    }

    @Test
    void testCancelBooking_BookingExists() {
        when(bookingRepository.existsById(1L)).thenReturn(true);
        doNothing().when(bookingRepository).deleteById(1L);

        boolean result = bookingService.cancelBooking(1L);

        assertThat(result).isTrue();
        verify(bookingRepository, times(1)).existsById(1L);
        verify(bookingRepository, times(1)).deleteById(1L);
    }

    @Test
    void testCancelBooking_BookingDoesNotExist() {
        when(bookingRepository.existsById(1L)).thenReturn(false);

        boolean result = bookingService.cancelBooking(1L);

        assertThat(result).isFalse();
        verify(bookingRepository, times(1)).existsById(1L);
        verify(bookingRepository, never()).deleteById(1L);
    }
}
