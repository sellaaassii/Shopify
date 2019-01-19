package simplesendreceive.example.com.shopify.Network;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;


public class DownloadImageBackgroundTask extends AsyncTask<String, Void, Object> {

    @Override
    protected Object doInBackground(String... params) {
        URL imageUrl;
        Bitmap bitmapImage = null;
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;

        try {
            imageUrl = new URL(params[0]);

            httpURLConnection = (HttpURLConnection) imageUrl.openConnection();
            httpURLConnection.setDoInput(true);//url connections is used for input
            httpURLConnection.connect();
            inputStream = httpURLConnection.getInputStream();

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Config.RGB_565; //encode only rgb channels

            bitmapImage = BitmapFactory.decodeStream(inputStream, null, options);//create bitmap object from stream with options

        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmapImage;
    }

}

