package com.example.socialparceldistribution.UI.ParcelHistory;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.socialparceldistribution.Data.ParcelRepository;
import com.example.socialparceldistribution.Data.RoomDatabaseHelper;
import com.example.socialparceldistribution.Entities.Parcel;
import com.example.socialparceldistribution.UI.AddParcel.AddParcelActivity;

import java.util.Date;
import java.util.List;

public class ParcelHistoryViewModel extends AndroidViewModel {

    private LiveData<List<Parcel>> parcels;
    private ParcelRepository database;


    public ParcelHistoryViewModel(@NonNull Application application) {
        super(application);
      database=ParcelRepository.getInstance(application);
    }

    public LiveData<List<Parcel>> getParcels() {
        return database.getParcels();
    }
}
