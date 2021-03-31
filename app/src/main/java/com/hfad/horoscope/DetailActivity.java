package com.hfad.horoscope;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {
    private String starSignFact = "";
    private HoroscopeHandler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        starSignFact = getIntent().getExtras().getString("starSignId");
        HoroscopeDetail frag = (HoroscopeDetail) getSupportFragmentManager().findFragmentById(R.id.fragment_horoscope_detail);
        frag.setHoroscopeFact(starSignFact);
    }

}