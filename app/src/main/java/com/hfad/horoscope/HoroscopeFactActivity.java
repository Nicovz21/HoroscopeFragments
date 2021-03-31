package com.hfad.horoscope;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

public class HoroscopeFactActivity extends AppCompatActivity {

  private String starSignFact = "";
//    private HoroscopeHandler handler;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.horoscope_detail);
//
//        String starSignFact = (String) getIntent().getExtras().get("horoscopeFact");
//         //= getIntent().getExtras().getString("starSignFact");
//        HoroscopeDetail frag = (HoroscopeDetail) getSupportFragmentManager().findFragmentById(R.id.fragment_horoscope_detail);
//        frag.setHoroscopeFact(starSignFact);
//    }








    private ShareActionProvider provider;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //set content view
        setContentView(R.layout.activity_horoscope_fact);

        String starSignFact = (String) getIntent().getExtras().get("horoscopeFact");
//         //= getIntent().getExtras().getString("starSignFact");
        HoroscopeDetail frag = (HoroscopeDetail) getSupportFragmentManager().findFragmentById(R.id.fragment_horoscope_detail);
        frag.setHoroscopeFact(starSignFact);
        //set text from last screen to this one using intents
//        String fact = (String) getIntent().getExtras().get("horoscopeFact");
//        TextView textView = (TextView) findViewById(R.id.textView);
//        textView.setText(fact);
//add toolbar to second screen
        setSupportActionBar(findViewById(R.id.toolbar));
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

    }
//create inflator
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        //add provider again to share this screen
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        //provider = (ShareActionProvider) MenuItemCompat.getActionProvider((MenuItem) menu.findItem(R.id.share));
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.help:
                Toast.makeText(this, "Help", Toast.LENGTH_LONG).show();
                //send user to help/info screen
                Intent intentHelp = new Intent(this, HelpScreen.class);
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
    //method to change the color of the screen
    public void setMyScreenColor(int color){
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(color);
    }
}