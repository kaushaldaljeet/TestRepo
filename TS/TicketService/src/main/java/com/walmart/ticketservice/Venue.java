package com.walmart.ticketservice;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class Venue {

    private Seat[][] seats;
    private int availableSeats;

    private HashMap<Integer, SeatHold> holdSeatsMap;
    private int seatHolds = 0;

    public String getName() {
        return name;
    }

    private String name;

    public long getExpirationDelay() {
        return expirationDelay;
    }

    public void setExpirationDelay(long expirationDelay) {
        this.expirationDelay = expirationDelay;
    }

    private long expirationDelay;
    HashMap<Integer, SeatHold> getHoldSeatsMap() {
        return holdSeatsMap;
    }
    Venue(){

    }
    public Venue name(String name){
        this.name=name;
        return this;
    }
    public Venue seats(int rows, int cols){
        this.seats=new Seat[rows][cols];
        return this;
    }
    Venue expirationDelay(long delay){
        this.expirationDelay = delay;
        return this;
    }
    public Venue build(){

        Venue venue = new Venue();
        venue.name = this.name;
        venue.seats = this.seats;
        venue.expirationDelay = this.expirationDelay;

        int rows = this.seats.length;
        int cols = this.seats[0].length;
        for(int row = 0; row < rows; row++){
            for(int col = 0; col < cols ;col++) {
                venue.seats[row][col] = new Seat(row, col+1);
            }
        }

        venue.holdSeatsMap = new HashMap<>();
        venue.availableSeats = rows * cols;
        return venue;
    }

    SeatHold findAndHoldSeats(int numOfSeats, String customerEmail){

        if(numOfSeats < 1)
            throw new IllegalArgumentException("Reserve atleast one seat");

        if( availableSeats < numOfSeats)
            return null;

        SeatHold seatHold = new SeatHoldBuilder()
                .id(++seatHolds)
                .seatsHold(allocateSeats(numOfSeats))
                .customerEmail(customerEmail)
                .venue(this)
                .expirationDelay(getExpirationDelay() * 1000)
                .build();

        this.holdSeatsMap.put(seatHold.getSeatHoldId(), seatHold);

        return seatHold;
    }

    void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    int getAvailableSeats(){
        return this.availableSeats;
    }

    private List<Seat> allocateSeats(int numOfSeats){

        //Assign seats
        List<Seat>  assignedSeats = new ArrayList<>();

        this.availableSeats-=numOfSeats;

        for(int row = seats.length - 1; row >=0 && numOfSeats>0 ;row--){
            for(int col = 0; col < seats[0].length && numOfSeats>0 ;col++){
                if(seats[row][col].getStatus() == SeatStatus.AVAILABLE) {
                    seats[row][col].setStatus(SeatStatus.HOLD);
                    numOfSeats--;
                    assignedSeats.add(seats[row][col]);
                }
            }
        }


        return assignedSeats;
    }

    public String reserveSeats(int seatHoldId, String customerEmail) {
        //check if the seats are held before reserving
        if(!this.holdSeatsMap.containsKey(seatHoldId)){
            return null;
        }

        SeatHold seatHold = this.holdSeatsMap.get(seatHoldId);

        if(!seatHold.getCustomerEmail().equals(customerEmail))
            throw new IllegalArgumentException("Invalid Customer");

        //Hold Expired
        if(!seatHold.isOnHold())
            return null;

        if(seatHold.isOnHold()){
            //change seat status to reserved
            seatHold.changeSeatStatus(SeatStatus.RESERVED);
            seatHold.stopReleaseTask();
            holdSeatsMap.remove(seatHold.getSeatHoldId());
        }

        return name + String.format("%08d", seatHold.getSeatHoldId());
    }

    public Seat[][] getSeats() {
        return seats;
    }
}
