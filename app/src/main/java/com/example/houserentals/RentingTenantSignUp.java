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

public class RentingTenantSignUp extends AppCompatActivity {
    HouseRentalsSPM houseRentalsSPM;



    String [] nationalities =new String[]{"Palestinian","Jordanian","Egyptians ","Lebanese","Saudis","Syrian"};
    String [] contries =new String[]{"Palestine","Jordan","Egypt ","Lebanon","Saudi","Syria"};
    String [] palCit =new String[]{"Rammallah","Jerusalem","Nablus", "Jenin"};
    String [] jorCit =new String[]{"Amman","irbid","Zarqa"};
    String [] eygCit=new String[]{"Cairo","Alexandria","Giza"};
    String [] lebCit =new String[]{"Beirut ","Sidon ", "Baalbek"};
    String [] suadCit =new String[]{"Abha","Dammam","Jeddah"};
    String [] syrCit =new String[]{"Aleppo","Homs", "Daraa"};
    String[] gender = new String[]{"Male","Female"};

    EditText tenantFirstName;
    TextView tenantPhoneCode;
    EditText tenantLastName;
    EditText tenantEmail;
    EditText tenantOccupation;
    EditText tenantFamilySize;
    EditText tenantGrossMonthlySalary;
    Spinner nationalitySp;
    Spinner countrySp;
    Spinner citySp;
    Spinner genderSp;
    EditText tenantPassword;
    EditText tenantRePass;
    EditText tenantPhone;


    private  boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    private boolean isEmptyField(EditText editText) {
        if (editText.getText().toString().trim().length() > 0)
            return true;

        return false;
    }
    private boolean isValidName(String s){
        Pattern NAME_PATTERN
                = Pattern.compile(
                "[a-zA-Z0-9]{3,20}");

        return !TextUtils.isEmpty(s) && NAME_PATTERN.matcher(s).matches();
    }
    private boolean isValidOccupation(String s){
        Pattern OCCUPATION_PATTERN
                = Pattern.compile(
                "[a-zA-Z]{1,20}");

        return !TextUtils.isEmpty(s) && OCCUPATION_PATTERN.matcher(s).matches();
    }
    private boolean isValidPhoneNum(String s){
        Pattern PHONE_PATTERN
                = Pattern.compile(
                "[0-9]{9}");

        return !TextUtils.isEmpty(s) && PHONE_PATTERN.matcher(s).matches();
    }
    private static boolean isValidPassword(String s) {
        Pattern PASSWORD_PATTERN
                = Pattern.compile(
                "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!{}])(?=\\S+$).{8,15}$");

        return !TextUtils.isEmpty(s) && PASSWORD_PATTERN.matcher(s).matches();
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
        setContentView(R.layout.activity_tenant_sign_up);
        houseRentalsSPM = HouseRentalsSPM.getInstance(this);
        tenantPhoneCode =(TextView) findViewById(R.id.phoneCode);
        tenantFirstName =(EditText)findViewById(R.id.first_name);
        tenantEmail =(EditText)findViewById(R.id.email);
        tenantLastName =(EditText)findViewById(R.id.last_name);
        tenantOccupation =(EditText)findViewById(R.id.Occupation);
        tenantFamilySize =(EditText)findViewById(R.id.Family_Size);
        tenantGrossMonthlySalary =(EditText)findViewById(R.id.Gross_Monthly_Salary);
        nationalitySp =(Spinner) findViewById(R.id.natSp);
        countrySp =(Spinner)findViewById(R.id.countrySp);
        citySp =(Spinner)findViewById(R.id.citySp);
        genderSp =(Spinner) findViewById(R.id.genderSp);
        tenantPassword =(EditText)findViewById(R.id.password);
        tenantRePass =(EditText)findViewById(R.id.rePassword);
        tenantPhone =(EditText)findViewById(R.id.phone);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, nationalities);
        spinnerArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        nationalitySp.setAdapter(spinnerArrayAdapter);

        ArrayAdapter<String> spinnerArrayAdapter3 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, gender);
        spinnerArrayAdapter3.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        genderSp.setAdapter(spinnerArrayAdapter3);

        ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, contries);
        spinnerArrayAdapter2.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        countrySp.setAdapter(spinnerArrayAdapter2);
        countrySp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
            citySp.setAdapter(spinnerArrayAdapter3);
            tenantPhoneCode.setText("+970");

        }
        if(index==1){
            ArrayAdapter<String> spinnerArrayAdapter3 = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item, jorCit);
            spinnerArrayAdapter3.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
            citySp.setAdapter(spinnerArrayAdapter3);
            tenantPhoneCode.setText("+962");
        }
        if(index==2){
            ArrayAdapter<String> spinnerArrayAdapter3 = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item, eygCit);
            spinnerArrayAdapter3.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
            citySp.setAdapter(spinnerArrayAdapter3);
            tenantPhoneCode.setText("+20");
        }
        if(index==3){
            ArrayAdapter<String> spinnerArrayAdapter3 = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item, lebCit);
            spinnerArrayAdapter3.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
            citySp.setAdapter(spinnerArrayAdapter3);
            tenantPhoneCode.setText("+961");
        }
        if(index==4){
            ArrayAdapter<String> spinnerArrayAdapter3 = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item, suadCit);
            spinnerArrayAdapter3.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
            citySp.setAdapter(spinnerArrayAdapter3);
            tenantPhoneCode.setText("+966");
        }
        if(index==5){
            ArrayAdapter<String> spinnerArrayAdapter3 = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item, syrCit);
            spinnerArrayAdapter3.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
            citySp.setAdapter(spinnerArrayAdapter3);
            tenantPhoneCode.setText("+963");
        }
    }

    public void RegisterTenant(View view) throws Exception {
        if(!isEmptyField(tenantPassword)){
            tenantPassword.setError("Please fill password field");
            tenantPassword.setBackgroundColor(0xffff0000);
            return;
        }
        if(!isEmptyField(tenantFirstName)){
            tenantFirstName.setError("Please fill first name field");
            tenantFirstName.setBackgroundColor(0xffff0000);
            return;
        }
        if(!isEmptyField(tenantLastName)){
            tenantLastName.setError("Please fill last name field");
            tenantLastName.setBackgroundColor(0xffff0000);
            return;
        }
        if(!isEmptyField(tenantEmail)){
            tenantEmail.setError("Please fill email field");
            tenantEmail.setBackgroundColor(0xffff0000);
            return;
        }
        if(!isEmptyField(tenantOccupation)){
            tenantOccupation.setError("Please fill occupation field");
            tenantOccupation.setBackgroundColor(0xffff0000);
            return;
        }
        if(!isEmptyField(tenantFamilySize)){
            tenantFamilySize.setError("Please fill family size field");
            tenantFamilySize.setBackgroundColor(0xffff0000);
            return;
        }
        if(!isEmptyField(tenantGrossMonthlySalary)){
            tenantGrossMonthlySalary.setError("Please fill salary field");
            tenantGrossMonthlySalary.setBackgroundColor(0xffff0000);
            return;
        }
        if(!isEmptyField(tenantRePass)){
            tenantRePass.setError("Please re-enter password");
            tenantRePass.setBackgroundColor(0xffff0000);
            return;
        }
        if(!isEmptyField(tenantPhone)){
            tenantPhone.setError("Please fill phone field");
            tenantPhone.setBackgroundColor(0xffff0000);
            return;
        }
        if(!isValidPassword(tenantPassword.getText().toString())){
            tenantPassword.setError("Password must contain:upper case, lower case, number, symbol");
            tenantPassword.setBackgroundColor(0xffff0000);

            return  ;
        }
        if(!tenantPassword.getText().toString().equals(tenantRePass.getText().toString())){
            tenantRePass.setError("Please check Password, Not matched");
            tenantRePass.setBackgroundColor(0xffff0000);

            return  ;
        }
        if(!isValidEmail(tenantEmail.getText().toString())) {
            tenantEmail.setError("InValid Email address");
            tenantEmail.setBackgroundColor(0xffff0000);
            return;
        }
        if(!isValidName(tenantFirstName.getText().toString()))
        {
            tenantFirstName.setError("InValid name");
            tenantFirstName.setBackgroundColor(0xffff0000);

            return;
        }
        if(!isValidName(tenantLastName.getText().toString()))
        {
            tenantLastName.setError("InValid name");
            tenantLastName.setBackgroundColor(0xffff0000);

            return;
        }
        if(!isValidOccupation(tenantOccupation.getText().toString()))
        {
            tenantOccupation.setError("Enter a String with less than 20 characters");
            tenantOccupation.setBackgroundColor(0xffff0000);

            return;
        }
        if(!isValidPhoneNum(tenantPhone.getText().toString()))
        {
            tenantPhone.setError("InValid phone number");
            tenantPhone.setBackgroundColor(0xffff0000);
            return;
        }

        TenantClass newTenant=new TenantClass();
        newTenant.setCity(citySp.getSelectedItem().toString());
        newTenant.setCountry(countrySp.getSelectedItem().toString());
        newTenant.setEmail(tenantEmail.getText().toString());
        String passwordEnc = encryptPassword(tenantPassword.getText().toString());
        newTenant.setPassword(passwordEnc);
        newTenant.setFamily_Size(Integer.parseInt(tenantFamilySize.getText().toString()));
        newTenant.setPhone(tenantPhone.getText().toString());
        newTenant.setSalary(Integer.parseInt(tenantGrossMonthlySalary.getText().toString()));
        newTenant.setGender(genderSp.getSelectedItem().toString());
        newTenant.setFirstname(tenantFirstName.getText().toString());
        newTenant.setLastname(tenantLastName.getText().toString());
        newTenant.setNationality(nationalitySp.getSelectedItem().toString());
        newTenant.setOccupation(tenantOccupation.getText().toString());
        houseRentalDataBaseHelper myDataBase =new houseRentalDataBaseHelper(RentingTenantSignUp.this,"TENANT",null,1);
        myDataBase.addTenant(newTenant);
        UserClass newUser = new UserClass();
        newUser.setEmail(tenantEmail.getText().toString());
        newUser.setPassword(tenantPassword.getText().toString());
        newUser.setAgencyFlag(false);
        UserClass.allUsers.add(newUser);
        houseRentalDataBaseHelper myDataBase2 =new houseRentalDataBaseHelper(RentingTenantSignUp.this,"USER",null,1);
        myDataBase2.addUser(newUser);
        houseRentalsSPM.writeString("email", tenantEmail.getText().toString());

        Intent nextIntent = new Intent(getBaseContext(), TenantMainActivity.class);
        nextIntent.putExtra("t", newTenant);
        startActivity(nextIntent);
        finish();

    }
}
