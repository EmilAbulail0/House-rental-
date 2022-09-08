package com.example.houserentals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    Button connectButton;
    ArrayList<HouseProperties> housesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity);
        connectButton = findViewById(R.id.connect);
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectionAsyncTask connectionAsyncTask= new ConnectionAsyncTask(MainActivity.this);
                connectionAsyncTask.execute("https://run.mocky.io/v3/01eb2c97-172d-46eb-b29c-83143bc70251");
            }
        });
    }

    public void readHouses(ArrayList<HouseProperties> houseList) {
        if (houseList == null) {
            Toast.makeText(this, "Connecting failed, please try again",Toast.LENGTH_LONG).show();
        } else {
            housesList = houseList;
            houseRentalDataBaseHelper myDataBase =new houseRentalDataBaseHelper(MainActivity.this,"HOUSE",null,1);            //myDataBase.deleteAllHouses();

            for (HouseProperties house : housesList){
                myDataBase.insertHouse(house,"");
            }

            Toast.makeText(this, "Connected successfully", Toast.LENGTH_LONG).show();

            Intent nextIntent=new Intent(MainActivity.this,
                    SignInUp.class);
            MainActivity.this.startActivity(nextIntent);

        }
    }
}