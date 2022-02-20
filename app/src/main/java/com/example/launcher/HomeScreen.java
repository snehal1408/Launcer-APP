package com.example.launcher;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.BatteryManager;
import android.os.Bundle;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class HomeScreen extends AppCompatActivity {

    BatteryReceiver mBatteryReceiver = new BatteryReceiver();
    IntentFilter mIntentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

    Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        button = findViewById(R.id.app_launch);
        button.setTextColor(Color.WHITE);
        button.setBackgroundColor(Color.GRAY);
        registerReceiver(mBatteryReceiver, mIntentFilter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), GetInstalledApps.class);
                view.getContext().startActivity(intent);
            }
        });

    }

//        @Override
//        public void onResume() {
//            super.onResume();
//            //registerReceiver(mBatteryReceiver, mIntentFilter);
//        }
//
//        @Override
//        public void onPause() {
//            //unregisterReceiver(mBatteryReceiver);
//            super.onPause();
//        }
}
