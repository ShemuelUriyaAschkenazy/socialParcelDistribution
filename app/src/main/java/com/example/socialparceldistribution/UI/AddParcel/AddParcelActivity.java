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
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddParcelActivity extends AppCompatActivity implements View.OnClickListener {

    AddParcelViewModel addParcelViewModel;
    LocationManager locationManager;
    Location warehouseLocation;
    Spinner spinner_type;
    Spinner spinner_isFragile;
    final int LOCATION_PERMISSION = 1;


    EditText etWeight, etLocation, etRecipient_name, etRecipient_phone, etRecipient_address, etRecipient_email;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_parcel_action_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_action) {

            String recipientName = ((EditText) findViewById(R.id.et_recipient_name)).getText().toString();
            Double weight = ((EditText) findViewById(R.id.et_weight)).getText().toString().isEmpty() ? null : Double.parseDouble(((EditText) findViewById(R.id.et_weight)).getText().toString());
            String recipientPhone = ((EditText) findViewById(R.id.et_recipient_phone)).getText().toString();
            String recipientEmail = ((EditText) findViewById(R.id.et_recipient_email)).getText().toString();
            int typeSpinnerPosition = spinner_type.getSelectedItemPosition();
            int isFragileSpinnerPosition = spinner_isFragile.getSelectedItemPosition();
            String recipientAddress = ((EditText) findViewById(R.id.et_recipient_address)).getText().toString();
            String warehouseAddress = ((EditText) findViewById(R.id.et_warehouseLocation)).getText().toString();
            Location recipientLocation;



            if (!addParcelViewModel.isValidEmail(recipientEmail)) {
                Toast.makeText(this, "6:please enter valid email", Toast.LENGTH_LONG).show();
                return true;
            }

            Geocoder geocoder = new Geocoder(this);
            if (warehouseAddress.isEmpty()) { //calc address according to the location
                getLocation();
                if (warehouseLocation != null)
                    try {
                        List<Address> l = geocoder.getFromLocation(warehouseLocation.getLatitude(), warehouseLocation.getLongitude(), 1);
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
                        warehouseLocation = new Location("warehouseLocation");
                        warehouseLocation.setLatitude(temp.getLatitude());
                        warehouseLocation.setLongitude(temp.getLongitude());
                    } else {
                        Toast.makeText(this, "4:Unable to understand address", Toast.LENGTH_LONG).show();
                        return true;
                    }
                } catch (IOException e) {
                    Toast.makeText(this, "5:Unable to understand address. Check Internet connection.", Toast.LENGTH_LONG).show();
                    return true;
                }
            }

            if (recipientAddress.isEmpty()) {
                Toast.makeText(this, "6:please enter recipient address", Toast.LENGTH_LONG).show();
                return true;
            }
            try {
                List<Address> l = geocoder.getFromLocationName(recipientAddress, 1);
                if (!l.isEmpty()) {
                    Address temp = l.get(0);
                    recipientLocation = new Location("recipientLocation");
                    recipientLocation.setLatitude(temp.getLatitude());
                    recipientLocation.setLongitude(temp.getLongitude());
                } else {
                    Toast.makeText(this, "7:Unable to understand address, try again", Toast.LENGTH_LONG).show();
                    return true;
                }
            } catch (IOException e) {
                Toast.makeText(this, "8:Unable to understand address. Check Internet connection.", Toast.LENGTH_LONG).show();
                return true;
            }

//            if(recipientPhone.toString().isEmpty()) {
//                Toast.makeText(this, getResources().getString(R.string.enterPhone), Toast.LENGTH_LONG).show();
//                return true;
//            }

            addParcelViewModel.addParcel(typeSpinnerPosition,
                    isFragileSpinnerPosition,
                    weight,
                    warehouseLocation,
                    recipientName,
                    warehouseAddress,
                    recipientAddress,
                    recipientLocation,
                    recipientPhone,
                    recipientEmail);

            finish();
        }
        return super.onOptionsItemSelected(item);
    }

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION);
        } else {
            warehouseLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (warehouseLocation == null) {
                Toast.makeText(this, getResources().getString(R.string.pleaseTurnOnGPS), Toast.LENGTH_LONG).show();
                return;
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                        checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                    warehouseLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            } else {
                Toast.makeText(this, "Until you grant the permission, we cannot display the warehouseLocation", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_addParcel) {
            String recipientName = ((EditText) findViewById(R.id.et_recipient_name)).getText().toString();
            Double weight = ((EditText) findViewById(R.id.et_weight)).getText().toString().isEmpty() ? null : Double.parseDouble(((EditText) findViewById(R.id.et_weight)).getText().toString());
            String recipientPhone = ((EditText) findViewById(R.id.et_recipient_phone)).getText().toString();
            String recipientEmail = ((EditText) findViewById(R.id.et_recipient_email)).getText().toString();
            int typeSpinnerPosition = spinner_type.getSelectedItemPosition();
            int isFragileSpinnerPosition = spinner_isFragile.getSelectedItemPosition();
            String recipientAddress = ((EditText) findViewById(R.id.et_recipient_address)).getText().toString();
            String warehouseAddress = ((EditText) findViewById(R.id.et_warehouseLocation)).getText().toString();
            Location recipientLocation;

            if (!addParcelViewModel.isValidEmail(recipientEmail)) {
                Toast.makeText(this, "6:please enter valid email", Toast.LENGTH_LONG).show();
                return;
            }

            Geocoder geocoder = new Geocoder(this);
            if (warehouseAddress.isEmpty()) { //calc address according to the location
                getLocation();
                if (warehouseLocation != null)
                    try {
                        List<Address> l = geocoder.getFromLocation(warehouseLocation.getLatitude(), warehouseLocation.getLongitude(), 1);
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
                        warehouseLocation = new Location("warehouseLocation");
                        warehouseLocation.setLatitude(temp.getLatitude());
                        warehouseLocation.setLongitude(temp.getLongitude());
                    } else {
                        Toast.makeText(this, "4:Unable to understand address", Toast.LENGTH_LONG).show();
                        return;
                    }
                } catch (IOException e) {
                    Toast.makeText(this, "5:Unable to understand address. Check Internet connection.", Toast.LENGTH_LONG).show();
                    return;
                }
            }

            //calc location according to the given address
            if (recipientAddress.isEmpty()) {
                Toast.makeText(this, "6:please enter recipient address", Toast.LENGTH_LONG).show();
                return;
            }
            try {
                List<Address> l = geocoder.getFromLocationName(recipientAddress, 1);
                if (!l.isEmpty()) {
                    Address temp = l.get(0);
                    recipientLocation = new Location("recipientLocation");
                    recipientLocation.setLatitude(temp.getLatitude());
                    recipientLocation.setLongitude(temp.getLongitude());
                } else {
                    Toast.makeText(this, "7:Unable to understand address, try again", Toast.LENGTH_LONG).show();
                    return;
                }
            } catch (IOException e) {
                Toast.makeText(this, "8:Unable to understand address. Check Internet connection.", Toast.LENGTH_LONG).show();
                return;
            }

//            if(recipientPhone.toString().isEmpty()) {
//                Toast.makeText(this, getResources().getString(R.string.enterPhone), Toast.LENGTH_LONG).show();
//                return true;
//            }

            addParcelViewModel.addParcel(typeSpinnerPosition,
                    isFragileSpinnerPosition,
                    weight,
                    warehouseLocation,
                    recipientName,
                    warehouseAddress,
                    recipientAddress,
                    recipientLocation,
                    recipientPhone,
                    recipientEmail);

            finish();
        }
    }
}

