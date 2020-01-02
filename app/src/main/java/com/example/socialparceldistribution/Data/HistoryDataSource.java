package com.example.socialparceldistribution.Data;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.socialparceldistribution.Entities.Parcel;

public abstract class HistoryDataSource extends RoomDatabase {

    public static final String DATABASE_NAME="database.db";
    public static HistoryDataSource database;

    public static HistoryDataSource getInstance(Context context){
        if (database==null)
            database= Room.databaseBuilder(context,HistoryDataSource.class,DATABASE_NAME).allowMainThreadQueries().build();
        return database;
    }

    public abstract ParcelDao getParcelDao();
}
