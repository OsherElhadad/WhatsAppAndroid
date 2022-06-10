package com.example.whatsappandroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.whatsappandroid.ContactWithMessages;
import com.example.whatsappandroid.listeners.OnItemClickListener;
import com.example.whatsappandroid.R;
import java.util.List;


public class ContactListAdapter2 extends RecyclerView.Adapter<ContactListAdapter2.ViewHolder> {

    private LayoutInflater inflater;
    private List<ContactWithMessages> contacts;
    private OnItemClickListener listener;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
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

    public ContactListAdapter2(Context context, OnItemClickListener listener) {
        this.inflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = this.inflater.inflate(R.layout.activity_contact, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        if (this.contacts != null) {
            viewHolder.imageView.setImageResource(contacts.get(position).contact.getPictureId());
            viewHolder.userName.setText(contacts.get(position).contact.getName());
            viewHolder.lastMsg.setText(contacts.get(position).contact.getLast());
            viewHolder.time.setText(contacts.get(position).contact.getLastdate());
            viewHolder.itemView.setOnClickListener(view -> {
                listener.onItemClick(contacts.get(position));
            });
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return this.contacts.size();
    }

    public void setContactsList(List<ContactWithMessages> newContacts) {
        this.contacts = newContacts;
        notifyDataSetChanged();
    }
}
