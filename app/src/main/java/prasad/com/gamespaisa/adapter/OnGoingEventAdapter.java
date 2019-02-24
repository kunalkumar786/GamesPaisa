package prasad.com.gamespaisa.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
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
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.marcinmoskala.videoplayview.VideoPlayView;

import java.util.List;

import prasad.com.gamespaisa.MainActivity;
import prasad.com.gamespaisa.R;
import prasad.com.gamespaisa.model.EventModel;
import prasad.com.gamespaisa.model.OnGoingEventModel;
import prasad.com.gamespaisa.model.PastEventModel;
import prasad.com.gamespaisa.model.UpComingEventModel;
import prasad.com.gamespaisa.utility.Constants;

public class OnGoingEventAdapter extends RecyclerView.Adapter<OnGoingEventAdapter.MyViewHolder>  {
    Context context;
    MainActivity mainActivity;
    List<OnGoingEventModel>onGoingEventData;
    public OnGoingEventAdapter(Context context, MainActivity mainActivity, List<OnGoingEventModel> onGoingEventData) {
        this.context = context;
        this.mainActivity = mainActivity;
        this.onGoingEventData = onGoingEventData;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{

        //VideoView thumbnail;
        ImageView overflow;
        TextView title;
        TextView count,tv_description_value;
        VideoPlayView thumbnail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail=(VideoPlayView)itemView.findViewById(R.id.thumbnail);
            overflow=(ImageView)itemView.findViewById(R.id.overflow);
            title=(TextView)itemView.findViewById(R.id.title);
            tv_description_value=(TextView)itemView.findViewById(R.id.tv_description_value);
            count=(TextView)itemView.findViewById(R.id.count);
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.on_going_item, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        //Glide.with(context).load(onGoingEventData.get(i).getImage()).into(myViewHolder.thumbnail);
        myViewHolder.title.setText(onGoingEventData.get(i).getTitle());
        Log.e("Title",onGoingEventData.get(i).getTitle());
        myViewHolder.tv_description_value.setText(onGoingEventData.get(i).getDescription());
        myViewHolder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(myViewHolder.overflow);
            }
        });
        String path="https://www.youtube.com/watch?v=-bF594j47ro";
        String day=onGoingEventData.get(i).getDate();
        String sourceString = "<b>" + day.charAt(0)+day.charAt(1) + "</b> "+"/" +day.charAt(3)+day.charAt(4) +"/"+day.charAt(6)+day.charAt(7)+day.charAt(8)+day.charAt(9)+" "+"-"+" "+onGoingEventData.get(i).getTime() ;
        myViewHolder.count.setText(Html.fromHtml(sourceString));

        String videoUrl = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";
        myViewHolder.thumbnail.setVideoUrl(videoUrl);
        Glide.with(context)
                .load(R.drawable.logo)
                .into(myViewHolder.thumbnail.getLoadingView());
        //  Uri uri=Uri.parse(path);
       // myViewHolder.thumbnail.setOnPreparedListener();
        myViewHolder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // Start the MediaController
                    //myViewHolder.thumbnail.
                    // Get the URL from String videoUrl


                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }


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
        return onGoingEventData.size();
    }
}
