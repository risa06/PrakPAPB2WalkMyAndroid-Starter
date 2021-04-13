package com.example.android.walkmyandroid;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.text.TextUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FetchAddresstask extends AsyncTask<Location, Void, String> {
    private Context mContext;
    private OnTaskCompleted mListener;
    FetchAddresstask(Context context, OnTaskCompleted listener) {
        mContext = context;
        mListener = listener;
    }

    @Override
    protected String doInBackground(Location... locations) {
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        Location location = locations[0];
        List<Address> addresses = null;
        String resultMsg = "";
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(),
                    location.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses == null || addresses.size() == 0) {
            resultMsg = "No Address Found";
        } else {
            Address address = addresses.get(0);
            ArrayList<String> addressParis = new ArrayList<>();
            for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                addressParis.add(address.getAddressLine(i));
            }
            resultMsg = TextUtils.join("\n", addressParis);
        }

        return resultMsg;
    }

    @Override
    protected void onPostExecute(String s) {
        mListener.onTaskCompleted(s);
        super.onPostExecute(s);
    }

    interface OnTaskCompleted {
        void onTaskCompleted(String result);
    }
}
