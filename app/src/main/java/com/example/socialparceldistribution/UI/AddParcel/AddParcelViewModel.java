package com.example.socialparceldistribution.UI.AddParcel;

import android.app.Application;
import android.location.Location;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.socialparceldistribution.Data.ParcelRepository;
import com.example.socialparceldistribution.Entities.Parcel;

import java.util.Date;

public class AddParcelViewModel extends AndroidViewModel {
    ParcelRepository parcelRepository;
    MutableLiveData<Boolean> booleanMutableLiveData;
    RadioGroup radioGroup_type, radioGroup_fragility;
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
        booleanMutableLiveData = new MutableLiveData<>();
        //booleanMutableLiveData.postValue(true);
    }


    public void addParcel(Parcel parcel){
        parcelRepository.addParcel(getApplication().getApplicationContext(),parcel);
    }

    
}
