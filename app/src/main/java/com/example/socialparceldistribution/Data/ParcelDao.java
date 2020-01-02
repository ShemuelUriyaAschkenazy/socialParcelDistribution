package com.example.socialparceldistribution.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.socialparceldistribution.Entities.Parcel;

import java.util.List;

@Dao
public interface ParcelDao {

    @Query("select * from parcel")
    LiveData<List<Parcel>> getAll();

    @Query("select * from parcel where parcelId=:id")
    LiveData<Parcel> get(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Parcel parcel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Parcel> parcels);

    @Update
    void update(Parcel parcel);

    @Delete
    void delete(Parcel... parcels);
}
