package com.example.houserentals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.security.Key;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class RentingAgencySignUp extends AppCompatActivity {
    HouseRentalsSPM houseRentalsSPM;
    String [] nationalities =new String[]{"Palestinian","Jordanian","Egyptians ","Lebanese","Saudis","Syrian"};
    String [] contries =new String[]{"Palestine","Jordan","Egypt ","Lebanon","Saudi","Syria"};
    String [] palCit =new String[]{"Rammallah","Jerusalem","Nablus", "Jenin"};
    String [] jorCit =new String[]{"Amman","irbid","Zarqa"};
    String [] eygCit=new String[]{"Cairo","Alexandria","Giza"};
    String [] lebCit =new String[]{"Beirut ","Sidon ", "Baalbek"};
    String [] suadCit =new String[]{"Abha","Dammam","Jeddah"};
    String [] syrCit =new String[]{"Aleppo","Homs", "Daraa"};

    EditText agencyName;
    TextView agencyPhoneCode;
    EditText agencyEmail;
    Spinner countrySpinner;
    Spinner citySpinner;
    EditText agencyPassword;
    EditText agencyRePass;
    EditText agencyPhone;


    private  boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    private boolean isEmptyField(EditText editText) {
        if (editText.getText().toString().trim().length() > 0)
            return true;

        return false;
    }
    private static boolean isValidPassword(String s) {
        Pattern PASSWORD_PATTERN
                = Pattern.compile(
                "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!{}])(?=\\S+$).{8,15}$");

        return !TextUtils.isEmpty(s) && PASSWORD_PATTERN.matcher(s).matches();
    }
    private boolean isValidName(String s){
        Pattern NAME_PATTERN
                = Pattern.compile(
                "[a-zA-Z0-9]{3,20}");

        return !TextUtils.isEmpty(s) && NAME_PATTERN.matcher(s).matches();
    }
    private boolean isValidPhoneNum(String s){
        Pattern PHONE_PATTERN
                = Pattern.compile(
                "[0-9]{9}");

        return !TextUtils.isEmpty(s) && PHONE_PATTERN.matcher(s).matches();
    }
    private static final String ALGORITHM = "AES";
    private static final String KEY = "1Hbfh667adfDEJ78";

    public static String encryptPassword(String value) throws Exception
    {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte [] encryptedByteValue = cipher.doFinal(value.getBytes("utf-8"));
        String encryptedValue64 = Base64.encodeToString(encryptedByteValue, Base64.DEFAULT);
        return encryptedValue64;

    }
    private static Key generateKey() throws Exception
    {
        Key key = new SecretKeySpec(KEY.getBytes(),ALGORITHM);
        return key;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agency_sign_up);
        houseRentalsSPM = HouseRentalsSPM.getInstance(this);
        agencyPhoneCode =(TextView) findViewById(R.id.phoneCode);
        agencyName =(EditText)findViewById(R.id.first_name);
        agencyEmail =(EditText)findViewById(R.id.email);
        countrySpinner =(Spinner)findViewById(R.id.countrySp);
        citySpinner=(Spinner)findViewById(R.id.citySp);
        agencyPassword=(EditText)findViewById(R.id.password);
        agencyRePass =(EditText)findViewById(R.id.rePassword);
        agencyPhone =(EditText)findViewById(R.id.phone);

        ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, contries);
        spinnerArrayAdapter2.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        countrySpinner.setAdapter(spinnerArrayAdapter2);
        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Choose(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });
    }
    void Choose(int index){
        if(index==0){
            ArrayAdapter<String> spinnerArrayAdapter3 = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item, palCit);
            spinnerArrayAdapter3.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
            citySpinner.setAdapter(spinnerArrayAdapter3);
            agencyPhoneCode.setText("+970");

        }
        if(index==1){
            ArrayAdapter<String> spinnerArrayAdapter3 = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item, jorCit);
            spinnerArrayAdapter3.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
            citySpinner.setAdapter(spinnerArrayAdapter3);
            agencyPhoneCode.setText("+962");
        }
        if(index==2){
            ArrayAdapter<String> spinnerArrayAdapter3 = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item, eygCit);
            spinnerArrayAdapter3.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
            citySpinner.setAdapter(spinnerArrayAdapter3);
            agencyPhoneCode.setText("+20");
        }
        if(index==3){
            ArrayAdapter<String> spinnerArrayAdapter3 = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item, lebCit);
            spinnerArrayAdapter3.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
            citySpinner.setAdapter(spinnerArrayAdapter3);
            agencyPhoneCode.setText("+961");
        }
        if(index==4){
            ArrayAdapter<String> spinnerArrayAdapter3 = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item, suadCit);
            spinnerArrayAdapter3.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
            citySpinner.setAdapter(spinnerArrayAdapter3);
            agencyPhoneCode.setText("+966");
        }
        if(index==5){
            ArrayAdapter<String> spinnerArrayAdapter3 = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item, syrCit);
            spinnerArrayAdapter3.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
            citySpinner.setAdapter(spinnerArrayAdapter3);
            agencyPhoneCode.setText("+963");
        }
    }

    public void RegisterAgency(View view) throws Exception {
        if(!isEmptyField(agencyName)){
            agencyName.setError("Please fill name field");
            agencyName.setBackgroundColor(0xffff0000);
            return;
        }
        if(!isEmptyField(agencyEmail)){
            agencyEmail.setError("Please fill email field");
            agencyEmail.setBackgroundColor(0xffff0000);
            return;
        }
        if(!isEmptyField(agencyPassword)){
            agencyPassword.setError("Please fill password field");
            agencyPassword.setBackgroundColor(0xffff0000);
            return;
        }
        if(!isEmptyField(agencyRePass)){
            agencyRePass.setError("Please re-enter password");
            agencyRePass.setBackgroundColor(0xffff0000);
            return;
        }
        if(!isEmptyField(agencyPhone)){
            agencyPhone.setError("Please fill phone field");
            agencyPhone.setBackgroundColor(0xffff0000);
            return;
        }
        if(!isValidPassword(agencyPassword.getText().toString())){
            agencyPassword.setError("Password must contain:upper case, lower case, number, symbol");
            agencyPassword.setBackgroundColor(0xffff0000);

            return  ;
        }
        if(!isValidEmail(agencyEmail.getText().toString())) {
            agencyEmail.setError("InValid Email address");
            agencyEmail.setBackgroundColor(0xffff0000);

            return;
        }
        if(!agencyPassword.getText().toString().equals(agencyRePass.getText().toString())){
            agencyRePass.setError("Please check Password, Not matched");
            agencyRePass.setBackgroundColor(0xffff0000);

            return  ;
        }
        if(!isValidPhoneNum(agencyPhone.getText().toString()))
        {
            agencyPhone.setError("InValid phone number");
            agencyPhone.setBackgroundColor(0xffff0000);
            return;
        }
        if(!isValidName(agencyName.getText().toString()))
        {
            agencyName.setError("InValid name");
            agencyName.setBackgroundColor(0xffff0000);
            return;
        }

        AgencyClass newAgency =new AgencyClass();
        newAgency.setCity(citySpinner.getSelectedItem().toString());
        newAgency.setCountry(countrySpinner.getSelectedItem().toString());
        newAgency.setEmail(agencyEmail.getText().toString());
        String passwordEnc = encryptPassword(agencyPassword.getText().toString());
        newAgency.setPassword(passwordEnc);
        newAgency.setPhone(agencyPhone.getText().toString());
        newAgency.setName(agencyName.getText().toString());
        houseRentalDataBaseHelper myDataBase =new houseRentalDataBaseHelper(RentingAgencySignUp.this,"AGENCY",null,1);
        myDataBase.addAgency(newAgency);
        UserClass newUser = new UserClass();
        newUser.setEmail(agencyEmail.getText().toString());
        newUser.setPassword(agencyPassword.getText().toString());
        newUser.setAgencyFlag(true);
        UserClass.allUsers.add(newUser);
        houseRentalDataBaseHelper myDataBase2 =new houseRentalDataBaseHelper(RentingAgencySignUp.this,"USER",null,1);
        myDataBase2.addUser(newUser);

        houseRentalsSPM.writeString("email", agencyEmail.getText().toString());

        Intent nextIntent = new Intent(getBaseContext(), AgencyMainActivity.class);
        nextIntent.putExtra("t", newAgency);
        startActivity(nextIntent);
        finish();

    }
}

