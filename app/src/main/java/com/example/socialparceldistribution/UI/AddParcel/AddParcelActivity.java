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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    LocationManager locationManager;
    // Define a listener that responds to warehouseLocation updates
    LocationListener locationListener;
    UserLocation warehouseLocation;
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
            boolean isFragile = false;
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
            String warehouseAddress = ((EditText) findViewById(R.id.et_warehouseLocation)).getText().toString();

            if (warehouseAddress.isEmpty()) { //calc address according to the location
                getLocation();
                if (warehouseLocation != null)
                    try {
                        List<Address> l = geocoder.getFromLocation(warehouseLocation.getLat(), warehouseLocation.getLon(), 1);
                        if (!l.isEmpty()) {
                            Address temp = l.get(0);
                            warehouseAddress = temp.getAddressLine(0).toString();
                        } else {
                            Toast.makeText(this, "1:Unable to find address, where are you?!", Toast.LENGTH_LONG).show();
                            return true;
                        }
                    } catch (IOException e) {
                        Toast.makeText(this, "2:Unable to find your address. please check internet connection and try again.", Toast.LENGTH_LONG).show();
                        return true;
                    }
                else {
                    Toast.makeText(this, "3:please check internet connection and try again.", Toast.LENGTH_LONG).show();
                    return true;
                }
            } else { //calc location according to the given address
                try {
                    List<Address> l = geocoder.getFromLocationName(warehouseAddress, 1);
                    if (!l.isEmpty()) {
                        Address temp = l.get(0);
                        warehouseLocation = new UserLocation(temp.getLatitude(),temp.getLongitude());
                    } else {
                        Toast.makeText(this, "4:Unable to understand address", Toast.LENGTH_LONG).show();
                        return true;
                    }
                } catch (IOException e) {
                    Toast.makeText(this, "5:Unable to understand address. Check Internet connection.", Toast.LENGTH_LONG).show();
                    return true;
                }
            }

            String recipientAddress = ((EditText) findViewById(R.id.et_recipient_address)).getText().toString();
            UserLocation recipientLocation;
            //calc location according to the given address
            if (recipientAddress.isEmpty()) {
                Toast.makeText(this, "6:please enter recipient address", Toast.LENGTH_LONG).show();
                return true;
            }
            try {
                List<Address> l = geocoder.getFromLocationName(recipientAddress, 1);
                if (!l.isEmpty()) {
                    Address temp = l.get(0);
                    recipientLocation = new UserLocation(temp.getLatitude(),temp.getLongitude());
                } else {
                    Toast.makeText(this, "7:Unable to understand address, try again", Toast.LENGTH_LONG).show();
                    return true;
                }
            } catch (IOException e) {
                Toast.makeText(this, "8:Unable to understand address. Check Internet connection.", Toast.LENGTH_LONG).show();
                return true;
            }


            String recipientName = ((EditText) findViewById(R.id.et_recipient_name)).getText().toString();
            String w= ((EditText) findViewById(R.id.et_weight)).getText().toString();
            Double weight = ((EditText) findViewById(R.id.et_weight)).getText().toString().isEmpty() ? null:Double.parseDouble(((EditText) findViewById(R.id.et_weight)).getText().toString());
            String recipientPhone = ((EditText) findViewById(R.id.et_recipient_phone)).getText().toString();
            String recipientEmail = ((EditText) findViewById(R.id.et_recipient_email)).getText().toString();

            parcel = new Parcel(
                    parcelType,
                    Parcel.ParcelStatus.registered,
                    isFragile,
                    weight,
                    warehouseLocation,
                    recipientName,
                    warehouseAddress,
                    recipientAddress,
                    recipientLocation,
                    new Date(),
                    null,
                    recipientPhone, recipientEmail,
                    null);
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
        final LiveData<Boolean> isSuccess = addParcelViewModel.getIsSuccess();
        isSuccess.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (isSuccess.getValue() == null)
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
        } else{
            Location temp=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            warehouseLocation = new UserLocation(temp.getLatitude(),temp.getLongitude());

        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 5) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                        checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            } else {
                Toast.makeText(this, "Until you grant the permission, we cannot display the warehouseLocation", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_addParcel) {
            Parcel.ParcelType parcelType;
            boolean isFragile = false;
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
            String warehouseAddress = ((EditText) findViewById(R.id.et_warehouseLocation)).getText().toString();

            if (warehouseAddress.isEmpty()) { //calc address according to the location
                getLocation();
                if (warehouseLocation != null)
                    try {
                        List<Address> l = geocoder.getFromLocation(warehouseLocation.getLat(), warehouseLocation.getLon(), 1);
                        if (!l.isEmpty()) {
                            Address temp = l.get(0);
                            warehouseAddress = temp.getAddressLine(0).toString();
                        } else {
                            Toast.makeText(this, "1:Unable to find address, where are you?!", Toast.LENGTH_LONG).show();
                            return;
                        }
                    } catch (IOException e) {
                        Toast.makeText(this, "2:Unable to find your address. please check internet connection and try again.", Toast.LENGTH_LONG).show();
                        return;
                    }
                else {
                    Toast.makeText(this, "3:please check internet connection and try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            } else { //calc location according to the given address
                try {
                    List<Address> l = geocoder.getFromLocationName(warehouseAddress, 1);
                    if (!l.isEmpty()) {
                        Address temp = l.get(0);
                        warehouseLocation = new UserLocation(temp.getLatitude(),temp.getLongitude());
                    } else {
                        Toast.makeText(this, "4:Unable to understand address", Toast.LENGTH_LONG).show();
                        return;
                    }
                } catch (IOException e) {
                    Toast.makeText(this, "5:Unable to understand address. Check Internet connection.", Toast.LENGTH_LONG).show();
                    return;
                }
            }

            String recipientAddress = ((EditText) findViewById(R.id.et_recipient_address)).getText().toString();
            UserLocation recipientLocation;
            //calc location according to the given address
            if (recipientAddress.isEmpty()) {
                Toast.makeText(this, "6:please enter recipient address", Toast.LENGTH_LONG).show();
                return;
            }
            try {
                List<Address> l = geocoder.getFromLocationName(recipientAddress, 1);
                if (!l.isEmpty()) {
                    Address temp = l.get(0);
                    recipientLocation = new UserLocation(temp.getLatitude(),temp.getLongitude());
                } else {
                    Toast.makeText(this, "7:Unable to understand address, try again", Toast.LENGTH_LONG).show();
                    return;
                }
            } catch (IOException e) {
                Toast.makeText(this, "8:Unable to understand address. Check Internet connection.", Toast.LENGTH_LONG).show();
                return;
            }


            String recipientName = ((EditText) findViewById(R.id.et_recipient_name)).getText().toString();
            String w= ((EditText) findViewById(R.id.et_weight)).getText().toString();
            Double weight = ((EditText) findViewById(R.id.et_weight)).getText().toString().isEmpty() ? null:Double.parseDouble(((EditText) findViewById(R.id.et_weight)).getText().toString());
            String recipientPhone = ((EditText) findViewById(R.id.et_recipient_phone)).getText().toString();
            String recipientEmail = ((EditText) findViewById(R.id.et_recipient_email)).getText().toString();

            parcel = new Parcel(
                    parcelType,
                    Parcel.ParcelStatus.registered,
                    isFragile,
                    weight,
                    warehouseLocation,
                    recipientName,
                    warehouseAddress,
                    recipientAddress,
                    recipientLocation,
                    new Date(),
                    null,
                    recipientPhone, recipientEmail,
                    null);
            addParcelViewModel.addParcel(parcel);

            finish();
        }
    }


}

