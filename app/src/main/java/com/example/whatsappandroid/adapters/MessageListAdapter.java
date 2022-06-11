package com.example.whatsappandroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsappandroid.R;
import com.example.whatsappandroid.listeners.OnItemClickListener;
import com.example.whatsappandroid.models.ContactWithMessages;
import com.example.whatsappandroid.models.Message;

import java.util.List;

public class MessageListAdapter {

//    private LayoutInflater inflater;
//    private List<Message> messages;
//
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        private final ImageView imageView;
//        private final TextView content;
//        private final TextView time;
//
//        public ViewHolder(View view) {
//            super(view);
//
//            imageView = view.findViewById(R.id.profile_image_contact);
//            content = view.findViewById(R.id.user_name_contact);
//            time = view.findViewById(R.id.time);
//        }
//    }
//
//    public ContactListAdapter(Context context, OnItemClickListener listener) {
//        this.inflater = LayoutInflater.from(context);
//    }
//
//    @Override
//    public ContactListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
//        View view = this.inflater.inflate(R.layout.contact, viewGroup, false);
//        return new ContactListAdapter.ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(ContactListAdapter.ViewHolder viewHolder, final int position) {
//        if (this.contacts != null) {
//            viewHolder.imageView.setImageResource(contacts.get(position).contact.getPictureId());
//            viewHolder.userName.setText(contacts.get(position).contact.getName());
//            viewHolder.lastMsg.setText(contacts.get(position).contact.getLast());
//            viewHolder.time.setText(contacts.get(position).contact.getLastdate());
//        }
//    }
//
//    // Return the size of your dataset (invoked by the layout manager)
//    @Override
//    public int getItemCount() {
//        if (this.contacts == null)
//            return 0;
//        return this.contacts.size();
//    }
//
//    public void setContactsList(List<ContactWithMessages> newContacts) {
//        this.contacts = newContacts;
//        notifyDataSetChanged();
//    }

}
