package com.example.socialparceldistribution.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity
public class Parcel {


    public Parcel() {
    }

    public Parcel(ParcelType parcelType, ParcelStatus parcelStatus, Boolean isFragile, Double weight, UserLocation location, String recipientName, String address, Date deliveryDate, Date arrivalDate, String recipientPhone, String recipientEmail, String messengerName, Integer messengerId) {
        this.parcelId = "aaa";
        this.parcelType = parcelType;
        this.parcelStatus = parcelStatus;
        this.isFragile = isFragile;
        this.weight = weight;
        this.userLocation = location;
        this.recipientName = recipientName;
        this.address = address;
        this.deliveryDate = deliveryDate;
        this.arrivalDate = arrivalDate;
        this.recipientPhone = recipientPhone;
        this.recipientEmail = recipientEmail;
        this.messengerName = messengerName;
        this.messengerId = messengerId;
    }

    @NonNull
    public String getParcelId() {
        return parcelId;
    }

    @NonNull
    public void setParcelId(String parcelId) {
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

    public UserLocation getLocation() {
        return userLocation;
    }

    public void setLocation(UserLocation location) {
        this.userLocation = location;
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

    public UserLocation getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(UserLocation userLocation) {
        this.userLocation = userLocation;
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

    public enum ParcelType {
        envelope(0), smallPackage(1), bigPackage(2);
        private final Integer code;

        ParcelType(Integer value) {
            this.code = value;
        }

        public Integer getCode() {
            return code;
        }

        @TypeConverter
        public static ParcelType getType(Integer numeral) {
            for (ParcelType ds : values()) {
                if (ds.code == numeral) {
                    return ds;
                }
            }
            return null;
        }

        @TypeConverter
        public static Integer getTypeInt(ParcelType parcelType) {
            if (parcelType != null)
                return parcelType.code;
            return null;
        }
    }

    public enum ParcelStatus {
        registered(0), pickUpSuggested(1), onTheWay(2), successfullyArrived(3);
        private final Integer code;

        ParcelStatus(Integer value) {
            this.code = value;
        }

        public Integer getCode() {
            return code;
        }

        @TypeConverter
        public static ParcelStatus getStatus(Integer numeral) {
            for (ParcelStatus ds : values()) {
                if (ds.code == numeral) {
                    return ds;
                }
            }
            return null;
        }
    }

    public ParcelStatus getParcelStatus() {
        return parcelStatus;
    }

    public void setParcelStatus(ParcelStatus parcelStatus) {
        this.parcelStatus = parcelStatus;
    }

    @NonNull
    @PrimaryKey
    private String parcelId = "aaa";
    @TypeConverters(ParcelType.class)
    private ParcelType parcelType;
    @TypeConverters(ParcelStatus.class)
    @Ignore
    private ParcelStatus parcelStatus;
    private Boolean isFragile;
    private Double weight;
    @TypeConverters(LocationConverter.class)
    private UserLocation userLocation;
    private String recipientName;
    private String address;
    @TypeConverters(DateConverter.class)
    private Date deliveryDate;
    @TypeConverters(DateConverter.class)
    private Date arrivalDate;
    private String recipientPhone;
    private String recipientEmail;
    private String messengerName;
    private Integer messengerId;

    public static class DateConverter {
        @TypeConverter
        public Date fromTimestamp(Long value) {
            return value == null ? null : new Date(value);
        }

        @TypeConverter
        public Long dateToTimestamp(Date date) {
            return date == null ? null : date.getTime();
        }
    }

    public static class LocationConverter {
        @TypeConverter
        public UserLocation fromString(String value) {
            if (value==null|value.isEmpty())
                return null;
            Double lat= Double.parseDouble(value.split(" ")[0]);
            Double lang = Double.parseDouble(value.split(" ")[1]);
            return new UserLocation(lat,lang);


        }

        @TypeConverter
        public String asString(UserLocation userLocation) {
            return userLocation==null? "":userLocation.getLat() + " " + userLocation.getLang();
        }


    }



}
