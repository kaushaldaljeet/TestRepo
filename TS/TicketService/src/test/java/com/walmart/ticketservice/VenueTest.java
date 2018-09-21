package com.walmart.ticketservice;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.*;


/**
 * Unit test for Venue
 */

public class VenueTest {

    Venue venue;
    static final Logger LOG = Logger.getLogger(TicketService.class.getName());

    @Before
    public void setUp() throws Exception {

        this.venue = new Venue()
                .name("AMC")
                .seats(5,5)
                .expirationDelay(120)
                .build();
    }


    //Test hold seats is Valid
    @Test
    public void testFindAndHoldSeatsValid(){
        this.venue.setExpirationDelay(5);
        SeatHold seatHold = this.venue.findAndHoldSeats(5, "abc@gmail.com");
        assertNotNull(seatHold);
    }

    //Test reserve seats after Hold Expiry
    @Test
    public void testReserveSeatsAfterExpiry(){
        this.venue.setExpirationDelay(2);
        SeatHold seatHold = this.venue.findAndHoldSeats(5, "abc@gmail.com");
        userDeciding(4);
        String confirmationCode = this.venue.reserveSeats(seatHold.getSeatHoldId(),"abc@gmail.com" );
        assertNull(confirmationCode);
    }


    //Test Reservation is valid
    @Test
    public void testReserveSeats(){
        this.venue.setExpirationDelay(2);
        SeatHold seatHold = this.venue.findAndHoldSeats(5, "abc@gmail.com");
        String confirmationCode = this.venue.reserveSeats(seatHold.getSeatHoldId(),"abc@gmail.com" );
        assertNotNull(confirmationCode);
    }


    //Test for reserving seats with Null email
    @Test
    public void testReserveSeatsWithNullEmail(){
        try{
            this.venue.setExpirationDelay(3);
            SeatHold seatHold = this.venue.findAndHoldSeats(4, "abc@xyz.com");
            this.venue.reserveSeats(seatHold.getSeatHoldId(), "");
        }
        catch(IllegalArgumentException e){
            assertEquals(e.getMessage(), "Invalid Customer");
        }
    }

    //Test for reserving seats with different email
    @Test
    public void testReserveSeatsWithEmailMismatch(){
        try{
            this.venue.setExpirationDelay(3);
            SeatHold seatHold = this.venue.findAndHoldSeats(4, "abc@xyz.com");
            this.venue.reserveSeats(seatHold.getSeatHoldId(), "pqr@gmail.com");
        }
        catch(IllegalArgumentException e){
            assertEquals(e.getMessage(), "Invalid Customer");
        }
    }

    //Test reserve with duplicate seatHoldId and customer Email
    @Test
    public void testReserveSeatsWithDuplicateParameters(){
        this.venue.setExpirationDelay(2);
        SeatHold seatHold = this.venue.findAndHoldSeats(4, "abc@gmail.com");
        String confirmationCode = this.venue.reserveSeats(seatHold.getSeatHoldId(), "abc@gmail.com");

        //DupliacteCall
        String duplicateConfirmationCode = this.venue.reserveSeats(seatHold.getSeatHoldId(), "abc@gmail.com");
        assertNull(duplicateConfirmationCode);

    }

    //Test reservation returns valid confirmation code
    @Test
    public void testValidConfirmationCode(){
        this.venue.setExpirationDelay(2);
        SeatHold seatHold = this.venue.findAndHoldSeats(4, "abc@gmail.com");
        userDeciding(1);
        String confirmationCode = this.venue.reserveSeats(seatHold.getSeatHoldId(), "abc@gmail.com");
        assertEquals(confirmationCode, "AMC00000001");
    }

    //Test reservation returns null confirmation code
    @Test
    public void testNullConfirmationCode(){
        this.venue.setExpirationDelay(2);
        SeatHold seatHold = this.venue.findAndHoldSeats(4, "abc@gmail.com");
        userDeciding(3);
        String confirmationCode = this.venue.reserveSeats(seatHold.getSeatHoldId(), "abc@gmail.com");
        assertNull(confirmationCode);
    }


    //Test Number of Seats Available
    @Test
    public void testNumberOfSeatsAvailable(){
        int availableSeats = this.venue.getAvailableSeats();
        assertEquals(availableSeats, 25);
    }


    //Test number of seats available after hold
    @Test
    public void testNumberOfSeatsAvailableAfterHold(){
        this.venue.setExpirationDelay(3);
        SeatHold seatHold = this.venue.findAndHoldSeats(5, "abc@xyz.com");
        int availableSeats = this.venue.getAvailableSeats();
        assertEquals(availableSeats, 20);

    }

    //Test number of seats available after hold expiry
    @Test
    public void testNumberOfSeatsAvailableAfterHoldExpiry(){
        this.venue.setExpirationDelay(3);
        SeatHold seatHold = this.venue.findAndHoldSeats(5, "abc@xyz.com");
        userDeciding(4);
        int availableSeats = this.venue.getAvailableSeats();
        assertEquals(availableSeats, 25);
    }

    //Test number of seats available after reservation
    @Test
    public void testNumberOfSeatsAvailableAfterReservation(){
        this.venue.setExpirationDelay(3);
        SeatHold seatHold = this.venue.findAndHoldSeats(5, "abc@xyz.com");
        this.venue.reserveSeats(seatHold.getSeatHoldId(), "abc@xyz.com");
        int availableSeats = this.venue.getAvailableSeats();
        assertEquals(availableSeats, 20);
    }


    //Test seat status after hold
    @Test
    public void testSeatStatusAfterHold() {
        this.venue.setExpirationDelay(3);
        SeatHold seatHold = this.venue.findAndHoldSeats(4, "abc@xyz.com");
        List<Seat> seats = seatHold.getSeats();
        SeatStatus[] status = new SeatStatus[seats.size()];

        for(int i = 0; i < seats.size(); i++){
            status[i] = seats.get(i).getStatus();
        }

        SeatStatus[] actualStatus = new SeatStatus[]{SeatStatus.HOLD, SeatStatus.HOLD, SeatStatus.HOLD, SeatStatus.HOLD};

        assertArrayEquals(status, actualStatus);
    }

    //Test seat status after hold expiry
    @Test
    public void testSeatStatusAfterHoldExpiry() {
        this.venue.setExpirationDelay(3);
        SeatHold seatHold = this.venue.findAndHoldSeats(4, "abc@xyz.com");
        userDeciding(5);
        List<Seat> seats = seatHold.getSeats();
        SeatStatus[] status = new SeatStatus[seats.size()];

        for(int i = 0; i < seats.size(); i++){
            status[i] = seats.get(i).getStatus();
        }

        SeatStatus[] expectedStatus = new SeatStatus[]{SeatStatus.AVAILABLE, SeatStatus.AVAILABLE, SeatStatus.AVAILABLE, SeatStatus.AVAILABLE};

        assertArrayEquals(status, expectedStatus);
    }

    //Test seat status after reservation
    @Test
    public void testSeatStatusAfterReservation() {
        this.venue.setExpirationDelay(3);
        SeatHold seatHold = this.venue.findAndHoldSeats(4, "abc@xyz.com");
        this.venue.reserveSeats(seatHold.getSeatHoldId(), "abc@xyz.com");
        List<Seat> seats = seatHold.getSeats();
        SeatStatus[] status = new SeatStatus[seats.size()];

        for(int i = 0; i < seats.size(); i++){
            status[i] = seats.get(i).getStatus();
        }

        SeatStatus[] expectedStatus = new SeatStatus[]{SeatStatus.RESERVED, SeatStatus.RESERVED, SeatStatus.RESERVED, SeatStatus.RESERVED};

        assertArrayEquals(status, expectedStatus);
    }

    //Test number of seats requested is greater than number of seats available
    @Test
    public void testFindAndHoldNumberOfSeatsGreaterThanAvailableSeats(){
        SeatHold seatHold = this.venue.findAndHoldSeats(30, "abc@xyz.com");
        assertNull(seatHold);

    }

    // Test for invalid number of seats requested for holding
    @Test
    public void testFindAndHoldWithZeroSeats(){
        try{
            SeatHold seatHold = this.venue.findAndHoldSeats(0, "abc@xyz.com");
        }
        catch (IllegalArgumentException e){
            assertEquals(e.getMessage(), "Reserve atleast one seat");
        }
    }


    //Method to introduce delay in reserving seats after holding
    private void userDeciding(long seconds) {
        try {
            Thread.sleep(seconds * 1000);
        }
        catch (Exception e){
            LOG.log(Level.SEVERE, e.getMessage());
        }
    }
}
