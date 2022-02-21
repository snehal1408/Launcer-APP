package com.example.launcher;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class GetInstalledApps extends AppCompatActivity {

    List<AppList> installedApps;
    AppAdapter installedAppAdapter;
    ListView applist;
    String appName, packages;
    Drawable icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_apps);

        applist = findViewById(R.id.installed_app_list);

        installedApps = getInstalledApps();
        installedAppAdapter = new AppAdapter(GetInstalledApps.this, installedApps);
        applist.setAdapter(installedAppAdapter);
        applist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                String[] dialog = {" Open App", " App Info"};
                AlertDialog.Builder builder = new AlertDialog.Builder(GetInstalledApps.this);
                builder.setTitle("Choose Action")
                        .setItems(dialog, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                // The 'which' argument contains the index position of the selected item
                                String pkg = installedApps.get(i).packages;
                                if (which == 0) {
                                    if (pkg.equals("com.example.launcher") || pkg.equals("com.example.launcherapplication")) {
                                        Intent intent1 = new Intent(GetInstalledApps.this, APIData.class);
                                        startActivity(intent1);
                                    } else {
                                        Intent intent = getPackageManager().getLaunchIntentForPackage(installedApps.get(i).packages);
                                        if (intent != null) {
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(GetInstalledApps.this, installedApps.get(i).packages + " Error, Please Try Again...", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                if (which == 1) {
                                    Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    intent.setData(Uri.parse("package:" + installedApps.get(i).packages));
                                    Toast.makeText(GetInstalledApps.this, installedApps.get(i).packages, Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                }

                            }
                        });
                builder.show();
            }
        });

        //Total Number of Installed-Apps(i.e. List Size)
        String  abc = applist.getCount()+"";
        TextView countApps = (TextView)findViewById(R.id.countApps);
        countApps.setText("Total Installed Apps: "+abc);
        Toast.makeText(this, abc+" Apps", Toast.LENGTH_SHORT).show();

    }

    public List<AppList> getInstalledApps() {
        PackageManager pm = getPackageManager();
        List<AppList> apps = new ArrayList<AppList>();
        List<PackageInfo> packs = getPackageManager().getInstalledPackages(PackageManager.GET_META_DATA);

        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
//            if (isSystemPackage(p)) {
                appName = p.applicationInfo.loadLabel(getPackageManager()).toString();
                icon = p.applicationInfo.loadIcon(getPackageManager());
                packages = p.applicationInfo.packageName;
                apps.add(new AppList(appName, icon, packages));
//            }
        }
        return apps;
    }

    public boolean isSystemPackage(PackageInfo pkgInfo) {
        return (pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
    }

    public class AppAdapter extends BaseAdapter {

        public LayoutInflater layoutInflater;
        public List<AppList> listStorage;

        public AppAdapter(Context context, List<AppList> customizedListView) {
            layoutInflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            if(convertView == null){
                listViewHolder = new ViewHolder();
                convertView = layoutInflater.inflate(R.layout.installed_app_list, parent, false);
                listViewHolder.textInListView = (TextView)convertView.findViewById(R.id.list_app_name);
                listViewHolder.imageInListView = (ImageView)convertView.findViewById(R.id.app_icon);
                listViewHolder.packageInListView=(TextView)convertView.findViewById(R.id.app_package);
                convertView.setTag(listViewHolder);
            }
            else
            {
                listViewHolder = (ViewHolder)convertView.getTag();
            }
            listViewHolder.textInListView.setText(listStorage.get(position).getName());
            listViewHolder.imageInListView.setImageDrawable(listStorage.get(position).getIcon());
            listViewHolder.packageInListView.setText(listStorage.get(position).getPackages());

            return convertView;
        }

        class ViewHolder{
            TextView textInListView;
            ImageView imageInListView;
            TextView packageInListView;
        }
    }

    public class AppList {
        private String name;
        Drawable icon;
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







//import android.content.pm.ApplicationInfo;
//import android.content.pm.PackageManager;
//import android.os.Bundle;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import java.util.List;
//
//public class GetInstalledApps extends AppCompatActivity {
//
//    ListView lv_listapp;
//    TextView tv_totalapp;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.list_packages);
//
//        lv_listapp = findViewById(R.id.listapp);
//        tv_totalapp = findViewById(R.id.totalapp);
//        List<ApplicationInfo> listAppsInfo = getPackageManager()
//                .getInstalledApplications(PackageManager.GET_META_DATA);
//        // create a list with size of total number of apps
//        String[] apps = new String[listAppsInfo.size()];
//        int i = 0;
//        // add all the app name in string list
//        for(ApplicationInfo applicationInfo : listAppsInfo) {
//        apps[i] = applicationInfo.packageName;
//        i++;
//    }
//    // set all the apps name in list view
//        lv_listapp.setAdapter(new ArrayAdapter<String>
//                (GetInstalledApps.this, android.R.layout.simple_list_item_1, apps));
//        tv_totalapp.setText(listAppsInfo.size()+"Apps are installed");
//    }
//}
