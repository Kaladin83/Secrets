package com.example.maratbe.secrets;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by MARATBE on 12/14/2017.
 */

public class NavigationPanel extends AppCompatActivity
{
    private static int screenHeight, screenWidth;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle mToggle;
    private Intent intent;
    private NavigationView  navigationView;
    private Toolbar mToolb;
    private ArrayList<Integer> arrayOfColors = new ArrayList<>();
    private static ArrayList<Item> arrayOfItems = new ArrayList<>();

    public Toolbar getToolbar()
    {
        return mToolb;
    }

    public int getRandomColor(int i)
    {
        return arrayOfColors.get(i);
    }

    public static int getScreenHeight()
    {
        return screenHeight;
    }

    public static ArrayList<Item> getArrayOfItems()
    {
        return arrayOfItems;
    }

    public static void setArrayOfItems(Item item)
    {
        arrayOfItems.add(item);
    }

    public static int getScreenWidth()
    {
        return screenWidth;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        loadColors();
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.blue_status_bar));
        super.onCreate(savedInstanceState);
        setDimentions();
        switch (MainActivity.screen)
        {
            case 1: setContentView(R.layout.activity_main);       break;
            case 2: setContentView(R.layout.activity_my_account); break;
            case 3: setContentView(R.layout.activity_add_secret); break;
        }
        drawer = (DrawerLayout) findViewById(R.id.mDrawer_layout);
        mToolb = (Toolbar) findViewById(R.id.my_action_bar);
        mToggle = new ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close) ;
        drawer.addDrawerListener(mToggle);
        mToggle.syncState();
        setSupportActionBar(mToolb);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                return chooseNavigationMenuAction(item);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void loadColors()
    {
        arrayOfColors.add(ContextCompat.getColor(this, R.color.top_1));
        arrayOfColors.add(ContextCompat.getColor(this, R.color.top_2));
        arrayOfColors.add(ContextCompat.getColor(this, R.color.top_3));
        arrayOfColors.add(ContextCompat.getColor(this, R.color.top_4));
        arrayOfColors.add(ContextCompat.getColor(this, R.color.top_5));
        arrayOfColors.add(ContextCompat.getColor(this, R.color.top_6));
        arrayOfColors.add(ContextCompat.getColor(this, R.color.top_7));
        arrayOfColors.add(ContextCompat.getColor(this, R.color.top_8));
        arrayOfColors.add(ContextCompat.getColor(this, R.color.top_9));
        arrayOfColors.add(ContextCompat.getColor(this, R.color.top_10));
    }

    public void setDimentions()
    {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;
    }

    public boolean chooseNavigationMenuAction(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.new_secret:
                intent = new Intent(this, AddSecret.class);
                startActivity(intent);
                // call add secret
                Toast.makeText(this,"new secret", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.see_secret:
                // call see all secrets
                Toast.makeText(this,"see secrets", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.new_thought:
                // call add thought
                //  Toast.makeText(this,"new secret", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.see_thoughts:
                // call see all thoughts
                Toast.makeText(this,"see secrets", Toast.LENGTH_SHORT).show();
                return true;
            default:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                // call see all thoughts
                Toast.makeText(this,"see secrets", Toast.LENGTH_SHORT).show();
                return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (mToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        else
        {
            switch (item.getItemId())
            {
                case R.id.account:
                    Intent intent = new Intent(NavigationPanel.this, MyAccount.class);
                    startActivity(intent);
                    // call view account
                    Toast.makeText(this, "view account", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.settings:
                    // call settings
                    Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                    return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
}
