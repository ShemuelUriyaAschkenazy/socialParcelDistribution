package com.example.socialparceldistribution.UI.AddParcel;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.socialparceldistribution.Entities.Parcel;
import com.example.socialparceldistribution.Entities.UserLocation;
import com.example.socialparceldistribution.R;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddParcelActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    AddParcelViewModel addParcelViewModel;
    RadioGroup radioGroup_type, radioGroup_fragility;
    // Acquire a reference to the system Location Manager
    LocationManager locationManager;
    // Define a listener that responds to location updates
    LocationListener locationListener;
    UserLocation location;
    EditText etWeight, etLocation, etRecipient_name, etRecipient_phone, etRecipient_address, etRecipient_email,
            etMessenger_name, etMessenger_id;
    Button btAddParcel;
    RadioButton radioButton_envelope, radioButton_big, radioButton_small, radioButton_fragile, radioButton_noFragile;
    Parcel parcel;

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
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
    }


    private void getLocation() {
        final int Location_PERMISSION = 1;
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Location_PERMISSION);
        } else {

            Location l= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (l!=null)
                location=new UserLocation(l.getLatitude(),l.getLongitude());
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 5) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                        checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            } else {
                Toast.makeText(this, "Until you grant the permission, we cannot display the location", Toast.LENGTH_SHORT).show();
            }
        }
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

            getLocation();
            parcel = new Parcel(
//                    Math.toIntExact(System.currentTimeMillis()/1000000),
                    null, null,
                    isFragile,
                    ((EditText) findViewById(R.id.et_weight)).getText().toString().equals("") ? null :
                            Double.parseDouble(((EditText) findViewById(R.id.et_weight)).getText().toString()),
                    location,
                    ((EditText) findViewById(R.id.et_recipient_name)).getText().toString(),
                    ((EditText) findViewById(R.id.et_recipient_address)).getText().toString(),
                    new Date(),
                    null,
                    ((EditText) findViewById(R.id.et_recipient_phone)).getText().toString(),
                    ((EditText) findViewById(R.id.et_recipient_email)).getText().toString(),
                    ((EditText) findViewById(R.id.et_messenger_name)).getText().toString(),
                    ((EditText) findViewById(R.id.et_messenger_id)).getText().toString().equals("") ? null :
                            Integer.parseInt(((EditText) findViewById(R.id.et_messenger_id)).getText().toString()));

            //    public Parcel(int parcelId, ParcelType parcelType, Boolean isFragile, Double weight,
//    Location location, String recipientName, String address, Date deliveryDate, Date arrivalDate,
//    String recipientPhone, String recipientEmail, String messengerName, Int messengerId) {
            addParcelViewModel.addParcel(parcel);
            finish();
        }
    }
}
