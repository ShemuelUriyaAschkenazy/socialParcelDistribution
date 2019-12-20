package com.example.socialparceldistribution.Entities;

import android.location.Address;
import android.location.Location;

import java.util.Date;

public class Parcel {

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
