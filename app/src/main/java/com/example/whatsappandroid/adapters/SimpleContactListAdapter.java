package com.example.whatsappandroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.whatsappandroid.models.ContactWithMessages;
import com.example.whatsappandroid.R;

import java.util.List;

public class SimpleContactListAdapter extends ArrayAdapter<ContactWithMessages> {
    private LayoutInflater inflater;

    public SimpleContactListAdapter(Context ctx, List<ContactWithMessages> contactArrayList) {
        super(ctx, R.layout.activity_contacts_list, contactArrayList);

        this.inflater = LayoutInflater.from(ctx);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ContactWithMessages c = getItem(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.contact, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.profile_image_contact);
        TextView userName = convertView.findViewById(R.id.user_name_contact);
        TextView lastMsg = convertView.findViewById(R.id.last_massage_contact);
        TextView time = convertView.findViewById(R.id.time);

        imageView.setImageResource(c.contact.getPictureId());
        userName.setText(c.contact.getName());
        lastMsg.setText(c.contact.getLast());
        time.setText(c.contact.getLastdate());

        return convertView;
    }
}
