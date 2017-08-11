package com.sendyit.selfhelp.utils;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.sendyit.selfhelp.R;

public class Utils {

    public static void setUpToolbar(AppCompatActivity activity, boolean centerText) {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setHomeButtonEnabled(true);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (centerText) {
            try {
                ActivityInfo activityInfo = activity.getPackageManager().getActivityInfo(activity.getComponentName(), PackageManager.GET_META_DATA);
                TextView tvToolBarText = (TextView) toolbar.findViewById(R.id.tvToolbarText);
                tvToolBarText.setText(activityInfo.loadLabel(activity.getPackageManager()).toString());
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }

        setUpStatusBar(activity);
    }

    public static void setUpStatusBar(AppCompatActivity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(R.color.colorPrimaryDark));
        }
    }
}
