package prasad.com.gamespaisa.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import prasad.com.gamespaisa.MainActivity;
import prasad.com.gamespaisa.R;
import prasad.com.gamespaisa.adapter.NotificationAdapter;
import prasad.com.gamespaisa.database.DatabaseHandler;
import prasad.com.gamespaisa.model.NotificationDataModel;

public class NotificationFragment extends Fragment {
    DatabaseHandler dbHandler;
    RecyclerView recycler_view_notifiaction;
    NotificationAdapter notificationAdapter;
    ArrayList<NotificationDataModel> notification_data;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notification_fragment, container, false);
        ((MainActivity) getActivity()).setTitle("Notification");
        dbHandler=new DatabaseHandler(getActivity());
        notification_data=dbHandler.getAllNotification();
        //setHasOptionsMenu(true);
        recycler_view_notifiaction=(RecyclerView)view.findViewById(R.id.recycler_view_notifiaction);
        notificationAdapter=new NotificationAdapter(notification_data,getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recycler_view_notifiaction.setLayoutManager(mLayoutManager);
        recycler_view_notifiaction.setItemAnimator(new DefaultItemAnimator());
        recycler_view_notifiaction.setAdapter(notificationAdapter);

        notificationAdapter.notifyDataSetChanged();

        return view;
    }
}
