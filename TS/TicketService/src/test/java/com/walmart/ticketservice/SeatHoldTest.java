package com.walmart.ticketservice;

import org.junit.Before;
import org.junit.Test;

import javax.swing.plaf.nimbus.State;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.*;


/**
 * Unit test for SeatHold
 */

public class SeatHoldTest {
    Venue venue;

    @Before
    public void setUp() throws Exception {

        this.venue = new Venue()
                .name("AMC")
                .seats(5,5)
                .expirationDelay(120)
                .build();
    }

    //Test change seat status
    @Test
    public void testChangeSeatStatus() {
        List<Seat> seats = new ArrayList<>();

        for(int i=0; i < 5; i++){
            seats.add(new Seat(1,2));
        }
        SeatHold seatHold = new SeatHold();
        seatHold.setSeats(seats);
        seatHold.changeSeatStatus(SeatStatus.HOLD);

        SeatStatus[] expectedStatus = new SeatStatus[seats.size()];
        SeatStatus[] actualStatus = new SeatStatus[]{SeatStatus.HOLD, SeatStatus.HOLD, SeatStatus.HOLD, SeatStatus.HOLD, SeatStatus.HOLD};

        for(int i = 0; i < expectedStatus.length; i++){
            expectedStatus[i] = seats.get(i).getStatus();
        }
        assertArrayEquals(expectedStatus, actualStatus);
    }

}
