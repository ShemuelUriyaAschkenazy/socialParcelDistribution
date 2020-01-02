package com.example.socialparceldistribution.Data;

import android.content.Context;
import android.net.sip.SipSession;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.socialparceldistribution.Entities.Parcel;
import com.example.socialparceldistribution.UI.MainActivity;
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

    // MutableLiveData<Boolean> booleanMutableLiveData;


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

    // FirebaseDatabase firebaseDatabase =  FirebaseDatabase.getInstance();
    // DatabaseReference warehouses = firebaseDatabase.getReference("warehouses");
    List<Parcel> parcelsList;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference parcels = firebaseDatabase.getReference("parcels");

    private ParcelDataSource() {
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
    private static ParcelDataSource instance;
    public static ParcelDataSource getInstance() {
        if (instance == null)
            instance = new ParcelDataSource();
        return instance;
    }


    public boolean addParcel(final Context context,Parcel p) {

        parcels.child(p.getParcelId()+"").setValue(p).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "success", Toast.LENGTH_LONG).show();

            }


        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "failure", Toast.LENGTH_LONG).show();

            }
        });
        return false;//todo
    }
}



