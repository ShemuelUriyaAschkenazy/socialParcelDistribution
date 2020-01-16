package com.example.socialparceldistribution.Data;
import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.socialparceldistribution.Entities.Parcel;

import java.util.List;

public class ParcelRepository {

    MutableLiveData<List<Parcel>> mutableLiveData= new MutableLiveData<>();
    ParcelDataSource parcelDataSource;
    RoomDatabaseHelper databaseHelper;

    private ParcelRepository(Application application){
        parcelDataSource = ParcelDataSource.getInstance();
        databaseHelper= new RoomDatabaseHelper(application.getApplicationContext());
        ParcelDataSource.changedListener changedListener= new ParcelDataSource.changedListener() {
            @Override
            public void change() {
                List<Parcel> parcelList=parcelDataSource.getParcelsList();
                mutableLiveData.setValue(parcelDataSource.getParcelsList());
                databaseHelper.clearTable();
                databaseHelper.addParcels(parcelList);

            }
        };
        parcelDataSource.setChangedListener(changedListener);
    }

    private static ParcelRepository instance;
    public static ParcelRepository getInstance(Application application) {
        if (instance == null)
            instance = new ParcelRepository(application);
        return instance;
    }

//    public MutableLiveData<Boolean> getStatus(){
//        //todo
//        return new MutableLiveData<Boolean>();
//    }


    public void addParcel(Parcel parcel) {
        parcelDataSource.addParcel(parcel);
    }

    public LiveData<List<Parcel>> getParcels() {
        return databaseHelper.getParcels();
    }

    public LiveData<Boolean> getIsSuccess(){
        return parcelDataSource.getIsSuccess();
    }
}
