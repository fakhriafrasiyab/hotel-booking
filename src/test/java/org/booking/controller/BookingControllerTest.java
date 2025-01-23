package org.booking.controller;

import org.booking.model.Booking;
import org.booking.service.BookingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    @Test
    void testCreateBooking() throws Exception {
        Booking booking = new Booking(null, "Alice", "Paris Hotel");
        Booking savedBooking = new Booking(1L, "Alice", "Paris Hotel");

        when(bookingService.createBooking(any(Booking.class))).thenReturn(savedBooking);

        mockMvc.perform(post("/bookings/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"customerName\":\"Alice\",\"hotelName\":\"Paris Hotel\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.customerName").value("Alice"))
                .andExpect(jsonPath("$.hotelName").value("Paris Hotel"));

        verify(bookingService, times(1)).createBooking(any(Booking.class));
    }

    @Test
    void testGetAllBookings() throws Exception {
        List<Booking> bookings = Arrays.asList(
                new Booking(1L, "John Doe", "Amsterdam Hotel"),
                new Booking(2L, "Jane Doe", "London Hotel")
        );

        when(bookingService.getAllBookings()).thenReturn(bookings);

        mockMvc.perform(get("/bookings/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].customerName").value("John Doe"))
                .andExpect(jsonPath("$[1].hotelName").value("London Hotel"));

        verify(bookingService, times(1)).getAllBookings();
    }

    @Test
    void testCancelBooking_BookingExists() throws Exception {
        when(bookingService.cancelBooking(1L)).thenReturn(true);

        mockMvc.perform(delete("/bookings/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Booking cancelled successfully"));

        verify(bookingService, times(1)).cancelBooking(1L);
    }

    @Test
    void testCancelBooking_BookingDoesNotExist() throws Exception {
        when(bookingService.cancelBooking(1L)).thenReturn(false);

        mockMvc.perform(delete("/bookings/1"))
                .andExpect(status().isNotFound());

        verify(bookingService, times(1)).cancelBooking(1L);
    }
}