package nodatingapp.fb.someapp.Helpers;

import android.icu.util.Output;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Edvin on 12/29/16.
 */

public class HttpHandler extends AsyncTask<String, Void, String>
{
    private static final String TAG = HttpHandler.class.getSimpleName();

    private String reqUrl;
    private Type callType;

    private JSONObject jsonObject;

    private HttpURLConnection conn;

    public interface IOnRequestFinished
    {
        void onRequestFinished(String output);
    }

    private IOnRequestFinished iOnRequestFinished;

    public HttpHandler(String reqUrl, Type type, IOnRequestFinished iOnRequestFinished)
    {
        this.reqUrl     = reqUrl;
        this.callType   = type;
        this.iOnRequestFinished = iOnRequestFinished;
    }

    public void setJsonObject(JSONObject jsonObject)
    {
        this.jsonObject = jsonObject;
    }

    public String makeServiceCall()
    {
        String response = null;

        try
        {
            URL url = new URL(reqUrl);

            byte[] postDataBytes = jsonObject.toString().getBytes("UTF-8");

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(callType.getType());
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoInput(true);

            if(callType == Type.POST) {
                conn.setDoOutput(true);

                OutputStream outputStream = conn.getOutputStream();
                outputStream.write(postDataBytes);
                outputStream.flush();
            }
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = Helper.convertStreamToString(TAG, in);

            Log.d(TAG, "Connection Response Code: " + conn.getResponseCode());
            Log.d(TAG, "Response: " + response);
        }
        catch (MalformedURLException e)
        {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        }
        catch (ProtocolException e)
        {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        }
        catch (IOException e)
        {
            Log.e(TAG, "IOException Handler: " + e);
        }
        catch (Exception e)
        {
            Log.e(TAG, "Exception: ", e);
        }

        return response;
    }

    @Override
    protected String doInBackground(String... strings) {
        return makeServiceCall();
    }

    @Override
    protected void onPostExecute(String result) {
        iOnRequestFinished.onRequestFinished(result);
    }

    public enum Type
    {
        POST("POST"),
        GET("GET"),
        PUT("PUT"),
        DELETE("DELETE");

        private String stringValue;
        private Type(String toString)
        {
            stringValue = toString;
        }

        public String getType()
        {
            return stringValue;
        }
    }
}