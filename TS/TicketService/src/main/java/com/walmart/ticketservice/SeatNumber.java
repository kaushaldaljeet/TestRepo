package com.walmart.ticketservice;

public class SeatNumber {

    private int rowNumber;

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    private int colNumber;
    public void setColNumber(int colNumber) {
        this.colNumber = colNumber;
    }

    public int getColNumber() {
        return colNumber;
    }

    public SeatNumber(int rowNumber, int colNumber){
        this.rowNumber = rowNumber;
        this.colNumber = colNumber;
    }

    public String toString(){
        return "" + (char)('A' + (getRowNumber() - 1)) + getColNumber();
    }
}
