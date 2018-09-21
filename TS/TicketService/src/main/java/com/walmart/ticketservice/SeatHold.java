package com.walmart.ticketservice;

import java.util.List;

public class SeatHold {

    public void setSeatHoldId(int seatHoldId) {
        this.seatHoldId = seatHoldId;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public void setReleaseTask(SeatReleaseTask releaseTask) {
        this.releaseTask = releaseTask;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    private int seatHoldId;
    private String customerEmail;
    private List<Seat> seats;
    private boolean hold;

    public long getDelay() {
        return delay;
    }

    private long delay;
    private SeatReleaseTask releaseTask;

    public Venue getVenue() {
        return venue;
    }

    private Venue venue;

    public int getSeatHoldId() {
        return seatHoldId;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public boolean isOnHold() {
        return hold;
    }

    void setHold(boolean hold){
        this.hold = hold;
    }


    void changeSeatStatus(SeatStatus status){
        for (Seat seat : this.seats) seat.setStatus(status);
    }
    void startReleaseTask(){
        this.releaseTask.start();
    }
    void stopReleaseTask(){
        this.releaseTask.stopTask();
    }

    void setDelay(long delay) {
        this.delay = delay;
    }
}
