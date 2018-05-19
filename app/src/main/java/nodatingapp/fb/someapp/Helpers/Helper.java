package nodatingapp.fb.someapp.Helpers;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Helper {

    private static String[] appPermisions = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};

    public static boolean checkPermissions(Activity activity)
    {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : Helper.appPermisions)
        {
            result = ContextCompat.checkSelfPermission(activity, p);
            if (result != PackageManager.PERMISSION_GRANTED)
            {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty())
        {
            ActivityCompat.requestPermissions(activity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 10 );
            return false;
        }
        return true;
    }

    public static String convertStreamToString(String TAG, InputStream is)
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try
        {
            while ((line = reader.readLine()) != null)
                sb.append(line).append('\n');
        }
        catch (IOException e)
        {
            Log.e(TAG, "convertStreamToString 1: " + e.getMessage());
            e.printStackTrace();
        }
        finally
        {
            try
            {
                is.close();
            }
            catch (IOException e)
            {
                Log.e(TAG, "convertStreamToString 2: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
