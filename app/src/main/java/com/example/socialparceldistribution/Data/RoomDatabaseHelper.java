package com.example.socialparceldistribution.Data;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.socialparceldistribution.Entities.Parcel;

import java.util.List;

public class RoomDatabaseHelper {
    private ParcelDao parcelDao;

    public RoomDatabaseHelper(Context context){
        HistoryDataSource database=HistoryDataSource.getInstance(context);
        parcelDao =database.getParcelDao();
    }

    public LiveData<List<Parcel>> getParcels(){
        return parcelDao.getAll();
    }

    public LiveData<Parcel> getParcel(String id){
        return parcelDao.get(id);
    }

    public void addParcel(Parcel p) {
        parcelDao.insert(p);
    }

    public void addParcels(List<Parcel> parcelList) {
        parcelDao.insert(parcelList);
    }

    public void editParcel(Parcel p) {
        parcelDao.update(p);
    }

    public void deleteParcel(Parcel p){
        parcelDao.delete(p);
    }

    public void clearTable(){parcelDao.clear();}
}
