package com.example.socialparceldistribution.UI.ParcelHistory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialparceldistribution.Entities.Parcel;
import com.example.socialparceldistribution.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class HistoryParcelsAdapter extends RecyclerView.Adapter<HistoryParcelsAdapter.HistoryParcelViewHolder> {

    private List<Parcel> parcels;
    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
    HistoryParcelsAdapter(List<Parcel> parcels) {
        this.parcels = parcels;
    }
    class HistoryParcelViewHolder extends RecyclerView.ViewHolder{

        TextView status;
        TextView recipientName;
        TextView parcelType;
        TextView address;
        TextView date;
        TextView messengerName;

        private HistoryParcelViewHolder(View itemView) {
            super(itemView);

            status=itemView.findViewById(R.id.status_tv);
            recipientName=itemView.findViewById(R.id.recipient_name_tv);
            parcelType=itemView.findViewById(R.id.type_tv);
            address=itemView.findViewById(R.id.address_tv);
            date=itemView.findViewById(R.id.date_tv);
            messengerName=itemView.findViewById(R.id.messenger_name_tv);
        }
    }
    @NonNull
    @Override
    public HistoryParcelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.parcel_cell, parent,false);
        return new HistoryParcelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryParcelViewHolder holder, int position) {
        Parcel parcel = parcels.get(position);
        //holder.messengerName.setText(parcel.getMessengers().isEmpty()?"no messenger": parcel.getMessengerName()+"");
        holder.date.setText(parcel.getDeliveryDate()==null? "no date":format.format(parcel.getDeliveryDate()));
        holder.address.setText(parcel.getRecipientAddress().isEmpty()?"no address":parcel.getRecipientAddress());
        holder.recipientName.setText(parcel.getRecipientName().isEmpty()?"no recipient name":parcel.getRecipientName());
        holder.parcelType.setText(parcel.getParcelType()==null?"no type":parcel.getParcelType().toString());
        holder.status.setText(parcel.getParcelStatus()==null?"no status":parcel.getParcelStatus().toString());
    }

    @Override
    public int getItemCount() {
        return parcels.size();
    }
}
