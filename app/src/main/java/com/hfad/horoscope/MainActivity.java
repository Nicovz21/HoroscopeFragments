package com.hfad.horoscope;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
//add access to the HoroscopeHandler class, url, LOG_TAG, and ShareActionProviders
    HoroscopeHandler horoscopeHandler = new HoroscopeHandler();

    boolean userSelect = false;
    private String url1 = "https://devbrewer-horoscope.p.rapidapi.com/today/short/";
    private String LOG_TAG = MainActivity.class.getSimpleName();
    private ShareActionProvider provider;
    boolean isRed = false;
    //create options to go in menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        //create item for search bar
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        //to search for items
        SearchView searchView = (SearchView) searchItem.getActionView();
        provider = (ShareActionProvider) MenuItemCompat.getActionProvider((MenuItem) menu.findItem(R.id.share));
        return super.onCreateOptionsMenu(menu);
    }
  

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//create spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

//initialized and create array adapter
        ArrayAdapter<String> horoscopeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, horoscopeHandler.horoscopes);
//set the drop down view
        horoscopeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//give spinner adapter and listener
        spinner.setAdapter(horoscopeAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //for when a sign is selected, fetch the text associated
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    final String horoscope = (String) parent.getItemAtPosition(position);
                    Log.i("onItemSelected :sign", horoscope);

                    new FetchHoroscopeFact().execute(horoscope);
                    userSelect = false;
                }
            }
            //if nothing is selected then do nothing
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //set action bar to the toolbar made in xml
        setSupportActionBar(findViewById(R.id.toolbar));
    }

//what to do when menu item is selected and add it to toast
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.help:
                Toast.makeText(this, "Help", Toast.LENGTH_LONG).show();
                //send user to help/info screen
                Intent intentHelp = new Intent(MainActivity.this, HelpScreen.class);
                startActivity(intentHelp);
                break;
            case R.id.invert:
                Toast.makeText(this, "Color is now red", Toast.LENGTH_LONG).show();
                //Send user to color changer screen
                setMyScreenColor(Color.RED);
                //add popup to display info about me and this app
                break;
            case R.id.share:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "This is a message for you");
                provider.setShareIntent(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
//if the user does select an option set boolean to true
    @Override
public void onUserInteraction() {
    super.onUserInteraction();
    userSelect = true;

}
//associate needed fact with sign picked, and use buffered reader to grab the needed text.
    //This class also establishes the connection to the url and all properties needed for the API
    class FetchHoroscopeFact extends AsyncTask<String ,Void,String> {
        @Override
        protected String doInBackground(String... strings) {
            HttpsURLConnection urlConnection = null;
            BufferedReader reader = null;
            String horoscopeFact = null;
            try{
                URL url = new URL(url1 + strings[0]);
                urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("x-rapidapi-key","UygwA3LnI1mshAPcqbrTdu6rvUkxp1Kd1q6jsnETjeLq2t3LzS");
                urlConnection.setRequestProperty("x-rapidapi-host", "devbrewer-horoscope.p.rapidapi.com");
                urlConnection.setRequestProperty("useQueryString", "true");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Postman-Token","46a92a59-f84d-ee8b-0db4-ee9128390c41");
                urlConnection.connect();
                Log.i("connection", "connected");
                InputStream in = urlConnection.getInputStream();
                //if connection grabs nothing
                if(in == null) {
                    Log.i("stream null", "stream null");

                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(in));
                horoscopeFact = getStringFromBuffer(reader);
                Log.i("factsreader", horoscopeFact);
//in case the connection fails
            }catch (Exception e){
                Log.e(LOG_TAG, "Error " + e.getMessage());
                return null;
                //disconnect connection
            }finally {
                if(urlConnection != null){
                    urlConnection.disconnect();
                }
                //close reader
                if (reader != null){
                    try {
                        reader.close();
                    }catch (IOException e){
                        Log.e(LOG_TAG, "Error IOError" + e.getMessage());
                        return null;
                    }
                }

            }
            return horoscopeFact;
        }
        //Use the fact that was pulled from the API and put it into an intent to go to the screen that displays the fact
        @Override
        protected void onPostExecute(String result) {
            if(result != null)
                Log.d(LOG_TAG, "Result is null");
            Intent intent = new Intent(MainActivity.this,HoroscopeFactActivity.class);
            intent.putExtra("horoscopeFact", result);
            startActivity(intent);
        }
    }
//Read the information into a string variable, so java can display it on a textView properly
    private String getStringFromBuffer(BufferedReader bufferedReader) {
        StringBuffer buffer = new StringBuffer();
        String line = "";

        if(bufferedReader != null){
            try{
                //while((
                line = bufferedReader.readLine();
                    System.out.println(line);
                    buffer.append(line + '\n');

                bufferedReader.close();
                return horoscopeHandler.getSignFact(buffer.toString());
                //return horoscopeHandler.getMatchFact(buffer.toString());
            }catch (Exception e){
                Log.e("MainActivity", "Error in getStringFromBuffer" + e.getMessage());
                return line;
            }
        }
        return line;

    }
    //method to change the color of the screen
    public void setMyScreenColor(int color){
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(color);
    }

}
