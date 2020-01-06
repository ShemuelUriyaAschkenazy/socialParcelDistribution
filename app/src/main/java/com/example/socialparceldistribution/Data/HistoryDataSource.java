package com.example.socialparceldistribution.Data;

import androidx.annotation.NonNull;

import com.example.socialparceldistribution.Entities.Parcel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HistoryDataSource {

    private static HistoryDataSource instance;
    public static HistoryDataSource getInstance() {
        if (instance == null)
            instance = new HistoryDataSource();
        return instance;
    }
    List<Parcel> parcelsList;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference parcels = firebaseDatabase.getReference("parcels");

    public HistoryDataSource() {
        parcelsList = new ArrayList<>();
        parcels.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                parcelsList.clear();

                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Parcel parcel = snapshot.getValue(Parcel.class);
                        parcelsList.add(parcel);
                    }
                    if (listener != null)
                        listener.change();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public interface changedListener {
        void change();
    }

    changedListener listener;

    public void setChangedListener(changedListener l) {
        listener = l;
    }


    public List<Parcel> getParcelsList() {
        return parcelsList;
    }


}
