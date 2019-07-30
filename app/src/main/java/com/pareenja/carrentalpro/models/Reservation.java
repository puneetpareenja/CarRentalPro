package com.pareenja.carrentalpro.models;

import java.util.Date;

public class Reservation {
    private String id;
    private String userId;
    private String carId;
    private String bookedById;
    private boolean isBooked;
    private double price;
    private Date bookingStartDate;
    private Date bookingEndDate;
    private boolean isByHour;
    private double deposit;
    private double total;

    public Reservation() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getBookedById() {
        return bookedById;
    }

    public void setBookedById(String bookedById) {
        this.bookedById = bookedById;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getBookingStartDate() {
        return bookingStartDate;
    }

    public void setBookingStartDate(Date bookingStartDate) {
        this.bookingStartDate = bookingStartDate;
    }

    public Date getBookingEndDate() {
        return bookingEndDate;
    }

    public void setBookingEndDate(Date bookingEndDate) {
        this.bookingEndDate = bookingEndDate;
    }

    public boolean isByHour() {
        return isByHour;
    }

    public void setByHour(boolean byHour) {
        isByHour = byHour;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", carId='" + carId + '\'' +
                ", bookedById='" + bookedById + '\'' +
                ", isBooked=" + isBooked +
                ", price=" + price +
                ", bookingStartDate=" + bookingStartDate +
                ", bookingEndDate=" + bookingEndDate +
                ", isByHour=" + isByHour +
                ", deposit=" + deposit +
                ", total=" + total +
                '}';
    }
}
