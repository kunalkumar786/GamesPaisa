package prasad.com.gamespaisa.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import prasad.com.gamespaisa.MainActivity;
import prasad.com.gamespaisa.R;
import prasad.com.gamespaisa.fragment.EventDetailFragment;
import prasad.com.gamespaisa.model.EventModel;
import prasad.com.gamespaisa.model.UpComingEventModel;
import prasad.com.gamespaisa.utility.Constants;

public class UpComingEventAdapter extends RecyclerView.Adapter<UpComingEventAdapter.MyViewHolder> {
    Context context;
    MainActivity mainActivity;
   List<UpComingEventModel>upComingEventData;


    public UpComingEventAdapter(ArrayList<UpComingEventModel> upcoming_event_data, Context context, MainActivity mainActivity) {

        this.context=context;
        this.mainActivity=mainActivity;
        upComingEventData=upcoming_event_data;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView thumbnail;
        ImageView overflow;
        TextView title;
        TextView date_time,tv_entery_fee_value,tv_win_prize_value,tv_win_extra_value,tv_description_value;
        LinearLayout ll_upcoming;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail=(ImageView)itemView.findViewById(R.id.thumbnail);
            overflow=(ImageView)itemView.findViewById(R.id.overflow);
            title=(TextView)itemView.findViewById(R.id.title);
            date_time =(TextView)itemView.findViewById(R.id.count);
            tv_entery_fee_value =(TextView)itemView.findViewById(R.id.tv_entery_fee_value);
            tv_win_prize_value =(TextView)itemView.findViewById(R.id.tv_win_prize_value);
            tv_win_extra_value =(TextView)itemView.findViewById(R.id.tv_win_extra_value);
            tv_description_value =(TextView)itemView.findViewById(R.id.tv_description_value);
            ll_upcoming=(LinearLayout)itemView.findViewById(R.id.ll_upcoming);
        }
    }


   /* public UpComingEventAdapter(List<EventModel> event_data, Context context, MainActivity mainActivity) {
        this.event_data = event_data;
        this.context=context;
        this.mainActivity=mainActivity;
    }*/


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.event_item, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
       /* upComingEventData=event_data.get(i).getUpComingEventData();
        PastEventData =event_data.get(i).getPastEventData();
        onGoingEventData = event_data.get(i).getOnGoingData();
        UpComingEventModel upComingEventModel=upComingEventData.get(i);
        Log.e("Upcomingdata_SizeAdapter","Upcomingdata_Size"+upComingEventData.size());
        PastEventModel pastEventData=PastEventData.get(i);
        OnGoingEventModel onGoingEventModel=onGoingEventData.get(i);*/

       //if(upComingEventData.get(i).getImage().contentEquals(Constants.BASE_IMAGE_URL)){
           Glide.with(context).load(upComingEventData.get(i).getImage()).into(myViewHolder.thumbnail);
       //}else{
        //   Glide.with(context).load(Constants.BASE_IMAGE_URL+upComingEventData.get(i).getImage()).into(myViewHolder.thumbnail);
      // }

        myViewHolder.title.setText(upComingEventData.get(i).getTitle());
        Log.e("Title",upComingEventData.get(i).getTitle());

            String day=upComingEventData.get(i).getDate();
         String sourceString = "<b>" + day.charAt(0)+day.charAt(1) + "</b> "+"/" +day.charAt(3)+day.charAt(4) +"/"+day.charAt(6)+day.charAt(7)+day.charAt(8)+day.charAt(9)+" "+"-"+" "+upComingEventData.get(i).getTime() ;
            myViewHolder.date_time.setText(Html.fromHtml(sourceString));
        String entry_fee=R.string.Rs_symbol+" "+upComingEventData.get(i).getPrice();
        String win_price=R.string.Rs_symbol+" "+upComingEventData.get(i).getWin_price();
        String extra=R.string.Rs_symbol+" "+upComingEventData.get(i).getExtra();
        myViewHolder.tv_entery_fee_value.setText("\u20B9"+" "+upComingEventData.get(i).getPrice());
        myViewHolder.tv_win_prize_value.setText("\u20B9"+" "+upComingEventData.get(i).getWin_price());
        myViewHolder.tv_win_extra_value.setText("\u20B9"+" "+upComingEventData.get(i).getExtra());
        myViewHolder.tv_description_value.setText(upComingEventData.get(i).getDescription());

        myViewHolder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventModel comingEventModel=new EventModel();
                comingEventModel.setId(upComingEventData.get(i).getId());
                comingEventModel.setAdded_date(upComingEventData.get(i).getAdded_date());
                comingEventModel.setTitle(upComingEventData.get(i).getTitle());
                comingEventModel.setDescription(upComingEventData.get(i).getDescription());
                comingEventModel.setPrice(upComingEventData.get(i).getPrice());
                comingEventModel.setImage(upComingEventData.get(i).getImage());
                comingEventModel.setAddress(upComingEventData.get(i).getAddress());
                comingEventModel.setExtra(upComingEventData.get(i).getExtra());
                comingEventModel.setDate(upComingEventData.get(i).getDate());
                comingEventModel.setTime(upComingEventData.get(i).getTime());
                comingEventModel.setTotal_ticket(upComingEventData.get(i).getTotal_ticket());
                comingEventModel.setIs_purchage_by_me(upComingEventData.get(i).getIs_purchage_by_me());
                comingEventModel.setAvailable_ticket(upComingEventData.get(i).getAvailable_ticket());
                comingEventModel.setWin_price(upComingEventData.get(i).getWin_price());
                EventDetailFragment eventDetailFragment=new EventDetailFragment();
                Bundle bundle=new Bundle();
                bundle.putParcelable("description",comingEventModel);
                eventDetailFragment.setArguments(bundle);
                mainActivity.hideViewPager();
                mainActivity.changeFragment(eventDetailFragment);
            }
        });



        myViewHolder.ll_upcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventModel comingEventModel=new EventModel();
                comingEventModel.setId(upComingEventData.get(i).getId());
                comingEventModel.setAdded_date(upComingEventData.get(i).getAdded_date());
                comingEventModel.setTitle(upComingEventData.get(i).getTitle());
                comingEventModel.setDescription(upComingEventData.get(i).getDescription());
                comingEventModel.setPrice(upComingEventData.get(i).getPrice());
                comingEventModel.setImage(upComingEventData.get(i).getImage());
                comingEventModel.setAddress(upComingEventData.get(i).getAddress());
                comingEventModel.setExtra(upComingEventData.get(i).getExtra());
                comingEventModel.setDate(upComingEventData.get(i).getDate());
                comingEventModel.setTime(upComingEventData.get(i).getTime());
                comingEventModel.setTotal_ticket(upComingEventData.get(i).getTotal_ticket());
                comingEventModel.setIs_purchage_by_me(upComingEventData.get(i).getIs_purchage_by_me());
                comingEventModel.setAvailable_ticket(upComingEventData.get(i).getAvailable_ticket());
                comingEventModel.setWin_price(upComingEventData.get(i).getWin_price());
                EventDetailFragment eventDetailFragment=new EventDetailFragment();
                Bundle bundle=new Bundle();
                bundle.putParcelable("description",comingEventModel);
                eventDetailFragment.setArguments(bundle);
                mainActivity.hideViewPager();
                mainActivity.changeFragment(eventDetailFragment);
            }
        });
        myViewHolder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(myViewHolder.overflow);
            }
        });

    }

    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(context, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play_next:
                    Toast.makeText(context, "Play next", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }


    @Override
    public int getItemCount() {
        return upComingEventData.size();
    }



}
