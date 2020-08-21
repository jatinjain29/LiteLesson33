package com.example.teacherdashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class BaseNavigation extends AppCompatActivity {
Toolbar toolbar;
ActionBarDrawerToggle drawerToggle;
DrawerLayout drawerLayout;
NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_navigation);
        toolbar=findViewById(R.id.toolbar);
        drawerLayout=findViewById(R.id.ldrawer);
        navigationView=findViewById(R.id.navigation);

        setSupportActionBar(toolbar);
        drawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.activity_dashboard:
                    {
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        break;
                    }
                    case R.id.activity_Cassingment:{
                        startActivity(new Intent(getApplicationContext(),CreateAssingment.class));
                        break;
                    }
                    case R.id.activity_classSchedule:{
                        startActivity(new Intent(getApplicationContext(),ClassSchedule.class));

                        break;
                    }
                    case R.id.activity_startclass:{
                        startActivity(new Intent(getApplicationContext(),StartClass.class));
                        break;
                    }
                    case R.id.activty_Vassingment:{
                        startActivity(new Intent(getApplicationContext(),ViewAssingment.class));
                    }
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });




    }
}