package com.example.socialparceldistribution.UI.ParcelHistory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.socialparceldistribution.Data.ParcelRepository;
import com.example.socialparceldistribution.Entities.Parcel;

import java.util.List;

public class ParcelHistoryViewModel extends AndroidViewModel {

    private ParcelRepository parcelRepository;
    //private LiveData<List<Parcel>> mutableLiveData;
    List<Parcel> parcelList;

    public List<Parcel> getParcelList() {
        return parcelList;
    }

    public void setParcelList(List<Parcel> parcelList) {
        this.parcelList = parcelList;
    }

    public ParcelHistoryViewModel(@NonNull Application application) {
        super(application);

        parcelRepository = ParcelRepository.getInstance();
        //mutableLiveData = new MutableLiveData<>();

//        ParcelRepository.changedListener changedListener = new ParcelRepository.changedListener() {
//            @Override
//            public void change() {
//                setParcelList(parcelList = parcelRepository.getRoomParcelsList());
//                mutableLiveData.setValue(parcelList);
//
//            }
//        };
//        parcelRepository.setChangedListener(changedListener);

//        parcelRepository.getParcels().observe(, new Observer<List<Parcel>>() {
//            @Override
//            public void onChanged(List<Parcel> parcels) {
//
//            }
//        });


        //parcelRepository.getParcels().observe( this,);
        //mutableLiveData.postValue(true);

    }

    LiveData<List<Parcel>> getMutableLiveData() {
        return parcelRepository.getParcels();
    }


}
