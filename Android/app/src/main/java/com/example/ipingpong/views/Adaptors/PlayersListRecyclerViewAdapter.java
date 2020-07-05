package com.example.ipingpong.views.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ipingpong.shared.entities.Action;
import com.example.ipingpong.shared.entities.Player;
import com.example.ipingpong.R;
import com.example.ipingpong.views.CoachProfile.MonitorFragment;
import com.example.ipingpong.views.PlayerProfile.PlayerActivity;
import com.example.ipingpong.views.PlayerProfile.PlayerProfileFragment;
import com.example.ipingpong.views.Reports.MainReportFragment;

import java.util.ArrayList;

public class PlayersListRecyclerViewAdapter extends RecyclerView.Adapter<PlayersListRecyclerViewAdapter.MyViewHolder> {

    private ArrayList<Player> data;
    private Context context;

    public PlayersListRecyclerViewAdapter(Context context, ArrayList<Player> data) {
        this.context = context;
        this.data = data;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView PlayerPictureImageView;
        private TextView playerNameTextView, handUsedTextView, BestDayPerformanceTextView;
        private Button performanceBtn, profileBtn;
        private ConstraintLayout cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardViewPlayer);

            PlayerPictureImageView = itemView.findViewById(R.id.profile_img);

            playerNameTextView = itemView.findViewById(R.id.playerNameTextView);
            handUsedTextView = itemView.findViewById(R.id.handUsedTextView);
            BestDayPerformanceTextView = itemView.findViewById(R.id.BestDayPerformanceTextView);

            performanceBtn = itemView.findViewById(R.id.performanceBtn);
            profileBtn = itemView.findViewById(R.id.profileBtn);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.players_list_recyclerview_items, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.playerNameTextView.setText(data.get(position).getUser().getFirstName() + " " + data.get(position).getUser().getLastName());
        holder.handUsedTextView.setText(data.get(position).getHandUsed());

        holder.performanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment fragment = new MainReportFragment(data.get(position));
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });

        holder.profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment fragment = new PlayerProfileFragment(context, Action.ViewUpdate, data.get(position));
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment fragment = new MonitorFragment(data.get(position).getId());
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public void removeItem(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Player item, int position) {
        data.add(position, item);
        notifyItemInserted(position);
    }

    public ArrayList<Player> getData() {
        return data;
    }
}
