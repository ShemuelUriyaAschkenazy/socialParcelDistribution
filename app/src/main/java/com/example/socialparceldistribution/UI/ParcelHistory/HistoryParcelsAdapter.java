package com.example.socialparceldistribution.UI.ParcelHistory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialparceldistribution.Entities.Parcel;
import com.example.socialparceldistribution.R;

import java.util.List;

public class HistoryParcelsAdapter extends RecyclerView.Adapter<HistoryParcelsAdapter.HistoryParcelViewHolder> {

    private List<Parcel> parcels;
    public HistoryParcelsAdapter(List<Parcel> parcels) {
        this.parcels = parcels;
    }
    public class HistoryParcelViewHolder extends RecyclerView.ViewHolder{
        TextView parcelId;

        TextView deliveryDate;

        public HistoryParcelViewHolder(View itemView) {
            super(itemView);
            parcelId = itemView.findViewById(R.id.parcelId);
            deliveryDate = itemView.findViewById(R.id.date);
        }
    }
    @NonNull
    @Override
    public HistoryParcelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.parcel_cell, parent,false);
        HistoryParcelViewHolder historyParcelViewHolder = new HistoryParcelViewHolder(view);
        return historyParcelViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryParcelViewHolder holder, int position) {
        Parcel parcel = parcels.get(position);
        holder.parcelId.setText(parcel.getParcelId()+"");
        holder.deliveryDate.setText(parcel.getDeliveryDate().toString());
    }

    @Override
    public int getItemCount() {
        return parcels.size();
    }
}
