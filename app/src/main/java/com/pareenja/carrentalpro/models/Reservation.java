package com.pareenja.carrentalpro.models;

import java.util.Date;

public class Reservation {
    private String id;
    private Date reservationDate;
    private String userID;
    private String carID;
    private Date reservationExpiryDate;
    private boolean isBooked;
    private boolean isExpired;
}
