package com.namjongbin.fridge_angel;

import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //ani
    Animation openFab,closeFab;

    //objects
    private DrawerLayout drawer;
    FloatingActionButton main_fab,look_fab,non_fab;


    //variable
    boolean openClose=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //tool bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState==null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
        //nav bar

        //fab
        main_fab=(FloatingActionButton)findViewById(R.id.fab);
        look_fab=(FloatingActionButton)findViewById(R.id.fab_look);
        non_fab=(FloatingActionButton)findViewById(R.id.fab_non);

        openFab= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.open_fab);
        closeFab= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.close_fab);

        main_fab.setClickable(true);
        main_fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(openClose)
                {
                    non_fab.startAnimation(closeFab);
                    look_fab.startAnimation(closeFab);
                    look_fab.setClickable(false);
                    non_fab.setClickable(false);
                    openClose=false;
                }
                else//open
                {
                    look_fab.startAnimation(openFab);
                    non_fab.startAnimation(openFab);
                    look_fab.setClickable(true);
                    non_fab.setClickable(true);
                    openClose=true;
                }
                Toast.makeText(getApplication(),"눌렀어?",Toast.LENGTH_SHORT).show();
            }
        });
        look_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),RecognizerFragment.class);
                startActivity(intent);
            }
        });

        non_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment, new HomeFragment()).commit();
            main_fab.show();
        } else if (id == R.id.nav_character) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment, new CharacterFragment()).commit();
            main_fab.show();
        } else if (id == R.id.nav_settings) {

            getFragmentManager().beginTransaction().replace(R.id.frame_fragment,new SettingsScreen()).commit();
            if(openClose==true)
            {
                non_fab.startAnimation(closeFab);
                look_fab.startAnimation(closeFab);
                look_fab.setClickable(false);
                non_fab.setClickable(false);
                openClose=false;
            }
            main_fab.hide();
        } else if (id == R.id.our) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment, new UsFragment()).commit();
            main_fab.hide();
        } else if (id == R.id.license) {

        }
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
