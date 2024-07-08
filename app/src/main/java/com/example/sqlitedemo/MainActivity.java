package com.example.sqlitedemo;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {

    private static final int EDIT_CONTACT_REQUEST = 1;
    private ArrayList<Contact> contactArrayList;
    private MyDbHandler db;
    private EditText editName,editNumber, editEmail;
    private ImageView profileImage;
    ImageButton buttonContacts, addContactButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        profileImage = findViewById(R.id.profileImg);
        editName = findViewById(R.id.editTextName);
        editNumber = findViewById(R.id.editTextNumber);
        editEmail = findViewById(R.id.editTextEmail);
        buttonContacts = findViewById(R.id.btnContact);

        buttonContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ContactList.class);
                startActivity(intent);
            }
        });


        db = new MyDbHandler(MainActivity.this);

        addContactButton = findViewById(R.id.buttonSave);
        addContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContact();
            }
        });

        loadContacts();
    }

    private void addContact() {

        Contact contact = new Contact();

        String Name = editName.getText().toString();
        String phoneNumber = editNumber.getText().toString();
        String Email = editEmail.getText().toString();

        if(Name.isEmpty() || phoneNumber.isEmpty() || Email.isEmpty()){
            Toast.makeText(this, "Please Enter Both Name And PhoneNumber", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!isStringOnlyAlphabet(editEmail.getText().toString())){
            Toast.makeText(this, "Invalid Email.", Toast.LENGTH_SHORT).show();
            return;
        }
        contact.setName(Name);
        contact.setPhoneNumber(phoneNumber);
        contact.setEmail(Email);
        db.addContact(contact);

        editName.setText("");
        editNumber.setText("");
        editEmail.setText("");
        loadContacts();
    }

    private boolean isStringOnlyAlphabet(String str1) {
        return str1.matches("[a-z0-9._-]+@[a-z]+\\.+[a-z]+");
    }
    private void loadContacts() {
        contactArrayList = new ArrayList<>();

        List<Contact> contactList = db.getAllContact();
        for (Contact contact : contactList) {
            Log.d("dbavi", "Id: " + contact.getId() + "\n" +
                    "Name: " + contact.getName() + "\n" +
                    "Phone Number: " + contact.getPhoneNumber() + "\n" +
                    "Email: " + contact.getEmail() + "\n");
            contactArrayList.add(contact);

        }

        Log.d("dbavi", "Bro You Have " + db.getCount() + " Contacts in your database");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == EDIT_CONTACT_REQUEST && resultCode == RESULT_OK){
            loadContacts();
        }
    }
}
