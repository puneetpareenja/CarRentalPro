package com.pareenja.carrentalpro.models;

import java.util.Date;

public class Reservation {
    private String id;
    private Date reservationDate;
    private String userId;
    private String carId;
    private String bookedById;
    private Date reservationExpiryDate;
    private boolean isBooked;
    private boolean isExpired;
    private double price;
    private Date bookingStartDate;
    private Date bookingEndDate;
    private boolean isByHour;
    private double deposit;
    private double total;
}
