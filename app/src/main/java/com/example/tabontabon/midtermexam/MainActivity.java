package com.example.tabontabon.midtermexam;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.tabontabon.midtermexam.HttpUtils.getResponse;

public class MainActivity extends ListActivity {


    // URL to get contacts JSON
    private static final String BASE_URL = "http://joseniandroid.herokuapp.com/api/books";

    // JSON Node names
    private static final String TAG_BOOKTITLE = "title";
    private static final String TAG_ISREAD = "isRead";

    private ProgressDialog pDialog;
    TextView tvBooktitle;
    private String title;
    private Boolean isRead;



    // Hashmap for ListView
    ArrayList<HashMap<String, String>> bookList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvBooktitle = (TextView) findViewById(R.id.bookName);

        bookList = new ArrayList<HashMap<String, String>>();
        ListView listView = getListView();

        // Calling async task to get json
        new GetBooks().execute();
    }


    /**
     * Async task class to get json by making HTTP call
     */
    private class GetBooks extends AsyncTask<String, Void, Books> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Getting Books...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Books doInBackground(String... arg0) {
            /***
             * Shua ari ko na ungot. mo run ni cya. peru ang BooksApi Class dli magamit.
             * tan-awa lng. anhi rko nadugay. dli ka proceed kung dli ni mahuman. basta dre ra dyud
             * mkahasol. 
             */

            String bookname;
            String isRead ;

            String jsonStr = getResponse(BASE_URL, "GET");
            Log.d("Response: ", "> " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONArray jsonArray = new JSONArray(jsonStr);
                    for (int n = 0; n < jsonArray.length(); n++) {
                        JSONObject obj = jsonArray.getJSONObject(n);
                        bookname = obj.getString(TAG_BOOKTITLE);
                        isRead = obj.getString(TAG_ISREAD);
                        // adding each child node to HashMap key => value
                        HashMap<String, String> book = new HashMap<String, String>();
                        book.put(BooksApi.TAG_TITLE,bookname);
                        bookList.add(book);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Books result) {
            super.onPostExecute(result);

            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */

         //  if(isRead.equals(1)){
        //        tvBooktitle.setPaintFlags(tvBooktitle.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
          //  }
            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, bookList,
                    R.layout.list_item, new String[]{TAG_BOOKTITLE,}, new int[]{R.id.bookName,});
            setListAdapter(adapter);
        }

    }
}
