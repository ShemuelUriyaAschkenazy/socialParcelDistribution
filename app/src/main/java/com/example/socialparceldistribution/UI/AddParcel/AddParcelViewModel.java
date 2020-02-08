package com.example.socialparceldistribution.UI.AddParcel;

import android.app.Application;
import android.location.Location;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.socialparceldistribution.Data.ParcelRepository;
import com.example.socialparceldistribution.Entities.Parcel;
import com.example.socialparceldistribution.Entities.UserLocation;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddParcelViewModel extends AndroidViewModel {
    private ParcelRepository parcelRepository;

    public AddParcelViewModel(@NonNull Application application) {
        super(application);
        parcelRepository = ParcelRepository.getInstance(application);
    }

    LiveData<Boolean> getIsSuccess() {
        return parcelRepository.getIsSuccess();
    }

    public void addParcel(int typeSpinnerPosition, int isFragileSpinnerPosition, Double weight, Location warehouseLocation, String recipientName, String warehouseAddress, String recipientAddress, Location recipientLocation, String recipientPhone, String recipientEmail) {

        Parcel.ParcelType parcelType;
        boolean isFragile = false;

        UserLocation warehouseUserLocation = new UserLocation();
        warehouseUserLocation = warehouseUserLocation.convertFromLocation(warehouseLocation);

        UserLocation recipientUserLocation = new UserLocation();
        recipientUserLocation = recipientUserLocation.convertFromLocation(recipientLocation);

        switch (typeSpinnerPosition) {
            case 0:
                parcelType = Parcel.ParcelType.envelope;
                break;
            case 1:
                parcelType = Parcel.ParcelType.bigPackage;
                break;
            case 2:
                parcelType = Parcel.ParcelType.smallPackage;
                break;
            default:
                parcelType = null;
        }

        switch (isFragileSpinnerPosition) {
            case 0:
                isFragile = true;
                break;
            case 1:
                isFragile = false;
                break;
        }

        Parcel parcel = new Parcel(
                parcelType,
                Parcel.ParcelStatus.registered,
                isFragile,
                weight,
                warehouseUserLocation,
                recipientName,
                warehouseAddress,
                recipientAddress,
                recipientUserLocation,
                new Date(),
                null,
                recipientPhone, recipientEmail,
                new HashMap<String, Boolean>());

        parcelRepository.addParcel(parcel);
    }

    boolean isValidEmail(String recipientEmail) {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(recipientEmail);
        if (!matcher.matches())
            return false;
        return true;
    }
}

