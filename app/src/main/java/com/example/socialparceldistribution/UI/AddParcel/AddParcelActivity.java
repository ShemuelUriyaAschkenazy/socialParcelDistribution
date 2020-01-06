package com.example.socialparceldistribution.UI.AddParcel;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.socialparceldistribution.Entities.Parcel;
import com.example.socialparceldistribution.R;

import java.time.Instant;
import java.util.Date;

public class AddParcelActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    AddParcelViewModel addParcelViewModel;
    RadioGroup radioGroup_type, radioGroup_fragility;

    Parcel parcel;
    RadioButton radioButton_envelope, radioButton_big, radioButton_small, radioButton_fragile, radioButton_noFragile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_parcel_layout);

        addParcelViewModel = ViewModelProviders.of(this).get(AddParcelViewModel.class);
        findViewById(R.id.btn_addParcel).setOnClickListener(this);
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

        radioGroup_type = findViewById(R.id.rg_parcelType);
        radioGroup_fragility = findViewById(R.id.rg_fragility);
//        radioGroup_type.setOnCheckedChangeListener(this);
//        radioGroup_fragility.setOnCheckedChangeListener(this);

        radioButton_envelope = findViewById(R.id.rb_envelope);
        radioButton_big = findViewById(R.id.rb_bigPackage);
        radioButton_small = findViewById(R.id.rb_smallPackage);
        radioButton_fragile = findViewById(R.id.rb_Fragile);
        radioButton_noFragile = findViewById(R.id.rb_notFragile);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group.getId() == R.id.rg_parcelType) {

        } else if (group.getId() == R.id.rg_fragility) {

        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        // Parcel.ParcelType parcelType;
        if (v.getId() == R.id.btn_addParcel) {
            Parcel.ParcelType parcelType;
            Boolean isFragile;
            switch (radioGroup_type.getCheckedRadioButtonId()) {
                case R.id.rb_envelope:
                    parcelType = Parcel.ParcelType.envelope;
                    break;
                case R.id.rb_bigPackage:
                    parcelType = Parcel.ParcelType.bigPackage;
                    break;
                case R.id.rb_smallPackage:
                    parcelType = Parcel.ParcelType.smallPackage;
                    break;
                default:
                    parcelType = null;

            }
            switch (radioGroup_fragility.getCheckedRadioButtonId()) {
                case R.id.rb_Fragile:
                    isFragile = true;
                    break;
                case R.id.rb_notFragile:
                    isFragile = false;
                    break;
                default:
                    isFragile = null;
            }
            parcel = new Parcel(
                    (System.currentTimeMillis())+"",
                    parcelType,
                    isFragile,
                    ((EditText) findViewById(R.id.et_weight)).getText().toString().equals("")? null:
                            Double.parseDouble(((EditText) findViewById(R.id.et_weight)).getText().toString()),
                    null,
                    ((EditText) findViewById(R.id.et_recipient_name)).getText().toString(),
                    ((EditText) findViewById(R.id.et_recipient_address)).getText().toString(),
                    new Date(),
                    null,
                    ((EditText) findViewById(R.id.et_recipient_phone)).getText().toString(),
                    ((EditText) findViewById(R.id.et_recipient_email)).getText().toString(),
                    ((EditText) findViewById(R.id.et_messenger_name)).getText().toString(),
                    ((EditText) findViewById(R.id.et_messenger_id)).getText().toString().equals("")? null:
                    Integer.parseInt(((EditText) findViewById(R.id.et_messenger_id)).getText().toString()));

            //    public Parcel(int parcelId, ParcelType parcelType, Boolean isFragile, Double weight,
//    Location location, String recipientName, String address, Date deliveryDate, Date arrivalDate,
//    String recipientPhone, String recipientEmail, String messengerName, Int messengerId) {
            addParcelViewModel.addParcel(parcel);
            finish();
        }
    }
}
