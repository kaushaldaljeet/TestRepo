package com.walmart.ticketservice;

import java.util.List;

public class SeatHoldBuilder {

    private int seatHoldId;
    private String customerEmail;
    private List<Seat> seats;
    private long delay;
    private Venue venue;

    SeatHoldBuilder id(int id){
        this.seatHoldId=id;
        return this;
    }

    SeatHoldBuilder seatsHold(List<Seat> seats){
        this.seats = seats;
        return this;
    }

    SeatHoldBuilder customerEmail(String customerEmail){
        this.customerEmail = customerEmail;
        return this;
    }

    SeatHoldBuilder expirationDelay(long delay) {
        this.delay = delay;
        return this;
    }

    SeatHoldBuilder venue(Venue venue) {
        this.venue = venue;
        return this;
    }

    SeatHold build()
    {
        SeatHold seatHold = new SeatHold();
        seatHold.setSeatHoldId(this.seatHoldId);
        seatHold.setSeats(this.seats);
        seatHold.setCustomerEmail(this.customerEmail);
        seatHold.setHold(true);
        seatHold.setVenue(venue);
        seatHold.setDelay(this.delay);
        SeatReleaseTask task = new SeatReleaseTask(seatHold);
        seatHold.setReleaseTask(task);
        seatHold.startReleaseTask();
        return seatHold;
    }
}
