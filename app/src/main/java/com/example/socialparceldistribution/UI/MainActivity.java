package com.example.socialparceldistribution.UI;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.socialparceldistribution.R;
import com.example.socialparceldistribution.UI.AddParcel.AddParcelActivity;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addParcel= findViewById(R.id.btn_addParcel);
        addParcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, AddParcelActivity.class);
                startActivity(intent);
            }
        });

    }


}
