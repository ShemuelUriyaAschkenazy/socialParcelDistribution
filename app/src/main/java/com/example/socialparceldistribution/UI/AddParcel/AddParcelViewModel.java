package com.example.socialparceldistribution.UI.AddParcel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.socialparceldistribution.Data.ParcelRepository;
import com.example.socialparceldistribution.Entities.Parcel;

public class AddParcelViewModel extends AndroidViewModel {
    ParcelRepository parcelRepository;
    MutableLiveData<Boolean> booleanMutableLiveData;

    public AddParcelViewModel(@NonNull Application application) {
        super(application);
        parcelRepository = ParcelRepository.getInstance();
        booleanMutableLiveData = new MutableLiveData<>();
        //booleanMutableLiveData.postValue(true);
    }


    public void addParcel(Parcel parcel){
        parcelRepository.addParcel();
    }

    
}
