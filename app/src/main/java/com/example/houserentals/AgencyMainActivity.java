package com.example.houserentals;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class AgencyMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agency_main);
        drawer = findViewById(R.id.agency_layout_drawer);
        NavigationView navigationView = findViewById(R.id.agency_navagation_view);
        navigationView.setNavigationItemSelectedListener(this);
        final Context context = getApplicationContext();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FavoriteFiveFrag()).commit();

        houseRentalDataBaseHelper myDataBase = new houseRentalDataBaseHelper(context, "APPLY", null, 1);
        String allMails = myDataBase.getAllRegisteredEmails();
        if (allMails != null) {

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.a23)
                    .setContentTitle("Go check your Rental Application")
                    .setContentText("New customers want to rent");

            Intent notificationIntent = new Intent(this, AgencyMainActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(contentIntent);

            // Add as notification
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(0, builder.build());
        }

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
            case R.id.agency_post:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PostAPropertyFrag()).commit();
                break;

            case R.id.agency_main:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FavoriteFiveFrag()).commit();
                break;

            case R.id.agency_edit:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EditPropertyFrag()).commit();
                break;

            case R.id.agency_history:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AgencyHistoryFrag()).commit();
                break;

            case R.id.agency_rent_approval:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ApproveRentFrag()).commit();
                break;

            case R.id.agency_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MyProfileFrag()).commit();
                break;

            case R.id.agency_signout:
                Intent nextIntent = new Intent(getApplicationContext(), SignInUp.class);
                nextIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(nextIntent);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}