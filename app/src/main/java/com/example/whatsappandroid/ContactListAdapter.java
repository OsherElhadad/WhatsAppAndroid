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

import java.util.List;

public class ContactListAdapter extends ArrayAdapter<ContactWithMessages> {
    private LayoutInflater inflater;

    public ContactListAdapter(Context ctx, List<ContactWithMessages> contactArrayList) {
        super(ctx, R.layout.activity_contacts_list, contactArrayList);

        this.inflater = LayoutInflater.from(ctx);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ContactWithMessages c = getItem(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_contact, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.profile_image_contact);
        TextView userName = convertView.findViewById(R.id.user_name_contact);
        TextView lastMsg = convertView.findViewById(R.id.last_massage_contact);
        TextView time = convertView.findViewById(R.id.time);

        imageView.setImageResource(c.getPictureId());
        userName.setText(c.getName());
        lastMsg.setText(c.getLast());
        time.setText(c.getLastdate());

        return convertView;
    }
}
