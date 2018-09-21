package com.walmart.ticketservice;

public class SeatReleaseTask extends Thread {

    private SeatHold seatHold;
    private long delay;
    private boolean running=true;

    SeatReleaseTask(SeatHold seatHold)
    {
        this.seatHold = seatHold;
        this.delay = seatHold.getDelay();
    }

    @Override
    public void run() {

        while (this.running) {
            try {
                sleep(this.delay);
                if(seatHold.isOnHold()) {
                    this.seatHold.changeSeatStatus(SeatStatus.AVAILABLE);
                    this.seatHold.setHold(false);
                    int newAvailableSeats = this.seatHold.getVenue().getAvailableSeats() + seatHold.getSeats().size();
                    this.seatHold.getVenue().setAvailableSeats(newAvailableSeats);
                    this.seatHold.getVenue().getHoldSeatsMap().remove(this.seatHold.getSeatHoldId());
                    stopTask();
                }
            }
            catch (InterruptedException e) {
                if(!this.running){
                    break;
                }
            }
        }

    }

    public void stopTask() {
        this.running = false;
        interrupt();
    }

}
