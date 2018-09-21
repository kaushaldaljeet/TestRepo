package com.walmart.ticketservice;

public class Seat {
    SeatStatus status;
    SeatNumber seatNumber;

    public Seat(int row, int col){
        this.status = SeatStatus.AVAILABLE;
        this.seatNumber = new SeatNumber(row, col);
    }

    public SeatStatus getStatus() {
        return status;
    }

    public void setStatus(SeatStatus status) {
        this.status = status;
    }
}
