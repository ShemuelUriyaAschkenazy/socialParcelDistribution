package com.example.socialparceldistribution.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Parcel {

    @TypeConverters(DateConverter.class)
    private Date deliveryDate;
    @TypeConverters(DateConverter.class)
    private Date arrivalDate;
    private String recipientPhone;
    private String recipientEmail;
    @NonNull
    @PrimaryKey
    private String parcelId = "id";
    @TypeConverters(ParcelType.class)
    private ParcelType parcelType;
    @TypeConverters(ParcelStatus.class)
    private ParcelStatus parcelStatus;
    private Boolean isFragile;
    private Double weight;
    private String warehouseAddress;
    private String recipientAddress;
    private UserLocation recipientUserLocation;
    private HashMap<String, Boolean> messengers;
    @TypeConverters(UserLocationConverter.class)
    private UserLocation warehouseUserLocation;
    private String recipientName;

    public Parcel() {
    }

    public Parcel(ParcelType parcelType, ParcelStatus parcelStatus, Boolean isFragile, Double weight, UserLocation warehouseUserLocation, String recipientName, String warehouseAddress, String recipientAddress, UserLocation recipientUserLocation, Date deliveryDate, Date arrivalDate, String recipientPhone, String recipientEmail, HashMap<String, Boolean> messengers) {
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
            for (ParcelType ds : values())
                if (ds.code.equals(numeral))
                    return ds;
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
            for (ParcelStatus ds : values())
                if (ds.code == numeral)
                    return ds;
            return null;
        }
        @TypeConverter
        public static Integer getTypeInt(ParcelStatus parcelStatus) {
            if (parcelStatus != null)
                return parcelStatus.code;
            return null;
        }
    }

    //region converters
    public static class DateConverter {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        @TypeConverter
        public Date fromTimestamp(String date) throws ParseException {
            return (date == null ? null : format.parse(date));
        }

        @TypeConverter
        public String dateToTimestamp(Date date) {
            return date == null ? null : format.format(date);
        }
    }

    public static class MessengersConverter {
        @TypeConverter
        public HashMap<String, Boolean> fromString(String value) {
            if (value == null || value.isEmpty())
                return null;
            String[] mapString = value.split(","); //split map into array of (string,boolean) strings
            HashMap<String, Boolean> hashMap = new HashMap<>();
            for (String s1 : mapString) //for all (string,boolean) in the map string
            {
                if (!s1.isEmpty()) {//is empty maybe will needed because the last char in the string is ","
                    String[] s2 = s1.split(":"); //split (string,boolean) to person string and boolean string.
                    Boolean aBoolean = Boolean.parseBoolean(s2[1]);
                    hashMap.put(/*person string:*/s2[0], aBoolean);
                }
            }
            return hashMap;
        }

        @TypeConverter
        public String asString(HashMap<String, Boolean> map) {
            if (map == null)
                return null;
            StringBuilder mapString = new StringBuilder();
            for (Map.Entry<String, Boolean> entry : map.entrySet())
                mapString.append(entry.getKey()).append(":").append(entry.getValue()).append(",");
            return mapString.toString();
        }
    }

    public static class UserLocationConverter {
        @TypeConverter
        public UserLocation fromString(String value) {
            if (value == null || value.equals(""))
                return null;
            double lat = Double.parseDouble(value.split(" ")[0]);
            double lang = Double.parseDouble(value.split(" ")[1]);
            return new UserLocation(lat, lang);
        }

        @TypeConverter
        public String asString(UserLocation warehouseUserLocation) {
            return warehouseUserLocation == null ? "" : warehouseUserLocation.getLat() + " " + warehouseUserLocation.getLon();
        }
    }
    //endregion
//region getters/setters
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

    public ParcelStatus getParcelStatus() {
        return parcelStatus;
    }

    public void setParcelStatus(ParcelStatus parcelStatus) {
        this.parcelStatus = parcelStatus;
    }

    public UserLocation getWarehouseUserLocation() {
        return warehouseUserLocation;
    }

    public void setWarehouseUserLocation(UserLocation warehouseUserLocation) {
        this.warehouseUserLocation = warehouseUserLocation;
    }

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

    public HashMap<String, Boolean> getMessengers() {
        return messengers;
    }

    public void setMessengers(HashMap<String, Boolean> messengers) {
        this.messengers = messengers;
    }
//endregion
}
