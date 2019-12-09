package com.example.socialparceldistribution.UI.AddParcel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.example.socialparceldistribution.R;

public class AddParcelActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_parcel_layout);

        final LinearLayout messengerDetailsLO = findViewById(R.id.lo_messenger_details);
        messengerDetailsLO.setVisibility(View.GONE);

        Switch pickUpSuggested = findViewById(R.id.swPickUp);
        pickUpSuggested.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    messengerDetailsLO.setVisibility(View.VISIBLE);
                else
                    messengerDetailsLO.setVisibility(View.GONE);
            }
        });
    }
}
