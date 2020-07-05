package com.example.ipingpong.views.Adaptors;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ipingpong.R;
import com.example.ipingpong.shared.entities.DialogEntities;
import com.example.ipingpong.shared.entities.Notification;
import com.example.ipingpong.util.CustomDialog;

import java.util.ArrayList;

public class NotificationListRecyclerViewAdapter extends RecyclerView.Adapter<NotificationListRecyclerViewAdapter.MyViewHolder> {

    private ArrayList<Notification> notifications;
    private Context ctx;

    public NotificationListRecyclerViewAdapter(Context context, ArrayList<Notification> notifications) {
        this.ctx = context;
        this.notifications = notifications;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView messageNumberTextView, subjectTextView;
        private CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            messageNumberTextView = itemView.findViewById(R.id.messageNumberTextView);
            subjectTextView = itemView.findViewById(R.id.subjectTextView);
            cardView = itemView.findViewById(R.id.messageCardView);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notifications_list_recyclerview_items, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.messageNumberTextView.setText(String.valueOf(1 + position));
        holder.subjectTextView.setText(notifications.get(position).getSubject());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CustomDialog customDialog = new CustomDialog(DialogEntities.Notification);

                customDialog.setSubject(notifications.get(position).getSubject());
                customDialog.setBody(notifications.get(position).getMessage());

                customDialog.show(((AppCompatActivity) ctx).getSupportFragmentManager(), "Message");
            }
        });


    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

}
