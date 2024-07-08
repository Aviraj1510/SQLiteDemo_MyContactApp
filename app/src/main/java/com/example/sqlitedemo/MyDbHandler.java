package com.example.sqlitedemo;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



import java.util.ArrayList;
import java.util.List;

public class MyDbHandler extends SQLiteOpenHelper {
    public MyDbHandler(Context context) {
        super(context, Params.DB_NAME,null, Params.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE " + Params.TABLE_NAME + "("
                + Params.KEY_ID + " INTEGER PRIMARY KEY," +
                Params.KEY_NAME + " TEXT, " +
                Params.KEY_PHONE + " TEXT" +
                Params.KEY_EMAIL + " TEXT" + ")";
        Log.d("dbavi", "Query being run is: "+create);
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion <= newVersion) {
            // Add the new column 'email' if the database version is less than 2

            String ALTER_TABLE = "ALTER TABLE " + Params.TABLE_NAME + " ADD COLUMN " + Params.KEY_EMAIL + " TEXT";
            db.execSQL(ALTER_TABLE);
        }
    }
    public void addContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues  values = new ContentValues();
        values.put(Params.KEY_NAME, contact.getName());
        values.put(Params.KEY_PHONE, contact.getPhoneNumber());
        values.put(Params.KEY_EMAIL, contact.getEmail());

        db.insert(Params.TABLE_NAME, null, values);
        Log.d("dbavi", "Successfully inserted");
        db.close();
    }

    public List<Contact> getAllContact() {
        List<Contact> contactList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        //Generate The Qyery to read fom the database

        String select = "SELECT * FROM " + Params.TABLE_NAME;
        Cursor cursor = db.rawQuery(select, null);

        //Loop through now

        if(cursor.moveToFirst()){
            do{
                Contact contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                contact.setEmail(cursor.getString(3));
                contactList.add(contact);
            }while (cursor.moveToNext());
        }
        return contactList;
    }

    public int updateContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values  = new ContentValues();
        values.put(Params.KEY_NAME, contact.getName());
        values.put(Params.KEY_PHONE, contact.getPhoneNumber());
        values.put(Params.KEY_EMAIL, contact.getEmail());

        int rowsAffected = db.update(Params.TABLE_NAME, values, Params.KEY_ID + "=?",
                new String[]{String.valueOf(contact.getId())});
        if (rowsAffected == 0) {
            Log.e("dbavi", "Failed to update contact");
        } else {
            Log.d("dbavi", "Contact updated successfully");
        }
        db.close();
        return rowsAffected;
    }

    public void deleteContactById(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Params.TABLE_NAME, Params.KEY_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }
    public void deleteContect(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Params.TABLE_NAME, Params.KEY_ID + "=?", new String[]{String.valueOf(contact.getId())});
        db.close();
    }
    public int getCount(){
        String query = "SELECT * FROM " + Params.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor  cursor = db.rawQuery(query, null);
        return cursor.getCount();
    }
}

