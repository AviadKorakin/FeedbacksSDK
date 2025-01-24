package com.feedbackslibary;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

public class MetadataUtil {

    public static String getApiKey(Context context) {
        try {
            ApplicationInfo appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            return appInfo.metaData.getString("com.feedbackslibary.API_KEY");
        } catch (PackageManager.NameNotFoundException | NullPointerException e) {
            e.printStackTrace();
            return null; // Return null if the API key is not found
        }
    }
}
