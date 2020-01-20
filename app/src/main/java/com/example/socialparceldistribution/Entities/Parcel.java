package com.example.socialparceldistribution.Entities;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.google.firebase.database.Exclude;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@Entity
public class Parcel {


    public Parcel() {
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


    public UserLocation getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(UserLocation userLocation) {
        this.userLocation = userLocation;
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

    @Exclude
    public HashMap<Person, Boolean> getMessengers() {
        return messengers;
    }

    @Exclude
    public void setMessengers(HashMap<Person, Boolean> messengers) {
        this.messengers = messengers;
    }

    public ParcelStatus getParcelStatus() {
        return parcelStatus;
    }

    public void setParcelStatus(ParcelStatus parcelStatus) {
        this.parcelStatus = parcelStatus;
    }


    @NonNull
    @PrimaryKey
    private String parcelId = "id";
    @TypeConverters(ParcelType.class)
    private ParcelType parcelType;
    @TypeConverters(ParcelStatus.class)
    @Ignore
    private ParcelStatus parcelStatus;
    private Boolean isFragile;
    private Double weight;
    @TypeConverters(LocationConverter.class)
    private UserLocation userLocation;

    public Parcel(ParcelType parcelType, ParcelStatus parcelStatus, Boolean isFragile, Double weight, UserLocation userLocation, String recipientName, String address, Date deliveryDate, Date arrivalDate, String recipientPhone, String recipientEmail) {
        this.parcelType = parcelType;
        this.parcelStatus = parcelStatus;
        this.isFragile = isFragile;
        this.weight = weight;
        this.userLocation = userLocation;
        this.recipientName = recipientName;
        this.address = address;
        this.deliveryDate = deliveryDate;
        this.arrivalDate = arrivalDate;
        this.recipientPhone = recipientPhone;
        this.recipientEmail = recipientEmail;
        this.messengers = new HashMap<>();
    }

    private String recipientName;
    private String address;
    @TypeConverters(DateConverter.class)
    private Date deliveryDate;
    @TypeConverters(DateConverter.class)
    private Date arrivalDate;
    private String recipientPhone;
    private String recipientEmail;
    @Exclude
    @Ignore
    private HashMap<Person, Boolean> messengers;

    public static class DateConverter {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        @TypeConverter
        public Date fromTimestamp(String date) throws ParseException {
            return (date == null ? null : format.parse ( date ));
        }

        @TypeConverter
        public String dateToTimestamp(Date date) {
            return date == null ? null : format.format(date);
        }
    }

    public static class LocationConverter {
        @TypeConverter
        public UserLocation fromString(String value) {
            if (value==null||value.equals(""))
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
