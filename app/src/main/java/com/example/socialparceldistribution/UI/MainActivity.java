package com.example.socialparceldistribution.UI;
import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;

import com.example.socialparceldistribution.Data.ParcelDataSource;
import com.example.socialparceldistribution.R;
import com.example.socialparceldistribution.UI.AddParcel.AddParcelActivity;
import com.example.socialparceldistribution.UI.ParcelHistory.HistoryParcelsActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addParcel= findViewById(R.id.btn_addParcelActivity);
        addParcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(MainActivity.this, AddParcelActivity.class);
                startActivity(intent);

            }
        });

        Button history= findViewById(R.id.btn_parcelHistory);
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(MainActivity.this, HistoryParcelsActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
