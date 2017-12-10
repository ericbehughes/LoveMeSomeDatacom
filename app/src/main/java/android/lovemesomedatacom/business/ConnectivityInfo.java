package android.lovemesomedatacom.business;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * The ConnectivityInfo class is a simple class with one method to be shared amongst all Activities
 * in this app. It checks for connectivity before INTERNET operations.
 *
 * @author Sebastian Ramirez
 */

public class ConnectivityInfo {

    private Activity context;

    /**
     * Non-default constructor
     *
     * @param context The activity calling this class
     */
    public ConnectivityInfo(Activity context){
        this.context = context;
    }

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}
