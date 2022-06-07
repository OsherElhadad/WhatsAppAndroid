package com.example.whatsappandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ContactListAdapter extends ArrayAdapter<Contact> {
    private LayoutInflater inflater;

    public ContactListAdapter(Context ctx, ArrayList<Contact> contactArrayList) {
        super(ctx, R.layout.activity_contacts_list, contactArrayList);

        this.inflater = LayoutInflater.from(ctx);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Contact c = getItem(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_contact, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.profile_image);
        TextView userName = convertView.findViewById(R.id.user_name);
        TextView lastMsg = convertView.findViewById(R.id.last_massage);
        TextView time = convertView.findViewById(R.id.time);

        imageView.setImageResource(c.getPictureId());
        userName.setText(c.getUserName());
        lastMsg.setText(c.getLastMassage());
        time.setText(c.getLastMassageSendingTime());

        return convertView;
    }
}
