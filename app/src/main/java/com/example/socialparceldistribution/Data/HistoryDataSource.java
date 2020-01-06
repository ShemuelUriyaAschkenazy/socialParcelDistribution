package com.example.socialparceldistribution.Data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.socialparceldistribution.Entities.Parcel;

@Database(entities = Parcel.class, version = 1, exportSchema = false)
@TypeConverters({Parcel.ParcelStatus.class, Parcel.ParcelType.class, Parcel.DateConverter.class, Parcel.LocationConverter.class})
public abstract class HistoryDataSource extends RoomDatabase {

    public static final String DATABASE_NAME="database.db";
    private static HistoryDataSource database;

    public static HistoryDataSource getInstance(Context context){
        if (database==null)
            database= Room.databaseBuilder(context,HistoryDataSource.class,DATABASE_NAME).allowMainThreadQueries().build();
        return database;
    }

    public abstract ParcelDao getParcelDao();
}
