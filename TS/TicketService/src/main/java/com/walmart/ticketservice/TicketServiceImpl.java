package com.walmart.ticketservice;

public class TicketServiceImpl implements TicketService {

    private Venue venue;


    public TicketServiceImpl(Venue venue){
        this.venue = venue;
    }

    public int numSeatsAvailable(){
        return this.venue.getAvailableSeats();
    }

    public synchronized SeatHold findAndHoldSeats(int numOfSeats, String customerEmail){
        return  venue.findAndHoldSeats(numOfSeats, customerEmail);
    }

    public synchronized String reserveSeats(int seatHoldId, String customerEmail){
        return venue.reserveSeats(seatHoldId, customerEmail);
    }
}
