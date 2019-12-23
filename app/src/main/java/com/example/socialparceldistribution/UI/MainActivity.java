package com.example.socialparceldistribution.UI;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.socialparceldistribution.Data.ParcelDataSource;
import com.example.socialparceldistribution.R;
import com.example.socialparceldistribution.UI.AddParcel.AddParcelActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addParcel= findViewById(R.id.btn_addParcelActivity);
        addParcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParcelDataSource parcelDataSource =new ParcelDataSource();
                parcelDataSource.addParcel(MainActivity.this);

                Intent intent= new Intent(MainActivity.this, AddParcelActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
