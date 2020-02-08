package com.example.socialparceldistribution.UI;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.socialparceldistribution.R;
import com.example.socialparceldistribution.UI.AddParcel.AddParcelActivity;
import com.example.socialparceldistribution.UI.ParcelHistory.HistoryParcelsActivity;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addParcel= findViewById(R.id.btn_addParcelActivity);
        addParcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ParcelDataSource parcelDataSource =new ParcelDataSource();
                //parcelDataSource.addParcel(MainActivity.this);

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

        ImageView imageView = findViewById(R.id.parcels_animation);
        AnimationDrawable animationDrawable = (AnimationDrawable)imageView.getDrawable();
        animationDrawable.start();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
