package com.example.socialparceldistribution.Data;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import com.example.socialparceldistribution.Entities.Parcel;
import com.example.socialparceldistribution.UI.MainActivity;

import java.util.List;

public class ParcelRepository {

    MutableLiveData<List<Parcel>> mutableLiveData= new MutableLiveData<>();
    ParcelDataSource parcelDataSource;

    private ParcelRepository(){
        parcelDataSource = ParcelDataSource.getInstance();
        ParcelDataSource.changedListener changedListener= new ParcelDataSource.changedListener() {
            @Override
            public void change() {
                List<Parcel> parcelList=parcelDataSource.getParcelsList();
                mutableLiveData.setValue(parcelDataSource.getParcelsList());

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


    public void addParcel(Context context, Parcel parcel) {
        parcelDataSource.addParcel(context,parcel);
    }
}
