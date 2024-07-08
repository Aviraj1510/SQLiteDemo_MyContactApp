package com.example.sqlitedemo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class editContact extends AppCompatActivity {

    private static final int EDIT_CONTACT_REQUEST = 1;
    private EditText editName, editPhone, editEmail;
    private ImageButton btnEdit;
    private MyDbHandler dbHandler;
    String name, phone, email;
    private int contactId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        editName = findViewById(R.id.editName);
        editPhone = findViewById(R.id.editPhone);
        editEmail = findViewById(R.id.editEmail);
        btnEdit = findViewById(R.id.editButton);

        dbHandler = new MyDbHandler(editContact.this);
        Intent intent = getIntent();
        contactId = intent.getIntExtra("contactId", 1);
        Log.d("dbavi", "Received contact ID: " + contactId);
        name = intent.getStringExtra("Rname");
        phone = intent.getStringExtra("Rphone");
        email = intent.getStringExtra("Remail");

        editName.setText(name);
        editPhone.setText(phone);
        editEmail.setText(email);


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(editContact.this, MainActivity.class);
                updateContact();
                startActivity(i);
            }
        });



    }
    private void updateContact(){

        String newName = editName.getText().toString();
        String newPhone = editPhone.getText().toString();
        String newEmail = editEmail.getText().toString();

        if (contactId == -1) {
            Log.e("dbavi", "Contact ID is invalid");
            Toast.makeText(editContact.this, "Contact ID is invalid", Toast.LENGTH_SHORT).show();
            return;
        }
        if (newName.isEmpty() || newPhone.isEmpty() || newEmail.isEmpty()) {
            Toast.makeText(editContact.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        Contact updatedContact = new Contact(contactId, newName, newPhone, newEmail);
        dbHandler.updateContact(updatedContact);

        Intent resultIntent = new Intent();
        resultIntent.putExtra("Rname", newName);
        resultIntent.putExtra("Rphone", newPhone);
        resultIntent.putExtra("Remail", newEmail);
        setResult(RESULT_OK, resultIntent);
        finish();


    }

}