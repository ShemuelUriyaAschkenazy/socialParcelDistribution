package com.example.socialparceldistribution.Data;

import androidx.lifecycle.MutableLiveData;

public class ParcelRepository {

    ParcelDataSource parcelDataSource= new ParcelDataSource();

    private ParcelRepository(){
        ParcelDataSource.changedListener changedListener= new ParcelDataSource.changedListener() {
            @Override
            public void change() {
                //TODO parcelDataSource.getParcelsList();
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
