package com.example.whatsappandroid.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsappandroid.R;
import com.example.whatsappandroid.listeners.OnItemClickListener;
import com.example.whatsappandroid.models.Contact;
import com.example.whatsappandroid.viewModels.UserViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Contact> contacts;
    private OnItemClickListener listener;
    private UserViewModel userViewModel;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView userName;
        private final TextView lastMsg;
        private final TextView time;

        public ViewHolder(View view) {
            super(view);

            imageView = view.findViewById(R.id.profile_image_contact);
            userName = view.findViewById(R.id.user_name_contact);
            lastMsg = view.findViewById(R.id.last_massage_contact);
            time = view.findViewById(R.id.time);
        }
    }

    public ContactListAdapter(Context context, OnItemClickListener listener) {
        this.inflater = LayoutInflater.from(context);
        this.listener = listener;
        this.userViewModel = new UserViewModel();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = this.inflater.inflate(R.layout.contact, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        if (this.contacts != null) {
            setPictureContact(viewHolder, position);
            viewHolder.userName.setText(contacts.get(position).getName());
            viewHolder.lastMsg.setText(contacts.get(position).getLast());
            if (contacts.get(position).getLastdate() == null) {
                viewHolder.time.setText("new");
            } else {

                // put only the hour and minute if it is from today or with the date else
                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String strDate = sdf.format(c.getTime());
                if (strDate.compareTo(contacts.get(position).getLastdate().substring(0, 10)) == 0) {
                    viewHolder.time.setText(contacts.get(position).getLastdate().substring(11, 16));
                } else {
                    viewHolder.time.setText(contacts.get(position).getLastdate().substring(0, 10) +
                            contacts.get(position).getLastdate().substring(11, 16));
                }
            }
            viewHolder.itemView.setOnClickListener(view -> {
                listener.onItemClick(contacts.get(position));
            });
        }
    }

    private void setPictureContact(ViewHolder viewHolder, final int position) {
        if (userViewModel.getUser(contacts.get(position).getId()) != null) {
            byte[] picture = userViewModel.getUser(contacts.get(position).getId()).getPicture();
            if (picture != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(picture, 0, picture.length);
                viewHolder.imageView.setImageBitmap(bitmap);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (this.contacts == null)
            return 0;
        return this.contacts.size();
    }

    public void setContactsList(List<Contact> newContacts) {
        this.contacts = newContacts;
        notifyDataSetChanged();
    }
}
