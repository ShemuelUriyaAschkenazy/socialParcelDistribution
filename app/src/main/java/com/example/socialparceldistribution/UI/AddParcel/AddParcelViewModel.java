package com.example.socialparceldistribution.UI.AddParcel;

import android.app.Application;
import android.location.Location;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.socialparceldistribution.Data.ParcelRepository;
import com.example.socialparceldistribution.Entities.Parcel;
import java.util.Date;

public class AddParcelViewModel extends AndroidViewModel {
    private ParcelRepository parcelRepository;
    EditText etWeight, etLocation, etRecipient_name, etRecipient_phone, etRecipient_address, etRecipient_email,
            etMessenger_name, etMessenger_id;

    private int parcelId;
    private Parcel.ParcelType parcelType;
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

    public AddParcelViewModel(@NonNull Application application) {
        super(application);
        parcelRepository = ParcelRepository.getInstance(application);
    }

    void addParcel(Parcel parcel){
        parcelRepository.addParcel(parcel);
    }

    LiveData<Boolean> getIsSuccess(){
        return parcelRepository.getIsSuccess();
    }
}
