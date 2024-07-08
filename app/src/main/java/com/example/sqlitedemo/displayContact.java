package com.example.sqlitedemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class displayContact extends AppCompatActivity {

    private static final int EDIT_CONTACT_REQUEST = 1;
    TextView nameText, phoneText, emailText;
    Button button, buttoedit;
    int contactId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact);

        Intent intent = getIntent();
        contactId = intent.getIntExtra("contactId", 1);
        String name = intent.getStringExtra("Rname");
        String phone = intent.getStringExtra("Rphone");
        String email = intent.getStringExtra("Remail");

        nameText = findViewById(R.id.displayName);
        nameText.setText(name);
        phoneText = findViewById(R.id.displayPhone);
        phoneText.setText(phone);
        emailText = findViewById(R.id.displayEmail);
        emailText.setText(email);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(displayContact.this, MainActivity.class);
                startActivity(intent1);
                finish();

            }
        });
        buttoedit = findViewById(R.id.editBtn);
        buttoedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(displayContact.this, editContact.class);
                intent2.putExtra("contactId", contactId);

                intent2.putExtra("Rname", name);
                intent2.putExtra("Rphone", phone);
                intent2.putExtra("Remail", email);
                Log.d("dbavi", "Starting editContact activity with ID: " + contactId);
                startActivityForResult(intent2, EDIT_CONTACT_REQUEST);

            }
        });


    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_CONTACT_REQUEST && resultCode == RESULT_OK && data != null) {
            String updatedName = data.getStringExtra("Rname");
            String updatedPhone = data.getStringExtra("Rphone");
            String updatedEmail = data.getStringExtra("Remail");

            nameText.setText(updatedName);
            phoneText.setText(updatedPhone);
            emailText.setText(updatedEmail);
        }
    }

}


