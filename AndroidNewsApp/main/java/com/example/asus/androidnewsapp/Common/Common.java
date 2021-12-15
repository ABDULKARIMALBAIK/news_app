package com.example.asus.androidnewsapp.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.asus.androidnewsapp.Remote.IconBatterIdeaClient;
import com.example.asus.androidnewsapp.Remote.IconBetterIdeaService;
import com.example.asus.androidnewsapp.Remote.NewsService;
import com.example.asus.androidnewsapp.Remote.RetrofitClient;

public class Common {

    public static final String BASE_URL = "........................";
    public static final String API_KEY ="..........................";

    public static NewsService getNewService(){

        return RetrofitClient.getInstance(BASE_URL).create(NewsService.class);
    }

    public static IconBetterIdeaService getIconService(){

        return IconBatterIdeaClient.getInstance().create(IconBetterIdeaService.class);
    }

    //https://newsapi.org/v1/
    public static String getAPIUrl(String source , String apiKEY){

        StringBuilder apiUrl = new StringBuilder("..............................");
        return apiUrl.append(source)
                .append("...............")
                .append(apiKEY)
                .toString();
    }

    public static boolean isConnectionToInternet(Context context){

        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null){

            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info != null){

                for (int i = 0; i < info.length; i++) {

                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                        return true;
                }
            }
        }
        return false;
    }
}
