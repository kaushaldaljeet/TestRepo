package com.walmart.ticketservice;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.*;


/**
 * Unit test for Seat
 */

public class SeatTest {
    Seat seat;

    @Before
    public void setUp() throws Exception {

        this.seat = new Seat(1, 2);
    }

    //Test change seat status
    @Test
    public void testSeatStatus(){
        this.seat.setStatus(SeatStatus.HOLD);
        SeatStatus expectedSeatStatus = this.seat.getStatus();
        assertEquals(expectedSeatStatus, SeatStatus.HOLD);
    }

    //Test Seat Number assignment
    @Test
    public void testSeatNumber(){
        this.seat.seatNumber.setRowNumber(2);
        this.seat.seatNumber.setColNumber(2);

        assertEquals(this.seat.seatNumber.toString(), "B2");
    }
}
