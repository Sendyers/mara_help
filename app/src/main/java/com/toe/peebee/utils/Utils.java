package com.toe.peebee.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.toe.peebee.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Wednesday on 10/12/2015.
 */
public class Utils {

    public static void setUpToolbar(AppCompatActivity activity, boolean centerText) {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setHomeButtonEnabled(true);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (centerText) {
            ActivityInfo activityInfo = null;
            try {
                activityInfo = activity.getPackageManager().getActivityInfo(
                        activity.getComponentName(), PackageManager.GET_META_DATA);
                TextView tvToolBarText = (TextView) toolbar.findViewById(R.id.tvToolbarText);
                tvToolBarText.setText(activityInfo.loadLabel(activity.getPackageManager())
                        .toString());
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(R.color.colorStatusBar));
        }
    }

    public static String formatNumber(float number) {
        DecimalFormat df = new DecimalFormat("###,###,###");
        return df.format(number);
    }

    public static boolean networkIsAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static Calendar formatTime(String rawTime) {
        // TODO Auto-generated method stub
        // "2015_11_08_04_04_06"

        String splitTimestamp[] = rawTime.split("_");
        int year = Integer.parseInt(splitTimestamp[0]);
        int month = Integer.parseInt(splitTimestamp[1]);
        int day = Integer.parseInt(splitTimestamp[2]);
        int hour = Integer.parseInt(splitTimestamp[3]);
        int minute = Integer.parseInt(splitTimestamp[4]);
        int second = Integer.parseInt(splitTimestamp[5]);

        Date dateObj = new Date(year - 1900,
                month - 1, day, hour, minute, second);

        Calendar calender = Calendar.getInstance();
        calender.setTime(dateObj);

        return calender;
    }


    public static File createSourceFile(Context context, Bitmap bitmap) {
        //create a file to write bitmap data
        File sourceFile = new File(context.getCacheDir(), "image-" + new SimpleDateFormat("yyyy-MM-dd-HHmmss").format(new Date()) + ".png");
        try {
            sourceFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Convert bitmap to byte array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();

        //write the bytes in file
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(sourceFile);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sourceFile;
    }

    public static String generateTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = dateFormat.format(new Date());

        return timestamp;
    }

    public static String bytesToHexString(byte[] bytes) {
        // http://stackoverflow.com/questions/332079
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public static String generateRandomId() {
        return UUID.randomUUID().toString();
    }

    public static String generateProductId() {
        //Length of the ID has to be 13
        char[] characterSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        int length = 13;
        Random random = new SecureRandom();
        char[] result = new char[length];
        for (int i = 0; i < result.length; i++) {
            // picks a random index out of character set > random character
            int randomCharIndex = random.nextInt(characterSet.length);
            result[i] = characterSet[randomCharIndex];
        }
        return new String(result);
    }

    public static int darkenColor(int color) {
        double factor = 1.5;
        int a = Color.alpha(color);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);

        return Color.argb(a,
                Math.max((int) (r * factor), 0),
                Math.max((int) (g * factor), 0),
                Math.max((int) (b * factor), 0));
    }

    public static double getDistanceAway(double fromLat, double fromLon, double toLat, double toLon) {
        double radius = 6378137;   // approximate Earth radius, *in meters*
        double deltaLat = toLat - fromLat;
        double deltaLon = toLon - fromLon;
        double angle = 2 * Math.asin(Math.sqrt(
                Math.pow(Math.sin(deltaLat / 2), 2) +
                        Math.cos(fromLat) * Math.cos(toLat) *
                                Math.pow(Math.sin(deltaLon / 2), 2)));
        return radius * angle;
    }

    public static int roundToNearest(double valueDouble) {
        int value = (int) valueDouble;
        int answer = 0;
        if (value % 50 < 25) {
            answer = value - (value % 50);
        } else if (value % 50 > 25) {
            answer = value + (50 - (value % 50));
        } else if (value % 50 == 25) {
            answer = value + 25; //when it is halfawy between the nearest 50 it will automatically round up, change this line to 'return value - 25' if you want it to automatically round down
        }

        return answer;
    }

    public static int generateRandomInteger() {
        int min = 0;
        int max = 100000;
        SecureRandom rand = new SecureRandom();
        rand.setSeed(new Date().getTime());
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    public static void generateDeviceId(Activity activity, SharedPreferences sp) {
        final TelephonyManager tm = (TelephonyManager) activity.getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, androidId;
        tmDevice = String.valueOf(tm.getDeviceId());
        tmSerial = String.valueOf(tm.getSimSerialNumber());
        androidId = String.valueOf(android.provider.Settings.Secure.getString(activity.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID));
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());

        sp.edit().putString("userId", "user:" + deviceUuid.toString()).apply();
    }

}
