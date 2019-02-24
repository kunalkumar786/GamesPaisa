package prasad.com.gamespaisa.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;


import prasad.com.gamespaisa.R;
import prasad.com.gamespaisa.model.NotificationDataModel;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {
    List<NotificationDataModel> notification_data;
    Context context;
    public NotificationAdapter(List<NotificationDataModel> notification_data, Context context) {
        this.notification_data = notification_data;
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NotificationDataModel notificationDataModel=notification_data.get(position);
    holder.tv_date.setText(notificationDataModel.getNotification_date());
    holder.tv_title.setText(notificationDataModel.getNotification_title());
    holder.tv_description.setText(notificationDataModel.getNotification_body());
    if(notificationDataModel.getNotification_url()!=null&&!notificationDataModel.getNotification_url().equals("")){
        holder.thumbnail.setVisibility(View.VISIBLE);
        Glide.with(context).load(notificationDataModel.getNotification_url()).into(holder.thumbnail);
        holder.tv_date.setTextColor(Color.parseColor("#ffffff"));
    }else{
        holder.thumbnail.setVisibility(View.GONE);
    }


    }

    @Override
    public int getItemCount() {
        return notification_data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
    TextView tv_date,tv_title,tv_description;
    ImageView thumbnail;

        public MyViewHolder(@NonNull View itemView){
        super(itemView);

           tv_date=(TextView)itemView.findViewById(R.id.tv_date);
            tv_title=(TextView)itemView.findViewById(R.id.tv_title);
            tv_description=(TextView)itemView.findViewById(R.id.tv_description) ;
            thumbnail=(ImageView)itemView.findViewById(R.id.thumbnail);
    }
}


}
