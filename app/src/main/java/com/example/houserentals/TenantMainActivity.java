package com.example.houserentals;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

public class TenantMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_main);
        drawer = findViewById(R.id.tenant_drawer_layout);
        NavigationView navigationView = findViewById(R.id.tenant_navagation_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new FavoriteFiveFrag()).commit();

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

        switch (item.getItemId()){

            case R.id.tenant_main:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new FavoriteFiveFrag()).commit();
                break;
            case R.id.tenant_search:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new GuestSearchFrag()).commit();
                break;

            case R.id.tenant_history:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new TenantHistoryFragment()).commit();
                break;

            case R.id.tenant_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MyProfileFrag()).commit();
                break;

            case R.id.tenant_signout:
                Intent nextIntent = new Intent(getApplicationContext(), SignInUp.class);
                nextIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(nextIntent);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}