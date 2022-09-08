package com.example.houserentals;
import android.app.Activity;
import android.os.AsyncTask;
import java.util.ArrayList;
import java.util.List;

public class ConnectionAsyncTask extends AsyncTask<String, String, String> {
    Activity activity;
    public ConnectionAsyncTask(Activity activity) {
        this.activity = activity;

    }
    @Override
    protected String doInBackground(String... params) {
        String data = HttpManager.getData(params[0]);
        return data;

    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        List<HouseProperties> house = null;
        if(s!=null)
            house= JasonParser.getObjectFromJason(s);
        ((MainActivity) activity).readHouses((ArrayList)house);
    }
}
