package com.example.socialparceldistribution.Data;

import androidx.lifecycle.MutableLiveData;

public class ParcelRepository {
    ParcelDataSource parcelDataSource;
    private ParcelRepository(){}

    private static ParcelRepository instance;
    public static ParcelRepository getInstance() {
        if (instance == null)
            instance = new ParcelRepository();
        return instance;
    }

    public MutableLiveData<Boolean> getStatus(){
        //todo
        return new MutableLiveData<Boolean>();
    }
}
