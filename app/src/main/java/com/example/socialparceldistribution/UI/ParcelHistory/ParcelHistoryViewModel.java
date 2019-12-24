package com.example.socialparceldistribution.UI.ParcelHistory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.socialparceldistribution.Entities.Parcel;

import java.util.List;

public class ParcelHistoryViewModel extends AndroidViewModel {


    public ParcelHistoryViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<Parcel>> getMutableLiveData() {
        return mutableLiveData;
    }



    MutableLiveData<List<Parcel>> mutableLiveData;





}
