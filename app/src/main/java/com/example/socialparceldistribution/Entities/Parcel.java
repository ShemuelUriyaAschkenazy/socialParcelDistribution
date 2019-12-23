package com.example.socialparceldistribution.Entities;

import android.location.Address;
import android.location.Location;

import java.util.Date;

public class Parcel {

    public int getParcelId() {
        return parcelId;
    }

    public void setParcelId(int parcelId) {
        this.parcelId = parcelId;
    }

    public ParcelType getParcelType() {
        return parcelType;
    }

    public void setParcelType(ParcelType parcelType) {
        this.parcelType = parcelType;
    }

    public boolean isFragile() {
        return isFragile;
    }

    public void setFragile(boolean fragile) {
        isFragile = fragile;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getRecipientPhone() {
        return recipientPhone;
    }

    public void setRecipientPhone(String recipientPhone) {
        this.recipientPhone = recipientPhone;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public String getMessengerName() {
        return messengerName;
    }

    public void setMessengerName(String messengerName) {
        this.messengerName = messengerName;
    }

    public int getMessengerId() {
        return messengerId;
    }

    public void setMessengerId(int messengerId) {
        this.messengerId = messengerId;
    }

    public enum ParcelType {envelope, smallPackage, bigPackage}

    public enum ParcelStatus {registered, pickUpSuggested, onTheWay, successfullyArrived}


    private int parcelId;
    private ParcelType parcelType;
    private boolean isFragile;
    double weight;
    private Location location;
    private String recipientName;
    private String address;
    private Date deliveryDate;
    private Date arrivalDate;
    private String recipientPhone;
    private String recipientEmail;
    private String messengerName;
    private int messengerId;


}
