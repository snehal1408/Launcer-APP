package com.example.launcher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

public class BatteryReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent batteryStatusIntent = context.getApplicationContext()
                .registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        TextView tv_battery_status = ((HomeActivity) context).findViewById(R.id.battery_status);
        TextView tv_battery_percentage = ((HomeActivity) context).findViewById(R.id.battery_percentage);
        ImageView batteryImage = ((HomeActivity) context).findViewById(R.id.battery_indicator);

        String action = batteryStatusIntent.getAction();

        if (action != null && action.equals(Intent.ACTION_BATTERY_CHANGED)) {

            // Status
            int status = batteryStatusIntent.getIntExtra(BatteryManager.EXTRA_STATUS, 0);
            String message = "";

            switch (status) {
                case BatteryManager.BATTERY_STATUS_FULL:
                    message = "Full";
                    break;
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    message = "Charging";
                    break;
                case BatteryManager.BATTERY_STATUS_DISCHARGING:
                    message = "Discharging";
                    break;
                case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                    message = "Not charging";
                    break;
                case BatteryManager.BATTERY_STATUS_UNKNOWN:
                    message = "Unknown";
                    break;
            }
            tv_battery_status.setText(message);


            // Percentage
            int level = batteryStatusIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            int scale = batteryStatusIntent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
            int percentage = level * 100 / scale;
            tv_battery_percentage.setText(percentage + "%");


            // Image

            if (percentage >= 90) {
                batteryImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.b100));

            } else if (percentage < 90 && percentage >= 65) {
                batteryImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.b75));

            } else if (percentage < 65 && percentage >= 40) {
                batteryImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.b50));

            } else if (percentage < 40 && percentage >= 15) {
                batteryImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.b25));

            } else {
                batteryImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.b0));

            }
        }
    }
}
