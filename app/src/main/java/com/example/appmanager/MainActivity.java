package com.example.appmanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView installedApps;
    private List<AppList> applist;
    private AppAdapter appAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        installedApps = findViewById(R.id.appList);
        installedApps.setLayoutManager(new LinearLayoutManager(this));

        function();
    }

    public void function() {
        applist = getAllApps();
        appAdapter = new AppAdapter(this, applist);
        installedApps.setAdapter(appAdapter);
    }

    public List<AppList> getAllApps() {
        PackageManager pm = getPackageManager();
        List<AppList> apps = new ArrayList<>();
        List<PackageInfo> packs = pm.getInstalledPackages(0);
        for (PackageInfo p : packs) {
            if ((p.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                String appName = p.applicationInfo.loadLabel(pm).toString();
                Drawable icon = p.applicationInfo.loadIcon(pm);
                String packages = p.applicationInfo.packageName;
                apps.add(new AppList(appName, icon, packages));
                Log.d("AppList", "Added app: " + appName);
            }
        }
        return apps;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        applist = getAllApps();
        appAdapter = new AppAdapter(this, applist);
        installedApps.setAdapter(appAdapter);
    }
}
