package com.namjongbin.fridge_angel;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //ani
    Animation openFab, closeFab;

    //objects
    private DrawerLayout drawer;
    FloatingActionButton main_fab, look_fab, non_fab, voice_fab;
    TextView lookFabText, nonFabText, voiceFabText;

    //variable
    boolean openClose = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            //Toast.makeText(getApplicationContext(),"눌림",Toast.LENGTH_LONG).show();
        } else {
            Fragment fg = getVisibleFragment();
            if (fg instanceof HomeFragment)
                super.onBackPressed();
            else
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment, new HomeFragment()).commit();
        }
    }

    public Fragment getVisibleFragment() {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment.isVisible()) {
                Log.d("asd", "찾음");
                return fragment;
            }
        }
        Log.d("asd", "못찾음");
        return null;
    }

    @Override
    public void onResume() {
        super.onResume();

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

        //if (savedInstanceState == null) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment, new HomeFragment()).commit();
        navigationView.setCheckedItem(R.id.nav_home);
        // }
        //nav bar

        //fab
        main_fab = (FloatingActionButton) findViewById(R.id.fab);
        look_fab = (FloatingActionButton) findViewById(R.id.fab_cal);
        voice_fab = (FloatingActionButton) findViewById(R.id.fab_voice);
        non_fab = (FloatingActionButton) findViewById(R.id.fab_non);
        lookFabText = findViewById(R.id.calFabText);
        voiceFabText = findViewById(R.id.voiceFabText);
        nonFabText = findViewById(R.id.nonFabText);

        openFab = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.open_fab);
        closeFab = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.close_fab);

        main_fab.setClickable(true);
        main_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (openClose) {
                    non_fab.startAnimation(closeFab);
                    look_fab.startAnimation(closeFab);
                    voice_fab.startAnimation(closeFab);
                    voice_fab.setClickable(false);
                    look_fab.setClickable(false);
                    non_fab.setClickable(false);
                    openClose = false;

                    lookFabText.setVisibility(View.GONE);
                    nonFabText.setVisibility(View.GONE);
                    voiceFabText.setVisibility(View.GONE);
                } else//open
                {
                    voice_fab.startAnimation(openFab);
                    look_fab.startAnimation(openFab);
                    non_fab.startAnimation(openFab);
                    look_fab.setClickable(true);
                    voice_fab.setClickable(true);
                    non_fab.setClickable(true);
                    openClose = true;

                    lookFabText.setVisibility(View.VISIBLE);
                    nonFabText.setVisibility(View.VISIBLE);
                    voiceFabText.setVisibility(View.VISIBLE);
                }
            }
        });
        voice_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RecognizerFragment.class);
                startActivity(intent);
            }
        });

        look_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),CalendarViewer.class);
                startActivity(intent);
            }
        });

        non_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
                startActivity(intent);
            }
        });
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

            non_fab.startAnimation(closeFab);
            look_fab.startAnimation(closeFab);
            voice_fab.startAnimation(closeFab);
            voice_fab.setClickable(false);
            look_fab.setClickable(false);
            non_fab.setClickable(false);
            openClose = false;

            lookFabText.setVisibility(View.GONE);
            nonFabText.setVisibility(View.GONE);
            voiceFabText.setVisibility(View.GONE);

            main_fab.show();
        } else if (id == R.id.nav_settings) {

            getFragmentManager().beginTransaction().replace(R.id.frame_fragment, new SettingsScreen()).commit();
            if (openClose == true) {
                non_fab.startAnimation(closeFab);
                look_fab.startAnimation(closeFab);
                voice_fab.startAnimation(closeFab);
                voice_fab.setClickable(false);
                look_fab.setClickable(false);
                non_fab.setClickable(false);
                openClose = false;

                lookFabText.setVisibility(View.GONE);
                nonFabText.setVisibility(View.GONE);
                voiceFabText.setVisibility(View.GONE);
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
