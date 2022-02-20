package com.example.launcher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.BatteryManager;
import android.widget.ImageView;
import android.widget.TextView;

public class BatteryReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent batteryStatusIntent = context.getApplicationContext()
                .registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        TextView tv_battery_status = ((HomeScreen)context).findViewById(R.id.battery_status);
        TextView tv_battery_percentage = ((HomeScreen)context).findViewById(R.id.battery_percentage);
        ImageView batteryImage = ((HomeScreen)context).findViewById(R.id.battery_indicator);

        String action = batteryStatusIntent.getAction();

        if (action != null && action.equals(Intent.ACTION_BATTERY_CHANGED)) {

            // Status
            int status = batteryStatusIntent.getIntExtra(BatteryManager.EXTRA_STATUS,0);
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
            Resources res = context.getResources();

            if (percentage >= 90) {
                batteryImage.setImageDrawable(res.getDrawable(R.drawable.b100));

            } else if (90 > percentage && percentage >= 65) {
                batteryImage.setImageDrawable(res.getDrawable(R.drawable.b75));

            } else if (65 > percentage && percentage >= 40) {
                batteryImage.setImageDrawable(res.getDrawable(R.drawable.b50));

            } else if (40 > percentage && percentage >= 15) {
                batteryImage.setImageDrawable(res.getDrawable(R.drawable.b25));

            } else {
                batteryImage.setImageDrawable(res.getDrawable(R.drawable.b0));

            }

        }
    }
}
