package com.pareenja.carrentalpro.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Car implements Parcelable {
    public static final Creator<Car> CREATOR = new Creator<Car>() {
        @Override
        public Car createFromParcel(Parcel in) {
            return new Car(in);
        }

        @Override
        public Car[] newArray(int size) {
            return new Car[size];
        }
    };
    private String vinNumber;
    private String carModel;
    private String brand;
    private String color;
    private int capacity;
    private double pricePerHour;
    private double pricePerDay;
    private String id;
    private boolean isReserved;
    private boolean isBroken;


    public Car() {

    }

    private boolean isBooked;

    protected Car(Parcel in) {
        id = in.readString();
        vinNumber = in.readString();
        carModel = in.readString();
        brand = in.readString();
        color = in.readString();
        capacity = in.readInt();
        pricePerHour = in.readDouble();
        pricePerDay = in.readDouble();
        isReserved = in.readByte() != 0;
        isBroken = in.readByte() != 0;
        isBooked = in.readByte() != 0;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public String getVinNumber() {
        return vinNumber;
    }

    public void setVinNumber(String vinNumber) {
        this.vinNumber = vinNumber;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }

    public boolean isBroken() {
        return isBroken;
    }

    public void setBroken(boolean broken) {
        isBroken = broken;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Car{" +
                "vinNumber='" + vinNumber + '\'' +
                ", carModel='" + carModel + '\'' +
                ", brand='" + brand + '\'' +
                ", color='" + color + '\'' +
                ", capacity=" + capacity +
                ", pricePerHour=" + pricePerHour +
                ", pricePerDay=" + pricePerDay +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(vinNumber);
        dest.writeString(carModel);
        dest.writeString(brand);
        dest.writeString(color);
        dest.writeInt(capacity);
        dest.writeDouble(pricePerHour);
        dest.writeDouble(pricePerDay);
        dest.writeByte((byte) (isReserved ? 1 : 0));
        dest.writeByte((byte) (isBroken ? 1 : 0));
        dest.writeByte((byte) (isBooked ? 1 : 0));
    }
}
