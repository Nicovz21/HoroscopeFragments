package com.hfad.horoscope;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.fragment.app.FragmentTransaction;

import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements MainActivityFragment.MainActivityListenser {

    private ShareActionProvider provider;
    boolean isRed = false;
    boolean userSelect = false;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));
    }

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

//keep here
    public void setHoroscopeOnClick(String fact) {
        View fragmentContainer = findViewById(R.id.fragment_container);
        if(fragmentContainer != null){
            HoroscopeDetail frag = new HoroscopeDetail();
            frag.setHoroscopeFact(fact);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, frag);
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();

        }else
        {
            Intent intent = new Intent(this, DetailActivity.class);
        Log.i("moveScreens", "secondScreen");
            intent.putExtra("horoscopeFact", fact);
            startActivity(intent);
        }
    }

    //method to change the color of the screen
    public void setMyScreenColor(int color){
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(color);
    }

}
