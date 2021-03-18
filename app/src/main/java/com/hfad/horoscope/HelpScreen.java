package com.hfad.horoscope;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class HelpScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_screen);
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
        return super.onCreateOptionsMenu(menu);
    }
    //re-initalize what each button on action bar does (since this is on a new screen, we want different actions and messages to appear when clicked)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.help:
                Toast.makeText(this, "Refresh", Toast.LENGTH_LONG).show();
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
                Toast.makeText(this, "Nothing to share currently", Toast.LENGTH_LONG).show();
                break;
            case R.id.home:
                Intent intentBack = new Intent(HelpScreen.this, MainActivity.class);
                startActivity(intentBack);
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