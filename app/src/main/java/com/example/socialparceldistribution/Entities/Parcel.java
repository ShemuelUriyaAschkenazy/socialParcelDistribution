package com.example.socialparceldistribution.Entities;

import android.location.Address;
import android.location.Location;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Parcel {
    public Parcel(int parcelId, ParcelType parcelType, Boolean isFragile, Double weight, Location location, String recipientName, String address, Date deliveryDate, Date arrivalDate, String recipientPhone, String recipientEmail, String messengerName, Integer messengerId) {
        this.parcelId = parcelId;
        this.parcelType = parcelType;
        this.isFragile = isFragile;
        this.weight = weight;
        this.location = location;
        this.recipientName = recipientName;
        this.address = address;
        this.deliveryDate = deliveryDate;
        this.arrivalDate = arrivalDate;
        this.recipientPhone = recipientPhone;
        this.recipientEmail = recipientEmail;
        this.messengerName = messengerName;
        this.messengerId = messengerId;
    }

    public Parcel() {
    }

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

    public Boolean isFragile() {
        return isFragile;
    }

    public void setFragile(Boolean fragile) {
        isFragile = fragile;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
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

    public Integer getMessengerId() {
        return messengerId;
    }

    public void setMessengerId(Integer messengerId) {
        this.messengerId = messengerId;
    }

    public enum ParcelType {envelope, smallPackage, bigPackage}

    public enum ParcelStatus {registered, pickUpSuggested, onTheWay, successfullyArrived}

@PrimaryKey
    private int parcelId;
    private ParcelType parcelType;
    private Boolean isFragile;
    private Double weight;
    private Location location;
    private String recipientName;
    private String address;
    private Date deliveryDate;
    private Date arrivalDate;
    private String recipientPhone;
    private String recipientEmail;
    private String messengerName;
    private Integer messengerId;


}
