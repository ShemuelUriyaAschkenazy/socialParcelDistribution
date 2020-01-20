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
import java.util.ArrayList;
import java.util.List;

public class HistoryParcelsActivity extends AppCompatActivity {

    private List<Parcel> parcelList=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_parcels);
        final RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        HistoryParcelsAdapter historyParcelsAdapter = new HistoryParcelsAdapter(parcelList);
        recyclerView.setAdapter(historyParcelsAdapter);

        ParcelHistoryViewModel viewModel= ViewModelProviders.of(this).get(ParcelHistoryViewModel.class);
        viewModel.getParcels().observe(this, new Observer<List<Parcel>>() {
            @Override
            public void onChanged(List<Parcel> parcels) {
                parcelList=parcels;
                recyclerView.setLayoutManager(new LinearLayoutManager(HistoryParcelsActivity.this));
                HistoryParcelsAdapter historyParcelsAdapter = new HistoryParcelsAdapter(parcelList);
                recyclerView.setAdapter(historyParcelsAdapter);
            }
        });
    }
}
