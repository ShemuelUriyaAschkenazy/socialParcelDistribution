package com.example.socialparceldistribution.UI.ParcelHistory;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialparceldistribution.Entities.Parcel;
import com.example.socialparceldistribution.R;

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

    }


}
