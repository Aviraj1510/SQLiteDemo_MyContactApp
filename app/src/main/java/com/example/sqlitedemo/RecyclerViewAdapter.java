package com.example.sqlitedemo;



import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<Contact> contactList;
    private OnEditClickListener onEditClickListener;
    public RecyclerViewAdapter(Context context, List<Contact> contactList, OnEditClickListener onEditClickListener){
        this.context = context;
        this.contactList = contactList;
        this.onEditClickListener = onEditClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Contact contact = contactList.get(position);
        holder.contactName.setText(contact.getName());
        holder.phoneNumber.setText(contact.getPhoneNumber());
        holder.contactEmail.setText(contact.getEmail());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onEditClickListener != null) {
                    onEditClickListener.onEditClick(contact);
                } else {
                    // Handle the case when onEditClickListener is null
                    Toast.makeText(context, "Edit click listener is not set!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView contactName;
        public TextView phoneNumber;
        public ImageView iconButton;
        public TextView contactEmail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            contactName = itemView.findViewById(R.id.name);
            phoneNumber = itemView.findViewById(R.id.phone_number);
            contactEmail = itemView.findViewById(R.id.email);
            iconButton = itemView.findViewById(R.id.iconButton);
            iconButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
//            Log.d("ClickFromHolder", "Clicked");
            int position = this.getAdapterPosition();
            Contact contact = contactList.get(position);
            String name = contact.getName();
            String phone = contact.getPhoneNumber();
            String Email = contact.getEmail();
            Intent intent  = new Intent(context, displayContact.class);
            intent.putExtra("Rname",  name);
            intent.putExtra("Rphone", phone);
            intent.putExtra("Remail", Email);
            context.startActivity(intent);
            Toast.makeText(context, "The  Position Is: " + String.valueOf(position) + " " + name + " " + phone , Toast.LENGTH_SHORT).show();
        }
    }
    public interface OnEditClickListener {
        void onEditClick(Contact contact);
    }
}

