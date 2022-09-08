package com.example.houserentals;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.List;

public class houseRentalDataBaseHelper extends SQLiteOpenHelper {
    public houseRentalDataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE HOUSE(Email VARCHAR(255),postalAddress VARCHAR(150) PRIMARY KEY UNIQUE,city VARCHAR(50),surfaceArea VARCHAR(10),constructionYear VARCHAR(4),numOfBedrooms VARCHAR(3),rentalPrice VARCHAR(9),status VARCHAR(15),photo VARCHAR(100),availabilityDate VARCHAR(20), propertyDescription VARCHAR(255), balcony VARCHAR(20), garden VARCHAR(20))");
        sqLiteDatabase.execSQL("CREATE TABLE RENTED(Email VARCHAR(255) PRIMARY KEY UNIQUE,First_name VARCHAR(20),Last_name VARCHAR(20), Name VARCHAR(20),postalAddress VARCHAR(150),rentingPeriod VARCHAR(10) )");
        sqLiteDatabase.execSQL("CREATE TABLE AGENCY(Email VARCHAR(255) PRIMARY KEY UNIQUE, Name VARCHAR(20),Password VARCHAR(15),Country VARCHAR(255),City VARCHAR(50),Phone_number CHAR(10))");
        sqLiteDatabase.execSQL("CREATE TABLE TENANT(Email VARCHAR(255) PRIMARY KEY UNIQUE, First_name VARCHAR(20),Last_name VARCHAR(20),Gender VARCHAR(20),Password VARCHAR(15) ,Gross_Monthly_Salary INTEGER,Occupation VARCHAR(255),Family_Size INTEGER ,Country VARCHAR(255),City VARCHAR(50),Phone_number CHAR(10),Nationality VARCHAR(50))");
        sqLiteDatabase.execSQL("CREATE TABLE USER(Email VARCHAR(255) PRIMARY KEY, Password VARCHAR(15))");
        sqLiteDatabase.execSQL("CREATE TABLE APPLY(Email VARCHAR(255) PRIMARY KEY UNIQUE, EmailAgency VARCHAR(255),postalAddress VARCHAR(150), rentingPeriod VARCHAR(10))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addTenant(TenantClass t) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Email", t.getEmail());
        contentValues.put("Password", t.getPassword());
        contentValues.put("First_name", t.getFirstname());
        contentValues.put("Last_name", t.getLastname());
        contentValues.put("Gender", t.getGender());
        contentValues.put("Nationality", t.getNationality());
        contentValues.put("Family_Size", t.getFamily_Size());
        contentValues.put("Gross_Monthly_Salary", t.getSalary());
        contentValues.put("City", t.getCity());
        contentValues.put("Phone_number", t.getPhone());
        sqLiteDatabase.insert("TENANT", null, contentValues);
    }

    public void addUser(UserClass u) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Email", u.getEmail());
        contentValues.put("Password", u.getPassword());
        sqLiteDatabase.insert("USER", null, contentValues);

    }

    public void insertHouse(HouseProperties h, String email) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Email", email);
        contentValues.put("postalAddress", h.getPostalAddress());
        contentValues.put("city", h.getCity());
        contentValues.put("surfaceArea", h.getSurfaceArea());
        contentValues.put("constructionYear", h.getConstructionYear());
        contentValues.put("numOfBedrooms", h.getNumOfBedrooms());
        contentValues.put("rentalPrice", h.getRentalPrice());
        contentValues.put("status", h.getStatus());
        contentValues.put("photo", h.getPhoto());
        contentValues.put("availabilityDate", h.getAvailabilityDate());
        contentValues.put("propertyDescription", h.getDescription());
        contentValues.put("balcony", h.getHasBalcony());
        contentValues.put("garden", h.getHasGarden());
        sqLiteDatabase.insert("HOUSE", null, contentValues);

    }

    public void addAgency(AgencyClass t) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Email", t.getEmail());
        contentValues.put("Password", t.getPassword());
        contentValues.put("Name", t.getName());
        contentValues.put("Country", t.getCountry());
        contentValues.put("City", t.getCity());
        contentValues.put("Phone_number", t.getPhone());
        sqLiteDatabase.insert("AGENCY", null, contentValues);
    }

    public int updateUser(UserClass user) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Email", user.getEmail());
        contentValues.put("Password", user.getPassword());
        return (sqLiteDatabase.update("USER", contentValues, "Email= '" + user.getEmail() + "'", null));
    }

    public boolean checkUser(String email, String password) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + "USER" + " where " + "Email=? ", new String[]{email});

        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                String data = cursor.getString(cursor.getColumnIndex("Password"));
                if (password.equals(data))
                    return true;
            }
        }
        return false;
    }

    public Cursor getFavByEmail(String email) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM HOUSE WHERE EMAIL = '" + email + "'", null);
        return cursor;
    }

    public Cursor getAllUsers() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM USER", null);
        return cursor;
    }

    public Cursor getHouseByEmail(String email) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM HOUSE WHERE EMAIL = '" + email + "'", null);
        return cursor;
    }

    public void deleteByPostal(String postalA) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        sqLiteDatabase.delete("HOUSE", "postalAddress=" + "'" + postalA + "'", null);
    }

    public void updateByPostal(HouseProperties h) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("postalAddress", h.getPostalAddress());
        contentValues.put("city", h.getCity());
        contentValues.put("surfaceArea", h.getSurfaceArea());
        contentValues.put("constructionYear", h.getConstructionYear());
        contentValues.put("numOfBedrooms", h.getNumOfBedrooms());
        contentValues.put("rentalPrice", h.getRentalPrice());
        contentValues.put("status", h.getStatus());
        contentValues.put("photo", h.getPhoto());
        contentValues.put("availabilityDate", h.getAvailabilityDate());
        contentValues.put("propertyDescription", h.getDescription());
        sqLiteDatabase.update("HOUSE", contentValues, "postalAddress = ?", new String[]{h.getPostalAddress()});
    }

    public void insertApplyRenting(String email, String address, String period, String ea) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Email", email);
        contentValues.put("postalAddress", address);
        contentValues.put("rentingPeriod", period);
        contentValues.put("EmailAgency", ea);
        sqLiteDatabase.insert("APPLY", null, contentValues);
    }

    public Cursor GetMailFromHouse(String postal) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM HOUSE WHERE postalAddress = '" + postal + "'", null);
        return cursor;

    }
    public Cursor getAgencyByEmail(String email) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM AGENCY WHERE Email = '" + email + "'", null);
        return cursor;
    }
    public Cursor getRentedFromTenant(String emailTn) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM RENTED WHERE Email = '" + emailTn + "'", null);
        return cursor;
    }
    public Cursor getTenantFromEmail(String email) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM TENANT WHERE Email = '" + email + "'", null);
        return cursor;
    }
    public String getAllRegisteredEmails() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String email;
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT Email FROM APPLY", null);
        if (cursor.moveToFirst()) {
            email = cursor.getString(cursor.getColumnIndex("Email"));
        } else {
            email = null;
        }
        return email;
    }

    public Cursor GetRentalByEmail(String email) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM APPLY WHERE EmailAgency = '" + email + "'", null);
        return cursor;
    }

    public void deleteByEmail(List<String> email) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        sqLiteDatabase.delete("APPLY", "Email=" + "'" + email + "'", null);
    }
    public Cursor getByPostal(String postal) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM HOUSE WHERE postalAddress = '" + postal + "'", null);
        return cursor;

    }

    public Cursor getRentedByAgencyMail(String emailAg) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM RENTED WHERE Email = '" + emailAg + "'", null);
        return cursor;
    }
    public void insertRented(String email, String address, String period, String ea) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Email", email);
        contentValues.put("postalAddress", address);
        contentValues.put("rentingPeriod", period);
        contentValues.put("Email", ea);
        sqLiteDatabase.insert("RENTED", null, contentValues);
    }
}
