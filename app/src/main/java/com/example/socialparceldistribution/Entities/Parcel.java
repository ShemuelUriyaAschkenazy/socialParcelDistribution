package com.example.socialparceldistribution.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.google.firebase.database.Exclude;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
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
    private String parcelId = "id";
    @TypeConverters(ParcelType.class)
    private ParcelType parcelType;
    @TypeConverters(ParcelStatus.class)
    //TODO להסיר את הignore
    @Ignore
    private ParcelStatus parcelStatus;
    private Boolean isFragile;


    private Double weight;

    public UserLocation getWarehouseUserLocation() {
        return warehouseUserLocation;
    }

    public void setWarehouseUserLocation(UserLocation warehouseUserLocation) {
        this.warehouseUserLocation = warehouseUserLocation;
    }

    @TypeConverters(UserLocationConverter.class)
    private UserLocation warehouseUserLocation;


    private String recipientName;


    public String getRecipientAddress() {
        return recipientAddress;
    }

    public void setRecipientAddress(String recipientAddress) {
        this.recipientAddress = recipientAddress;
    }

    public UserLocation getRecipientUserLocation() {
        return recipientUserLocation;
    }

    public void setRecipientUserLocation(UserLocation recipientUserLocation) {
        this.recipientUserLocation = recipientUserLocation;
    }

    public String getWarehouseAddress() {
        return warehouseAddress;
    }

    public void setWarehouseAddress(String warehouseAddress) {
        this.warehouseAddress = warehouseAddress;
    }

    private String warehouseAddress;
    private String recipientAddress;
    private UserLocation recipientUserLocation;

    public Parcel(ParcelType parcelType, ParcelStatus parcelStatus, Boolean isFragile, Double weight, UserLocation warehouseUserLocation, String recipientName, String warehouseAddress, String recipientAddress, UserLocation recipientUserLocation, Date deliveryDate, Date arrivalDate, String recipientPhone, String recipientEmail, HashMap<Person, Boolean> messengers) {
        this.parcelType = parcelType;
        this.parcelStatus = parcelStatus;
        this.isFragile = isFragile;
        this.weight = weight;
        this.warehouseUserLocation = warehouseUserLocation;
        this.recipientName = recipientName;
        this.warehouseAddress = warehouseAddress;
        this.recipientAddress = recipientAddress;
        this.recipientUserLocation = recipientUserLocation;
        this.deliveryDate = deliveryDate;
        this.arrivalDate = arrivalDate;
        this.recipientPhone = recipientPhone;
        this.recipientEmail = recipientEmail;
        this.messengers = messengers;
    }

    @TypeConverters(DateConverter.class)
    private Date deliveryDate;
    @TypeConverters(DateConverter.class)
    private Date arrivalDate;
    private String recipientPhone;
    private String recipientEmail;

    public HashMap<Person, Boolean> getMessengers() {
        return messengers;
    }

    public void setMessengers(HashMap<Person, Boolean> messengers) {
        this.messengers = messengers;
    }

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

    public static class MessengersConverter {
        @TypeConverter
        public HashMap<Person,Boolean> fromString(String value) {
            if (value==null||value.isEmpty())
                return null;
            String[] mapString = value.split(","); //split map into array of (person,boolean) strings
            HashMap<Person,Boolean>  hashMap= new HashMap<>();
            for (String s1:mapString) //for all (person,boolean) in the map string
            {
                if(!s1.isEmpty()){//is empty maybe will needed because the last char in the string is ","
                String[] s2= s1.split(":"); //split (person,boolean) to person string and boolean string.
                String[] personString= s2[0].split(" "); //split person string into its 3 fields: name,id,email.
                Person person= new Person(personString[0],personString[1],personString[2]);
                Boolean b= Boolean.parseBoolean(s2[1]);
                hashMap.put(person,b);}
            }
            return hashMap;
        }

        @TypeConverter
        public String asString(HashMap<Person,Boolean> map) {
            if (map==null)
                return null;
            StringBuilder mapString= new StringBuilder();
            for (Map.Entry<Person,Boolean> entry:map.entrySet())
            {
                mapString.append(entry.getKey().toString()+":"+(Boolean.toString(entry.getValue()))+",");
            }
            return mapString.toString();
        }



    }

    public static class UserLocationConverter {
        @TypeConverter
        public UserLocation fromString(String value) {
            if (value==null||value.equals(""))
                return null;
            Double lat= Double.parseDouble(value.split(" ")[0]);
            Double lang = Double.parseDouble(value.split(" ")[1]);
            return new UserLocation(lat, lang);
        }

        @TypeConverter
        public String asString(UserLocation warehouseUserLocation) {
            return warehouseUserLocation==null? "":warehouseUserLocation.getLat() + " " + warehouseUserLocation.getLang();
        }



    }



}
