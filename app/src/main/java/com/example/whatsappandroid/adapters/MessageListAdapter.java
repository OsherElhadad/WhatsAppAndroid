package com.example.whatsappandroid.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsappandroid.R;
import com.example.whatsappandroid.models.Message;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Message> messages;
    private Bitmap bitmap;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView content;
        private final TextView time;
        private final CircleImageView circleImageView;

        public ViewHolder(View view) {
            super(view);

            content = view.findViewById(R.id.text_message);
            time = view.findViewById(R.id.time_message);
            circleImageView = view.findViewById(R.id.profile_image_received_message);
        }
    }

    public MessageListAdapter(Context context, Bitmap bitmap) {
        this.bitmap = bitmap;
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
            // add here the code
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        if (this.messages != null) {
            viewHolder.content.setText(messages.get(position).getContent());
            if (messages.get(position).getCreated() != null) {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String strDate = sdf.format(c.getTime());
                if (strDate == messages.get(position).getCreated().substring(11, 16)) {
                    viewHolder.time.setText(messages.get(position).getCreated().substring(11, 16));
                } else {
                    viewHolder.time.setText(messages.get(position).getCreated().substring(0, 16));
                }
            }
            if (getItemViewType(position) == 1 && bitmap != null) {
                viewHolder.circleImageView.setImageBitmap(bitmap);
            }
        }
    }

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
