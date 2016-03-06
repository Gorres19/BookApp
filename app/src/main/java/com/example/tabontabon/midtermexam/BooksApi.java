package com.example.tabontabon.midtermexam;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gorres and chember on 3/5/2016.
 *
 */
public class BooksApi {

    public static final String BASE_URL = "http://joseniandroid.herokuapp.com/api/books";

    public static final String TAG_TITLE = "title";
    public static final String TAG_GENRE = "genre";
    public static final String TAG_AUTHOR = "author";
    public static final String TAG_ISREAD = "isread";


    public static Books getBooks(Uri uri, @NonNull String requestMethod){
        String json = HttpUtils.getResponse(uri,requestMethod);

        if(TextUtils.isEmpty(json)){
            return  null;
        }

        //Here we will now parse the json response and convert into a Book object
        String title;
        String genre;
        String author;
        Boolean isRead;



        Log.d("Response: ", "> " + json);
        if (json != null) {
            try {

                JSONArray jsonArray = new JSONArray(json);
                for (int n = 0; n < jsonArray.length(); n++) {
                    JSONObject obj = jsonArray.getJSONObject(n);
                    title = obj.getString(TAG_TITLE);
                    genre = obj.getString(TAG_GENRE);
                    author = obj.getString(TAG_AUTHOR);
                    isRead = obj.getBoolean(TAG_ISREAD);

                //Setting new books object
                    Books books = new Books();
                    books.setmTitle(title);
                    books.setmGenre(genre);
                    books.setmAuthor(author);
                    books.setIsRead(isRead);
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
        }

        return null;

    }

}
