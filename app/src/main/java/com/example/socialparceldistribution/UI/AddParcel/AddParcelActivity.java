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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.socialparceldistribution.Entities.Parcel;
import com.example.socialparceldistribution.Entities.UserLocation;
import com.example.socialparceldistribution.R;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class AddParcelActivity extends AppCompatActivity implements View.OnClickListener {

    AddParcelViewModel addParcelViewModel;
    RadioGroup radioGroup_type, radioGroup_fragility;
    // Acquire a reference to the system Location Manager
    LocationManager locationManager;
    // Define a listener that responds to location updates
    LocationListener locationListener;
    UserLocation location;
    Spinner spinner_type;
    Spinner spinner_isFragile;

    EditText etWeight, etLocation, etRecipient_name, etRecipient_phone, etRecipient_address, etRecipient_email;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_parcel_action_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_action) {
            Parcel.ParcelType parcelType;
            Boolean isFragile = false;
            switch (spinner_type.getSelectedItemPosition()) {
                case 0:
                    parcelType = Parcel.ParcelType.envelope;
                    break;
                case 1:
                    parcelType = Parcel.ParcelType.bigPackage;
                    break;
                case 2:
                    parcelType = Parcel.ParcelType.smallPackage;
                    break;
                default:
                    parcelType = null;
            }

            switch (spinner_isFragile.getSelectedItemPosition()) {
                case 0:
                    isFragile = true;
                    break;
                case 1:
                    isFragile = false;
                    break;
            }
            Geocoder geocoder = new Geocoder(this);
            String locationString = ((EditText) findViewById(R.id.et_location)).getText().toString();
            if (locationString.isEmpty())
                getLocation();
            else {
                try {
                    List<Address> l = geocoder.getFromLocationName(locationString, 1);
                    if (!l.isEmpty()) {
                        Address temp = l.get(0);
                        location = new UserLocation(temp.getLatitude(), temp.getLongitude());
                    }
                    else
                        Toast.makeText(this, "Unable to understand address", Toast.LENGTH_LONG);

                } catch (IOException e) {
                    Toast.makeText(this, "Unable to understand address. Check Internet connection.", Toast.LENGTH_LONG);
                }
            }
            parcel = new Parcel(
                    parcelType, null,
                    isFragile,
                    ((EditText) findViewById(R.id.et_weight)).getText().toString().equals("") ? null :
                            Double.parseDouble(((EditText) findViewById(R.id.et_weight)).getText().toString()),
                    location,
                    ((EditText) findViewById(R.id.et_recipient_name)).getText().toString(),
                    ((EditText) findViewById(R.id.et_recipient_address)).getText().toString(),
                    new Date(),
                    null,
                    ((EditText) findViewById(R.id.et_recipient_phone)).getText().toString(),
                    ((EditText) findViewById(R.id.et_recipient_email)).getText().toString());

            addParcelViewModel.addParcel(parcel);
            finish();


        }

        return super.onOptionsItemSelected(item);
    }

    Parcel parcel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_parcel_layout);

        addParcelViewModel = ViewModelProviders.of(this).get(AddParcelViewModel.class);
        final LiveData<Boolean> isSuccess=addParcelViewModel.getIsSuccess();
        isSuccess.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (isSuccess.getValue()==null)
                    return;
                Toast.makeText(AddParcelActivity.this, isSuccess.getValue() == true ? "success" : "failure", Toast.LENGTH_LONG).show();
            }
        });
        findViewById(R.id.btn_addParcel).setOnClickListener(this);

        spinner_type = findViewById(R.id.spinner_type);
        spinner_isFragile = findViewById(R.id.isFragile_spinner);


        //spinner_type.setAdapter(new ArrayAdapter<Parcel.ParcelType>(this, android.R.layout.simple_spinner_item, Parcel.ParcelType.values()));

        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this, R.array.parcelType, R.layout.custom_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_type.setAdapter(typeAdapter);

        ArrayAdapter<CharSequence> isFragileAdapter = ArrayAdapter.createFromResource(this, R.array.isFragile, R.layout.custom_spinner_item);
        isFragileAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_isFragile.setAdapter(isFragileAdapter);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
    }


    private void getLocation() {
        final int Location_PERMISSION = 1;
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Location_PERMISSION);
        } else {

            Location l = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (l != null)
                location = new UserLocation(l.getLatitude(), l.getLongitude());
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
    public void onClick(View v) {
        // Parcel.ParcelType parcelType;
        if (v.getId() == R.id.btn_addParcel) {
            Parcel.ParcelType parcelType;
            Boolean isFragile = false;
            switch (spinner_type.getSelectedItemPosition()) {
                case 0:
                    parcelType = Parcel.ParcelType.envelope;
                    break;
                case 1:
                    parcelType = Parcel.ParcelType.bigPackage;
                    break;
                case 2:
                    parcelType = Parcel.ParcelType.smallPackage;
                    break;
                default:
                    parcelType = null;
            }

            switch (spinner_isFragile.getSelectedItemPosition()) {
                case 0:
                    isFragile = true;
                    break;
                case 1:
                    isFragile = false;
                    break;
            }

            getLocation();
            parcel = new Parcel(
//                    Math.toIntExact(System.currentTimeMillis()/1000000),
                    parcelType, null,
                    isFragile,
                    ((EditText) findViewById(R.id.et_weight)).getText().toString().equals("") ? null :
                            Double.parseDouble(((EditText) findViewById(R.id.et_weight)).getText().toString()),
                    location,
                    ((EditText) findViewById(R.id.et_recipient_name)).getText().toString(),
                    ((EditText) findViewById(R.id.et_recipient_address)).getText().toString(),
                    new Date(),
                    null,
                    ((EditText) findViewById(R.id.et_recipient_phone)).getText().toString(),
                    ((EditText) findViewById(R.id.et_recipient_email)).getText().toString());

            //    public Parcel(int parcelId, ParcelType parcelType, Boolean isFragile, Double weight,
//    Location location, String recipientName, String address, Date deliveryDate, Date arrivalDate,
//    String recipientPhone, String recipientEmail, String messengerName, Int messengerId) {
            addParcelViewModel.addParcel(parcel);
            finish();
        }
    }


}

