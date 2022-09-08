package com.example.houserentals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class SignInUp extends AppCompatActivity {
    HouseRentalsSPM houseRentalsSPM;
    EditText email;
    EditText password;
    CheckBox rememberMe;
    Button loginButton, guestButton;
    PrintToast toast =new PrintToast();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singinup);
        houseRentalsSPM = HouseRentalsSPM.getInstance(this);
        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String recoverd_email = sharedPreferences.getString("email","");
        String recoverd_pass = sharedPreferences.getString("password","");
        email=findViewById(R.id.email_field);
        email.setText(recoverd_email);
        password=findViewById(R.id.password_field);
        password.setText(recoverd_pass);
        rememberMe =findViewById(R.id.rememberMeCB);
        loginButton =findViewById(R.id.login_button);
        guestButton = findViewById(R.id.guest_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
                        Toast toast = Toast.makeText(SignInUp.this, "Please fill email and password field and try to login", Toast.LENGTH_LONG);
                        toast.show();
                    } else {
                        houseRentalDataBaseHelper dataBaseHelper =new houseRentalDataBaseHelper(SignInUp.this,"USER",null,1);
                        boolean registeredUser = dataBaseHelper.checkUser(email.getText().toString(),password.getText().toString());

                        if (registeredUser) {
                            UserClass user = new UserClass();
                            for (UserClass User : user.allUsers) {
                                if(User.getEmail().equals(email.getText().toString())){
                                    if(User.isAgencyFlag()){ ///Agency
                                        houseRentalsSPM.writeString("email",email.getText().toString());
                                        Intent nextIntent = new Intent(SignInUp.this, AgencyMainActivity.class);
                                        SignInUp.this.startActivity(nextIntent);
                                        finish();
                                        Toast toast = Toast.makeText(SignInUp.this, "You have logged in successfully as agency", Toast.LENGTH_LONG);
                                        toast.show();

                                    }
                                    else { ///Tenant
                                        houseRentalsSPM.writeString("email",email.getText().toString());
                                        Intent nextIntent = new Intent(SignInUp.this, TenantMainActivity.class);
                                        SignInUp.this.startActivity(nextIntent);
                                        finish();
                                        Toast toast = Toast.makeText(SignInUp.this, "You have logged in successfully as tenant", Toast.LENGTH_LONG);
                                        toast.show();
                                    }
                                }
                            }

                            SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            if(rememberMe.isChecked()){
                                editor.putString("email",email.getText().toString());
                                editor.commit();
                                editor.putString("password",password.getText().toString());
                                editor.commit();
                            }
                            editor.putString("loggedIn",email.getText().toString());
                            editor.commit();

                        } else {
                            Toast toast = Toast.makeText(SignInUp.this, "Wrong email or password, please check", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }

            }
        });
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;

        return true;
    }
    public void Sign(View view) {
        if(!isEmpty(email) ){
            toast.message(this,"Fill the empty Email field");
        }
             else   if(!isEmpty(password)){
                    toast.message(this,"Fill the empty Password field");
                }
             else if(!isValidEmail(email.getText().toString())) {

             }
             else {

             }
    }
    public static boolean isValidPassword(String s) {
        Pattern PASSWORD_PATTERN
                = Pattern.compile(
                "[a-zA-Z0-9\\!\\@\\#\\$]{8,24}");

        return !TextUtils.isEmpty(s) && PASSWORD_PATTERN.matcher(s).matches();
    }
    public void Renting(View view) {
        Intent intent = new Intent(this, RentingAgencySignUp.class);
        startActivity(intent);
    }

    public void as_Tenant(View view) {
        Intent intent = new Intent(this, RentingTenantSignUp.class);
        startActivity(intent);
    }
    public void as_guest(View view) {
        Intent intent = new Intent(this, GuestMainActivity.class);
        startActivity(intent);
    }
}
