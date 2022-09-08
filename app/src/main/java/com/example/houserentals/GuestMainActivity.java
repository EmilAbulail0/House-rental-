package com.example.houserentals;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class GuestMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    public static int flag = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_main);
        drawer = findViewById(R.id.guest_drawer_layout);
        NavigationView navigationView = findViewById(R.id.tenant_navagation_view);
        navigationView.setNavigationItemSelectedListener(this);

    //   getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new FavFrag()).commit();

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.guest_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FavoriteFiveFrag()).commit();
                break;

            case R.id.guest_search:
                flag = 1;
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new GuestSearchFrag()).commit();
                break;

            case R.id.guest_sign:
                Intent nextIntent = new Intent(getApplicationContext(), SignInUp.class);
                nextIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(nextIntent);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}