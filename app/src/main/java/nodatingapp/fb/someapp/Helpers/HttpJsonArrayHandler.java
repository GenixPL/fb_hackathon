package nodatingapp.fb.someapp.Helpers;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HttpJsonArrayHandler extends AsyncTask<String, Void, String> {
    private static final String TAG = HttpJsonArrayHandler.class.getSimpleName();

    private String reqUrl;
    private HttpHandler.Type callType;

    private JSONArray jsonArray;

    private HttpURLConnection conn;

    public interface IOnRequestFinished {
        void onRequestFinished(String output);
    }

    private HttpHandler.IOnRequestFinished iOnRequestFinished;

    public HttpJsonArrayHandler(String reqUrl, HttpHandler.Type type, HttpHandler.IOnRequestFinished iOnRequestFinished) {
        this.reqUrl = reqUrl;
        this.callType = type;
        this.iOnRequestFinished = iOnRequestFinished;
    }

    public void setJsonObject(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    public String makeServiceCall() {
        String response = null;

        try {
            URL url = new URL(reqUrl);

            byte[] postDataBytes = jsonArray != null ? jsonArray.toString().getBytes("UTF-8") : new byte[0];

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(callType.getType());
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoInput(true);

            if (callType == HttpHandler.Type.POST) {
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
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException Handler: " + e);
        } catch (Exception e) {
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
}
