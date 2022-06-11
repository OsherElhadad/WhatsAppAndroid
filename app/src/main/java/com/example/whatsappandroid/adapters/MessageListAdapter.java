package com.example.whatsappandroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.whatsappandroid.R;
import com.example.whatsappandroid.models.Message;
import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Message> messages;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView content;
        private final TextView time;

        public ViewHolder(View view) {
            super(view);

            content = view.findViewById(R.id.text_message);
            time = view.findViewById(R.id.time_message);
        }
    }

    public MessageListAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (messages != null) {
            if (messages.get(position).isSent()) {
                return 0;
            }
            return 1;
        }
        return -1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = null;
        if (viewType == 0) {
            view = this.inflater.inflate(R.layout.sent_message, viewGroup, false);
        } else {
            view = this.inflater.inflate(R.layout.received_message, viewGroup, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        if (this.messages != null) {
//            viewHolder.imageView.setImageResource(messages.get(position));
            viewHolder.content.setText(messages.get(position).getContent());
            viewHolder.time.setText(messages.get(position).getCreated());
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (this.messages == null)
            return 0;
        return this.messages.size();
    }

    public void setMessageList(List<Message> newMessages) {
        this.messages = newMessages;
        notifyDataSetChanged();
    }

}
