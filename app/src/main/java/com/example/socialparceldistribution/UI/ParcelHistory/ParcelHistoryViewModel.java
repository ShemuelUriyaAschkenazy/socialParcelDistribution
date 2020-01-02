package com.example.socialparceldistribution.UI.ParcelHistory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.socialparceldistribution.Data.ParcelRepository;
import com.example.socialparceldistribution.Entities.Parcel;

import java.util.List;

public class ParcelHistoryViewModel extends AndroidViewModel {

    ParcelRepository parcelRepository;
    MutableLiveData<List<Parcel>> booleanMutableLiveData;

    public ParcelHistoryViewModel(@NonNull Application application) {
        super(application);

            parcelRepository = ParcelRepository.getInstance();
            booleanMutableLiveData = new MutableLiveData<>();
            //booleanMutableLiveData.postValue(true);

    }

    public MutableLiveData<List<Parcel>> getMutableLiveData() {
        return mutableLiveData;
    }



    MutableLiveData<List<Parcel>> mutableLiveData;



}
