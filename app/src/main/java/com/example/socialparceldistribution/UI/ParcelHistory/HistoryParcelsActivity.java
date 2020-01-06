package com.example.socialparceldistribution.UI.ParcelHistory;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialparceldistribution.Entities.Parcel;
import com.example.socialparceldistribution.R;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class HistoryParcelsActivity extends AppCompatActivity {

//    List<Parcel> parcelList;
    ParcelHistoryViewModel parcelHistoryViewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_parcels);
        final RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        parcelHistoryViewModel = ViewModelProviders.of(this).get(ParcelHistoryViewModel.class);

//        parcelList = new ArrayList<>();
//        final HistoryParcelsAdapter historyParcelsAdapter = new HistoryParcelsAdapter(parcelList);
//        recyclerView.setAdapter(historyParcelsAdapter);
        Observer<List<Parcel>> observer = new Observer<List<Parcel>>() {

            @Override
            public void onChanged(List<Parcel> parcels) {
//                recyclerView.setAdapter(historyParcelsAdapter);
                recyclerView.setAdapter(new HistoryParcelsAdapter(parcels));

            }
        };
        parcelHistoryViewModel.getMutableLiveData().observe(this,observer);
//        Parcel parcel;
//        for (int i = 0; i < 80; i++) {
//            parcel = new Parcel();
//            parcel.setParcelId(i);
//            parcel.setDeliveryDate(new Date(i, 1, 1));
//            parcelList.add(parcel);
//        }



    }


}
