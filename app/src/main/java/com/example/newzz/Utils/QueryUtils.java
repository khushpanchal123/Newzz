package com.example.newzz.Utils;

import android.text.TextUtils;
import android.util.Log;

import com.example.newzz.database.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getName();
    private QueryUtils(){

    }

    public static List<News> extractNews(String jsonResponse) throws IOException, JSONException {

        URL url = new URL(jsonResponse);
        String jsonString = makeHttpRequest(url);
        return extractFeatureFromJSON(jsonString);
        //return extractFeatureFromJSON(jsonResponse);
    }

    public static List<News> extractFeatureFromJSON(String jsonString) throws JSONException {

        if (TextUtils.isEmpty(jsonString)) return null;
        List<News> newsLists = new ArrayList<>();

        JSONObject root = new JSONObject(jsonString);
        JSONArray features = root.getJSONArray("articles");
        for (int i = 0; i < features.length(); i++) {
            JSONObject currentArticle = features.getJSONObject(i);
            String title = currentArticle.getString("title");
            String url = currentArticle.getString("url");
            String imageUrl = currentArticle.getString("urlToImage");
            if(!imageUrl.equals("null")){
                News news = new News(imageUrl, title, url);
                newsLists.add(news);
            }
        }
        String x = String.valueOf(newsLists.size());
        Log.i("QueryutilsJava", x);

        return newsLists;

    }

    private static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = "";
        if (url == null) return jsonResponse;

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:221.0) Gecko/20100101 Firefox/31.0");
            urlConnection.setReadTimeout(30000 /* milliseconds */);
            urlConnection.setConnectTimeout(60000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }


}
