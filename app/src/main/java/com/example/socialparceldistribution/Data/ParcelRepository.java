package com.example.socialparceldistribution.Data;

import androidx.lifecycle.MutableLiveData;

import com.example.socialparceldistribution.Entities.Parcel;

import java.util.List;

public class ParcelRepository {

    MutableLiveData<List<Parcel>> mutableLiveData;
    ParcelDataSource parcelDataSource= new ParcelDataSource();

    private ParcelRepository(){
        ParcelDataSource.changedListener changedListener= new ParcelDataSource.changedListener() {
            @Override
            public void change() {
                //todo(parcelDataSource.getParcelsList());
            }
        };
        parcelDataSource.setChangedListener(changedListener);



    }

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
