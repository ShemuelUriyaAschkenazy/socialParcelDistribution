package com.example.socialparceldistribution.UI.ParcelHistory;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialparceldistribution.Entities.Parcel;
import com.example.socialparceldistribution.R;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class HistoryParcelsActivity extends AppCompatActivity {

    List<Parcel> parcelList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_parcels);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        parcelList = new ArrayList<>();
        Parcel parcel;
        for (int i = 0; i < 80; i++) {
            parcel = new Parcel();
            parcel.setParcelId(i);
            parcel.setDeliveryDate(new Date(i, 1, 1));
            parcelList.add(parcel);
        }
        HistoryParcelsAdapter historyParcelsAdapter = new HistoryParcelsAdapter(parcelList);
        recyclerView.setAdapter(historyParcelsAdapter);
    }


}
