package com.example.socialparceldistribution.Data;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.socialparceldistribution.Entities.Parcel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ParcelDataSource {


    private MutableLiveData<Boolean> isSuccess= new MutableLiveData<>();
    public MutableLiveData<Boolean> getIsSuccess() {
        return isSuccess;
    }


    public interface changedListener {
        void change();
    }

    private changedListener listener;

    public void setChangedListener(changedListener l) {
        listener = l;
    }


    public List<Parcel> getParcelsList() {
        return parcelsList;
    }

    List<Parcel> parcelsList;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference parcels = firebaseDatabase.getReference("ExistingParcels");

    private ParcelDataSource() {
        parcelsList = new ArrayList<>();
        parcels.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                parcelsList.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren())
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            Parcel parcel = snapshot1.getValue(Parcel.class);
                            parcelsList.add(parcel);
                        }
                }
                if (listener != null)
                    listener.change();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private static ParcelDataSource instance;

    public static ParcelDataSource getInstance() {
        if (instance == null)
            instance = new ParcelDataSource();
        return instance;
    }


    public void addParcel(Parcel p) {
        String id = parcels.push().getKey();
        p.setParcelId(id);
        parcels.child(p.getRecipientPhone()).child(id).setValue(p).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                isSuccess.setValue(true);
                isSuccess.setValue(null);
            }


        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                isSuccess.setValue(false);
                isSuccess.setValue(null);
            }
        });
    }
}



