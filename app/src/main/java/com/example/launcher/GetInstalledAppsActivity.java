package com.example.launcher;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class GetInstalledAppsActivity extends AppCompatActivity {

    private List<AppList> installedApps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_apps);

        ListView applist = findViewById(R.id.installed_app_list);

        installedApps = getInstalledApps();
        AppAdapter installedAppAdapter = new AppAdapter(GetInstalledAppsActivity.this, installedApps);
        applist.setAdapter(installedAppAdapter);
        applist.setOnItemClickListener((adapterView, view, appPosition, l) -> {
            String[] dialog = {" Open App", " App Info"};
            AlertDialog.Builder builder = new AlertDialog.Builder(GetInstalledAppsActivity.this);
            builder.setTitle("Choose Action")
                    .setItems(dialog, (dialog1, selectAction) -> openInstalledApp(selectAction, appPosition));
            builder.show();

        });

        //Total Number of Installed-Apps(i.e. List Size)
        String abc = applist.getCount() + "";
        TextView countApps = findViewById(R.id.countApps);
        countApps.setText("Total Installed Apps: " + abc);
        Toast.makeText(this, abc + " Apps", Toast.LENGTH_SHORT).show();

    }

    public List<AppList> getInstalledApps() {
        List<AppList> apps = new ArrayList<AppList>();
        List<PackageInfo> packs = getPackageManager().getInstalledPackages(PackageManager.GET_META_DATA);

        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
            String appName = p.applicationInfo.loadLabel(getPackageManager()).toString();
            Drawable icon = p.applicationInfo.loadIcon(getPackageManager());
            String packages = p.applicationInfo.packageName;
            apps.add(new AppList(appName, icon, packages));
        }
        return apps;
    }

    private void openInstalledApp(int selectAction, int appPosition) {
        String pkg = installedApps.get(appPosition).packages;
        if (selectAction == 0) {
            if (pkg.equals("com.example.launcher") || pkg.equals("com.example.launcherapplication")) {
                Intent intent1 = new Intent(GetInstalledAppsActivity.this, APIDataActivity.class);
                startActivity(intent1);
            } else {
                Intent intent = getPackageManager().getLaunchIntentForPackage(installedApps.get(appPosition).packages);
                if (intent != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(GetInstalledAppsActivity.this, installedApps.get(appPosition).packages + " Error, Please Try Again...", Toast.LENGTH_SHORT).show();
                }
            }
        }

        if (selectAction == 1) {
            Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + installedApps.get(appPosition).packages));
            Toast.makeText(GetInstalledAppsActivity.this, installedApps.get(appPosition).packages, Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }

    }

    private class AppAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;
        private List<AppList> listStorage;

        private AppAdapter(Context context, List<AppList> customizedListView) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            listStorage = customizedListView;
        }

        @Override
        public int getCount() {
            return listStorage.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder listViewHolder;
            if (convertView == null) {
                listViewHolder = new ViewHolder();
                convertView = layoutInflater.inflate(R.layout.installed_app_list, parent, false);
                listViewHolder.textInListView = convertView.findViewById(R.id.list_app_name);
                listViewHolder.imageInListView = convertView.findViewById(R.id.app_icon);
                listViewHolder.packageInListView = convertView.findViewById(R.id.app_package);
                convertView.setTag(listViewHolder);
            } else {
                listViewHolder = (ViewHolder) convertView.getTag();
            }
            listViewHolder.textInListView.setText(listStorage.get(position).getName());
            listViewHolder.imageInListView.setImageDrawable(listStorage.get(position).getIcon());
            listViewHolder.packageInListView.setText(listStorage.get(position).getPackages());

            return convertView;
        }

        class ViewHolder {
            TextView textInListView;
            ImageView imageInListView;
            TextView packageInListView;
        }
    }

    private class AppList {
        Drawable icon;
        private String name;
        private String packages;

        public AppList(String name, Drawable icon, String packages) {
            this.name = name;
            this.icon = icon;
            this.packages = packages;
        }

        public String getName() {
            return name;
        }

        public Drawable getIcon() {
            return icon;
        }

        public String getPackages() {
            return packages;
        }

    }

}
