package com.toe.peebee.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Wednesday on 10/16/2015.
 */
public class FirebaseUtils {

    public static void setUpFirebase(Context context) {
        Firebase.setAndroidContext(context);
        try {
            Firebase.getDefaultConfig().setPersistenceEnabled(true);
        } catch (Exception e) {
            Log.e("Persistence Error", e.getMessage());
        }
    }

    public static void saveNewUser(Firebase firebase, final AppCompatActivity activity, final SharedPreferences sp) {
        Firebase userRef = firebase.child("users/" + sp.getString("uid", null));
        Map<String, String> user = new HashMap<>();
        user.put("name", sp.getString("username", null));
        user.put("avatar", sp.getString("avatar", null));
        user.put("location", sp.getString("location", null));
        userRef.setValue(user, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError == null) {
//                    sp.edit().putBoolean("hasLoggedIn", true).apply();
//                    Intent i = new Intent(activity.getApplicationContext(), MainActivity.class);
//                    activity.startActivity(i);
                } else {
                    Toast.makeText(activity.getApplicationContext(), "Something went wrong...", Toast.LENGTH_SHORT).show();
                    System.out.println("SignIn Error: " + firebaseError.getMessage());
                }
            }
        });

//        sp.edit().putBoolean("hasLoggedIn", true).apply();
//        Intent i = new Intent(activity.getApplicationContext(), MainActivity.class);
//        activity.startActivity(i);
    }

    public static void saveNewPromotion(Firebase firebase, final AppCompatActivity activity, SharedPreferences sp, String title, String description, String price, String endDate, String imageUrl, String productId, int deliveryMethod) {
        Firebase promotionRef = firebase.child("promotions");
//        PromotionModel promotion = new PromotionModel(sp.getString("uid", null), title, description, price, endDate, imageUrl, new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date()), 0, 0, null, sp.getString("username", null), productId, sp.getString("businessName", null), sp.getString("payBillNumber", null), sp.getString("passkey", null), sp.getString("businessPhoneNumber", null), deliveryMethod, sp.getString("businessAddress", null), Double.longBitsToDouble(sp.getLong("businessLatitude", Double.doubleToLongBits(0.000))), Double.longBitsToDouble(sp.getLong("businessLongitude", Double.doubleToLongBits(0.000))));
//        promotionRef.push().setValue(promotion, new Firebase.CompletionListener() {
//            @Override
//            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
//                if (firebaseError == null) {
////                    Toast.makeText(activity.getApplicationContext(), "Promotion saved successfully", Toast.LENGTH_SHORT).show();
////                    Intent i = new Intent(activity.getApplicationContext(), MainActivity.class);
////                    activity.startActivity(i);
////                    activity.finish();
//                } else {
//                    Toast.makeText(activity.getApplicationContext(), "Error saving this promotion", Toast.LENGTH_SHORT).show();
//                    System.out.println("Promotion Data: " + firebaseError.getMessage());
//                }
//            }
//        });

//        Toast.makeText(activity.getApplicationContext(), "Promotion saved successfully", Toast.LENGTH_SHORT).show();
//        Intent i = new Intent(activity.getApplicationContext(), MainActivity.class);
//        activity.startActivity(i);
//        activity.finish();

        if (!Utils.networkIsAvailable(activity.getApplicationContext())) {
            Toast.makeText(activity.getApplicationContext(), "You are offline. The promotion will be uploaded when you have an internet connection", Toast.LENGTH_LONG).show();
        }
    }

    public static void makeNewOrder(Firebase firebase, final AppCompatActivity activity, SharedPreferences sp, String user, String title, String description, float price, String endDate, String imageUrl, String productId, int deliveryMethod, int quantity, boolean hasPaid, int orderStatus, String businessName, String payBillNumber, String passkey, String businessPhoneNumber) {
        Firebase merchantOrderRef = firebase.child("users/" + user + "/ordersReceived");
//        OrderModel merchantOrder = new OrderModel(sp.getString("uid", null), sp.getString("username", null), sp.getString("avatar", null), quantity, sp.getString("userAddress", null), hasPaid, orderStatus, title, description, price, imageUrl, new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date()), 0, 0, null, productId, businessName, payBillNumber, passkey, businessPhoneNumber, deliveryMethod, sp.getString("phoneNumber", null));
//        merchantOrderRef.push().setValue(merchantOrder, new Firebase.CompletionListener() {
//            @Override
//            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
//                if (firebaseError == null) {
////                    Toast.makeText(activity.getApplicationContext(), "Promotion saved successfully", Toast.LENGTH_SHORT).show();
////                    Intent i = new Intent(activity.getApplicationContext(), MainActivity.class);
////                    activity.startActivity(i);
////                    activity.finish();
//                } else {
//                    Toast.makeText(activity.getApplicationContext(), "Error making this order", Toast.LENGTH_SHORT).show();
//                    System.out.println("Order Data: " + firebaseError.getMessage());
//                }
//            }
//        });


        Firebase userOrderRef = firebase.child("users/" + sp.getString("uid", null) + "/ordersMade");
//        OrderModel userOrder = new OrderModel(sp.getString("uid", null), sp.getString("username", null), sp.getString("avatar", null), quantity, sp.getString("userAddress", null), hasPaid, orderStatus, title, description, price, imageUrl, new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date()), 0, 0, null, productId, businessName, payBillNumber, passkey, businessPhoneNumber, deliveryMethod, sp.getString("phoneNumber", null));
//        userOrderRef.push().setValue(userOrder, new Firebase.CompletionListener() {
//            @Override
//            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
//                if (firebaseError == null) {
////                    Toast.makeText(activity.getApplicationContext(), "Promotion saved successfully", Toast.LENGTH_SHORT).show();
////                    Intent i = new Intent(activity.getApplicationContext(), MainActivity.class);
////                    activity.startActivity(i);
////                    activity.finish();
//                } else {
//                    Toast.makeText(activity.getApplicationContext(), "Error making this order", Toast.LENGTH_SHORT).show();
//                    System.out.println("Order Data: " + firebaseError.getMessage());
//                }
//            }
//        });

        Toast.makeText(activity.getApplicationContext(), "Order made successfully", Toast.LENGTH_SHORT).show();
        activity.finish();

        if (!Utils.networkIsAvailable(activity.getApplicationContext())) {
            Toast.makeText(activity.getApplicationContext(), "You are offline. The order will be made when you have an internet connection", Toast.LENGTH_LONG).show();
        }
    }

    public static void changeOrderStatus(final Context activity, Firebase firebase, SharedPreferences sp, String user, int orderStatus, String key) {
        Firebase merchantOrderRef = firebase.child("users/" + user + "/ordersReceived");
        Map<String, String> merchantOrder = new HashMap<>();
        merchantOrder.put("orderStatus", orderStatus + "");
        merchantOrderRef.push().setValue(merchantOrder, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    Toast.makeText(activity.getApplicationContext(), "Error saving this promotion", Toast.LENGTH_SHORT).show();
                    System.out.println("Promotion Data: " + firebaseError.getMessage());
                } else {
                    //This isn't workning for some reason
                }
            }
        });

        Firebase userOrderRef = firebase.child("users/" + sp.getString("uid", null) + "/ordersMade");
        Map<String, String> userOrder = new HashMap<>();
        userOrder.put("orderStatus", orderStatus + "");
        merchantOrderRef.push().setValue(userOrder, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    Toast.makeText(activity.getApplicationContext(), "Error saving this promotion", Toast.LENGTH_SHORT).show();
                    System.out.println("Promotion Data: " + firebaseError.getMessage());
                } else {
                    //This isn't workning for some reason
                }
            }
        });
    }

    public static void editPromotion(Firebase firebase, final AppCompatActivity activity, final SharedPreferences sp, final String title, final String description, final float price, final String endDate, final String imageUrl, final int ratingCount, final float ratingValue, final Object ratings, final String productId, final int deliveryMethod, final String key) {
        Firebase promotionRef = firebase.child("promotions/" + key);
        Map<String, Object> promotion = new HashMap<>();
        promotion.put("user", sp.getString("uid", null));
        promotion.put("title", title);
        promotion.put("description", description);
        promotion.put("price", price);
        promotion.put("endDate", endDate);
        promotion.put("imageUrl", imageUrl);
        promotion.put("timestamp", new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date()));
        promotion.put("ratingsCount", ratingCount);
        promotion.put("ratingsValue", ratingValue + "");
        promotion.put("ratings", ratings);
        promotion.put("username", sp.getString("username", null));
        promotion.put("productId", productId);
        promotion.put("businessName", sp.getString("businessName", null));
        promotion.put("payBillNumber", sp.getString("payBillNumber", null));
        promotion.put("passkey", sp.getString("passkey", null));
        promotion.put("businessPhoneNumber", sp.getString("businessPhoneNumber", null));
        promotion.put("deliveryMethod", deliveryMethod);
        promotion.put("businessAddress", sp.getString("businessAddress", null));
        promotion.put("businessLatitude", Double.longBitsToDouble(sp.getLong("businessLatitude", Double.doubleToLongBits(0.0))));
        promotion.put("businessLongitude", Double.longBitsToDouble(sp.getLong("businessLongitude", Double.doubleToLongBits(0.0))));

        promotionRef.updateChildren(promotion, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError == null) {
//                    Toast.makeText(activity.getApplicationContext(), "Promotion edited successfully", Toast.LENGTH_SHORT).show();
//
//                    Intent i = new Intent(activity.getApplicationContext(), ProductView.class);
//                    Bundle b = new Bundle();
//                    b.putString("user", sp.getString("uid", null));
//                    b.putString("title", title);
//                    b.putString("description", description);
//                    b.putFloat("price", price);
//                    b.putString("endDate", endDate);
//                    b.putString("imageUrl", imageUrl);
//                    b.putString("businessLocation", sp.getString("businessLlocation", null));
//                    b.putString("timestamp", new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date()));
//                    b.putInt("ratingCount", ratingCount);
//                    b.putFloat("ratingValue", ratingValue);
//                    b.putString("ratings", String.valueOf(ratings));
//                    b.putString("username", sp.getString("username", null));
//                    b.putString("productId", productId);
//                    b.putString("businessName", sp.getString("businessName", null));
//                    b.putString("payBillNumber", sp.getString("payBillNumber", null));
//                    b.putString("passkey", sp.getString("passkey", null));
//                    b.putString("businessPhoneNumber", sp.getString("businessPhoneNumber", null));
//                    b.putString("key", key);
//                    i.putExtras(b);
//                    activity.startActivity(i);
//                    activity.finish();
                } else {
                    Toast.makeText(activity.getApplicationContext(), "Error editing this promotion", Toast.LENGTH_SHORT).show();
                    System.out.println("Promotion Data: " + firebaseError.getMessage());
                }
            }
        });

        Toast.makeText(activity.getApplicationContext(), "Promotion edited successfully", Toast.LENGTH_SHORT).show();

//        Intent i = new Intent(activity.getApplicationContext(), ProductView.class);
//        Bundle b = new Bundle();
//        b.putString("user", sp.getString("uid", null));
//        b.putString("title", title);
//        b.putString("description", description);
//        b.putFloat("price", price);
//        b.putString("endDate", endDate);
//        b.putString("imageUrl", imageUrl);
//        b.putString("businessLocation", sp.getString("businessLlocation", null));
//        b.putString("timestamp", new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date()));
//        b.putInt("ratingCount", ratingCount);
//        b.putFloat("ratingValue", ratingValue);
//        b.putString("ratings", String.valueOf(ratings));
//        b.putString("username", sp.getString("username", null));
//        b.putString("productId", productId);
//        b.putString("businessName", sp.getString("businessName", null));
//        b.putString("payBillNumber", sp.getString("payBillNumber", null));
//        b.putString("passkey", sp.getString("passkey", null));
//        b.putString("businessPhoneNumber", sp.getString("businessPhoneNumber", null));
//        b.putString("key", key);
//        i.putExtras(b);
//        activity.startActivity(i);
//        activity.finish();

        if (!Utils.networkIsAvailable(activity.getApplicationContext())) {
            Toast.makeText(activity.getApplicationContext(), "You are offline. The promotion will be uploaded when you have an internet connection", Toast.LENGTH_LONG).show();
        }
    }

//    public static void addRating(final Context activity, Firebase firebase, Ratings ratings, SharedPreferences sp, String title, String comment, String username, int ratingCount, float ratingValue, String rating, String timestamp, String key) {
//        Firebase promotionRef = firebase.child("promotions/" + key + "/ratings");
//
//        Map<String, String> promotion = new HashMap<>();
//        promotion.put("username", username);
//        promotion.put("title", title);
//        promotion.put("comment", comment);
//        promotion.put("rating", rating);
//        promotion.put("timestamp", new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date()));
//        promotionRef.push().setValue(promotion, new Firebase.CompletionListener() {
//            @Override
//            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
//                if (firebaseError != null) {
//                    Toast.makeText(activity.getApplicationContext(), "Error saving this promotion", Toast.LENGTH_SHORT).show();
//                    System.out.println("Promotion Data: " + firebaseError.getMessage());
//                } else {
//                    //This isn't workning for some reason
//                }
//            }
//        });
//
//        ratingCount = ratingCount + 1;
//        ratingValue = (ratingValue + Float.parseFloat(rating)) / ratingCount;
//
//        Firebase promotionRef2 = firebase.child("promotions/" + key);
//        Map<String, Object> promotion2 = new HashMap<>();
//        promotion2.put("ratingsCount", ratingCount);
//        promotion2.put("ratingsValue", ratingValue + "");
//        promotionRef2.updateChildren(promotion2, new Firebase.CompletionListener() {
//            @Override
//            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
//                if (firebaseError != null) {
//                    Toast.makeText(activity.getApplicationContext(), "Error editing this promotion", Toast.LENGTH_SHORT).show();
//                    System.out.println("Promotion Data: " + firebaseError.getMessage());
//                } else {
//                    //This isn't working for some reason
//                }
//            }
//        });
//
//        final UserRatingsEntity userRating = new UserRatingsEntity(key);
//        userRating.save();
//    }
}
