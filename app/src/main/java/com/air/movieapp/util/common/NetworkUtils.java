package com.air.movieapp.util.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.air.movieapp.network.CacheType;


public class NetworkUtils {

    private Context mContext;
    public static final String TOP_RATED = "top_rated";
    public static final String POPULAR = "popular";
    public static final String UPCOMING = "upcoming";

    public NetworkUtils(Context context) {
        this.mContext = context;
    }


    /**
     * Check if the network is connected
     *
     * @return true/false if network is connected or not
     */
    public boolean isNetworkConnected() {
        boolean isConnected = false;
        if (mContext == null) return false;
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
            if (networkInfo != null) {
                for (NetworkInfo aNetworkInfo : networkInfo) {
                    if (aNetworkInfo.isConnected()) {
                        isConnected = true;
                        break;
                    }
                }
            }
        }

        return isConnected;
    }

    /**
     * Check if the current network is WiFi
     *
     * @return true/false if current network is WiFi or not
     */
    public boolean isConnectedWifi() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
        }
        return false;
    }


    public CacheType setCacheType(CacheType cacheType) {
        if (isNetworkConnected()) {
            return cacheType;
        } else {
            return CacheType.CACHE;
        }

    }

}
