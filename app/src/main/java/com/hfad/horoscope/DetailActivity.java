package com.hfad.horoscope;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class DetailActivity extends AppCompatActivity {

  private String starSignFact = "hello";
  private ShareActionProvider provider;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //set content view
        setContentView(R.layout.activity_detail);
        Log.i("moveScreens", "Now on second screen");
        //make the string to go into the fragment
        String starSignFact = (String) getIntent().getExtras().get("horoscopeFact");
        System.out.println(starSignFact);
        //get fragment
        HoroscopeDetail frag = (HoroscopeDetail) getSupportFragmentManager().findFragmentById(R.id.fragment);
        //Add the string to fragment
        frag.setHoroscopeFact(starSignFact);

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