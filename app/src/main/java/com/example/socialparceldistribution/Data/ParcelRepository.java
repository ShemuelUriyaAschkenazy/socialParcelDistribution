package com.example.socialparceldistribution.Data;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.socialparceldistribution.Entities.Parcel;
import com.example.socialparceldistribution.UI.MainActivity;

import java.util.List;

public class ParcelRepository {

    LiveData<List<Parcel>> mutableLiveData= new MutableLiveData<>();
    ParcelDataSource parcelDataSource;
    HistoryDataSource historyDataSource;
    List<Parcel> parcelsList;
    List<Parcel> roomParcelsList;

    public void setParcelsList(List<Parcel> parcelsList) {
        this.parcelsList = parcelsList;
    }

    public void setRoomParcelsList(List<Parcel> roomParcelsList) {
        this.roomParcelsList = roomParcelsList;
    }

    public List<Parcel> getRoomParcelsList() {
        return roomParcelsList;
    }

    public List<Parcel> getParcelsList() {
        //todo
        return roomParcelsList;

        //return parcelsList;
    }
    public interface changedListener {
        void change();
    }
    changedListener listener;
    public void setChangedListener(changedListener l) {
        listener = l;
    }

    private ParcelRepository(){
        parcelDataSource = ParcelDataSource.getInstance();
//        ParcelDataSource.changedListener changedListener= new ParcelDataSource.changedListener() {
//            @Override
//            public void change() {
//                setParcelsList(parcelDataSource.getParcelsList());
//                mutableLiveData.setValue(parcelDataSource.getParcelsList());
//
//            }
//        };
//        parcelDataSource.setChangedListener(changedListener);

        historyDataSource = HistoryDataSource.getInstance();
//        HistoryDataSource.changedListener changedHistoryListener= new HistoryDataSource.changedListener() {
//            @Override
//            public void change() {
//                setRoomParcelsList(historyDataSource.getParcelsList());
//                mutableLiveData.setValue(historyDataSource.getParcelsList());
//
//            }
//        };
//        historyDataSource.setChangedListener(changedHistoryListener);

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

    public LiveData<List<Parcel>> getParcels() {
        return new MutableLiveData<>(parcelDataSource.getParcelsList()) ;
        //return mutableLiveData;
    }
}
