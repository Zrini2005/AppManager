package com.example.appmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.ViewHolder> {
    private List<AppList> appListList;
    private Context context;

    public AppAdapter(Context context, List<AppList> list) {
        this.context = context;
        appListList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(appListList.get(position).getName());
        holder.imageView.setImageDrawable(appListList.get(position).getIcon());
        holder.textView2.setText(appListList.get(position).getPackages());

        holder.itemView.setOnClickListener(v -> {
            String[] colors = {"Open App", "App Info", "Delete App"};
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Choose Action")
                    .setItems(colors, (dialog, which) -> {
                        switch (which) {
                            case 0:
                                Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(appListList.get(position).getPackages());
                                if (launchIntent != null) {
                                    context.startActivity(launchIntent);
                                } else {
                                    Toast.makeText(context, appListList.get(position).getPackages() + " Error, Please Try Again...", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case 1:
                                Intent appInfoIntent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                appInfoIntent.setData(Uri.parse("package:" + appListList.get(position).getPackages()));
                                context.startActivity(appInfoIntent);
                                break;
                            case 2:
                                Uri packageUri = Uri.parse("package:" + appListList.get(position).getPackages());
                                Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageUri);
                               
                                ((Activity) context).startActivityForResult(uninstallIntent, 1);


                                break;
                        }
                    });
            builder.show();
        });
    }

    @Override
    public int getItemCount() {
        return appListList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;
        public TextView textView2;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.app_name);
            imageView = itemView.findViewById(R.id.app_icon);
            textView2 = itemView.findViewById(R.id.app_package);
        }
    }
}
