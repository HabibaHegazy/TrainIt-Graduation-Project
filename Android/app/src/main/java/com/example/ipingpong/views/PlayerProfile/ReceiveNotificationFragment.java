package com.example.ipingpong.views.PlayerProfile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ipingpong.R;
import com.example.ipingpong.controllers.CallBacks.ReceiveNotificationCallBack;
import com.example.ipingpong.controllers.NotificationController;
import com.example.ipingpong.shared.entities.Notification;
import com.example.ipingpong.shared.entities.User;
import com.example.ipingpong.views.Adaptors.NotificationListRecyclerViewAdapter;

import java.util.ArrayList;

public class ReceiveNotificationFragment extends Fragment {

    private RecyclerView recyclerView;
    private NotificationListRecyclerViewAdapter mAdapter;
    private LinearLayoutManager linearLayoutManager;
    private NotificationController notificationController;


    public ReceiveNotificationFragment(User user) {
        notificationController = new NotificationController(getActivity());
        populateRecyclerView(user);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_receive_notification, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);



        return view;
    }

    private void populateRecyclerView(User user){

        notificationController.receiveMessage(String.valueOf(user.getId()), new ReceiveNotificationCallBack() {
            @Override
            public void onSuccess(ArrayList<Notification> notifications) {

                System.out.println(notifications.size());

                mAdapter = new NotificationListRecyclerViewAdapter(getActivity(), notifications);
                recyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String reason) {
                Toast.makeText(getActivity(), reason, Toast.LENGTH_LONG).show();
            }
        });

    }
}
