package com.appdesigndm.loc2loc;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_CONTACTS;

public class PermissionUtils {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;
    private static final int REQUEST_ACCES_FINE_LOCATION = 1;

    /**
     * Check if read contacts permission is requested and request if needed
     *
     * @param activity
     * @param view
     * @return
     */
    public static boolean mayRequestContacts(final Activity activity, View view) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (activity.checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (activity.shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(view, R.string.contact_permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            activity.requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            activity.requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Check if location permission is requested and request if needed
     *
     * @param activity
     * @param view
     * @return
     */
    public static boolean mayRequestLocation(final Activity activity, View view) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (activity.checkSelfPermission(ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (activity.shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
            Snackbar.make(view, R.string.contact_permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            activity.requestPermissions(new String[]{ACCESS_FINE_LOCATION}, REQUEST_ACCES_FINE_LOCATION);
                        }
                    });
        } else {
            activity.requestPermissions(new String[]{ACCESS_FINE_LOCATION}, REQUEST_ACCES_FINE_LOCATION);
        }
        return false;
    }

    /**
     * Called on callback received when a permissions request has been completed.
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     * @return if permission is Granted
     */
    public static boolean isPermissionGranted(int requestCode, @NonNull String[] permissions,
                                              @NonNull int[] grantResults) {
        if ((requestCode == REQUEST_READ_CONTACTS) ||
                (requestCode == REQUEST_ACCES_FINE_LOCATION)) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }

        return false;
    }

}