package com.example.ipingpong.views.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ipingpong.R;
import com.example.ipingpong.shared.entities.Action;
import com.example.ipingpong.shared.entities.User;
import com.example.ipingpong.shared.entities.UserType;
import com.example.ipingpong.views.CoachProfile.CoachProfileFragment;
import com.example.ipingpong.views.PlayerProfile.PlayerProfileFragment;

import java.util.ArrayList;

public class Coach_ClubManagerListRecyclerViewAdapter extends RecyclerView.Adapter<Coach_ClubManagerListRecyclerViewAdapter.MyViewHolder> {

    private ArrayList<User> users;
    private Context context;

    public Coach_ClubManagerListRecyclerViewAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView clubManagerPictureImageView;
        TextView clubManagerNameTextView, clubNameTextView, clubRateTextView;
        Button profileBtn;


        public MyViewHolder(View itemView) {
            super(itemView);

            clubManagerPictureImageView = itemView.findViewById(R.id.clubManagerPictureImageView);
            clubManagerNameTextView = itemView.findViewById(R.id.clubManagerNameTextView);

            clubNameTextView = itemView.findViewById(R.id.clubNameTextView);
            clubRateTextView = itemView.findViewById(R.id.clubRateTextView);

            profileBtn = itemView.findViewById(R.id.profileBtn);

        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.club_managers_list_recyclerview_items, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.clubManagerNameTextView.setText(users.get(position).getFirstName() + " " + users.get(position).getLastName());

        if(users.get(position).getUserType() == UserType.COACH) { // list of coaches to club manager

            holder.clubRateTextView.setText(users.get(position).getGender());
            holder.clubNameTextView.setText("Phone: " + users.get(position).getPhoneNumber());

            holder.profileBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    Fragment fragment = new CoachProfileFragment(context, Action.ViewUpdate, users.get(position));
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                }
            });
        } else{ // list of club managers to admin

            holder.clubNameTextView.setText(users.get(position).getClub().getName());
            holder.clubRateTextView.setText("Club Rate: " + users.get(position).getClub().getRate());

            holder.profileBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    public void removeItem(int position) {
        users.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(User user, int position) {
        users.add(position, user);
        notifyItemInserted(position);
    }

    public ArrayList<User> getData() {
        return users;
    }

}
