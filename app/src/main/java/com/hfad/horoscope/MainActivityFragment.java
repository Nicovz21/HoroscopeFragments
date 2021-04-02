package com.hfad.horoscope;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class MainActivityFragment extends Fragment {

    //add access to the HoroscopeHandler class, url, LOG_TAG, and ShareActionProviders
    HoroscopeHandler horoscopeHandler = new HoroscopeHandler();

    private String url1 = "https://devbrewer-horoscope.p.rapidapi.com/today/short/";
    private String LOG_TAG = MainActivity.class.getSimpleName();
    private MainActivityListenser mActivity;


    public MainActivityFragment() {
        // Required empty public constructor
    }



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        //set action bar to the toolbar made in xml

    }
//create interface for fragment
public interface MainActivityListenser{
        void setHoroscopeOnClick(String fact);
}
    //reference to Main Activity
    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        Log.i("mactivity", "context called");
        mActivity = (MainActivityListenser) activity;

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
            if(result == null)
                Log.d(LOG_TAG, "Result is null");
            Log.i("onPostCalled", "true");
            mActivity.setHoroscopeOnClick(result);

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //variable for myActivity
        Activity myActivity = getActivity();
        //create inflater
        View view =inflater.inflate(R.layout.fragment_main_activity, container, false);

        //create spinner
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);


        //initialized and create array adapter
        ArrayAdapter<String> horoscopeAdapter = new ArrayAdapter<String>(myActivity,android.R.layout.simple_spinner_item, horoscopeHandler.horoscopes);
        //set the drop down view
        horoscopeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //give spinner adapter and listener
        spinner.setAdapter(horoscopeAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //for when a sign is selected, fetch the text associated
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (myActivity.userSelect) {
                    final String horoscope = (String) parent.getItemAtPosition(position);
                    Log.i("onItemSelected :sign", horoscope);

                    new FetchHoroscopeFact().execute(horoscope);
                    myActivity.userSelect = false;
               // }
            }
            //if nothing is selected then do nothing
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Inflate the layout for this fragment
        return view;


    }
}