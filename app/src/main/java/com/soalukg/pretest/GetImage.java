package com.soalukg.pretest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by neha5 on 09-02-2017.
 */

public class GetImage extends AsyncTask<String,Void,Bitmap> {
    IImage activity;

    public GetImage(IImage activity) {
        this.activity = activity;
    }

    @Override
    protected Bitmap doInBackground(String... params) {

        try {
            URL url = new URL(params[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            Bitmap image = BitmapFactory.decodeStream(conn.getInputStream());
            return image;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        activity.sendImage(bitmap);
    }

    public static interface IImage{
        public void sendImage(Bitmap img);
    }
}
