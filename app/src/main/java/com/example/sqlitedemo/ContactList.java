package com.example.sqlitedemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class ContactList extends AppCompatActivity implements RecyclerViewAdapter.OnEditClickListener{
    private static final int EDIT_CONTACT_REQUEST = 1;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<Contact> contactArrayList;
    private MyDbHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        recyclerView = findViewById(R.id.contactList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = new MyDbHandler(ContactList.this);
        loadContacts();

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

        recyclerViewAdapter = new RecyclerViewAdapter(ContactList.this, contactArrayList, this);
        recyclerView.setAdapter(recyclerViewAdapter);
        Log.d("dbavi", "Bro You Have " + db.getCount() + " Contacts in your database");
    }


    @Override
    public void onEditClick(Contact contact) {
        Intent editIntent = new Intent(ContactList.this, editContact.class);
        editIntent.putExtra("id", contact.getId());
        editIntent.putExtra("Rname", contact.getName());
        editIntent.putExtra("Rphone", contact.getPhoneNumber());
        editIntent.putExtra("Remail", contact.getEmail());
        startActivityForResult(editIntent, EDIT_CONTACT_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_CONTACT_REQUEST && resultCode == RESULT_OK && data != null) {
            int contactId = data.getIntExtra("id", -1);
            String newName = data.getStringExtra("Rname");
            String newPhone = data.getStringExtra("Rphone");
            String newEmail = data.getStringExtra("Remail");

            if (contactId != -1) {
                // Find the contact in the list and update it
                for (Contact contact : contactArrayList) {
                    if (contact.getId() == contactId) {
                        contact.setName(newName);
                        contact.setPhoneNumber(newPhone);
                        contact.setEmail(newEmail);
                        break;
                    }
                }

                // Notify adapter about data set changed
                recyclerViewAdapter.notifyDataSetChanged();
            } else {
                Log.e("dbavi", "Contact ID is invalid");
            }
        } else {
            Log.e("dbavi", "Failed to get result from edit contact");
        }
    }
}