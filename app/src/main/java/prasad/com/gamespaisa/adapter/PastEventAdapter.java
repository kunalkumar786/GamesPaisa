package prasad.com.gamespaisa.adapter;

import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import prasad.com.gamespaisa.MainActivity;
import prasad.com.gamespaisa.R;
import prasad.com.gamespaisa.model.PastEventModel;
import prasad.com.gamespaisa.utility.Constants;

public class PastEventAdapter extends RecyclerView.Adapter<PastEventAdapter.MyViewHolder>{
    List<PastEventModel>past_data;

    public PastEventAdapter(List<PastEventModel> past_data, Context context, MainActivity mainActivity) {
        this.past_data = past_data;
        this.context = context;
        this.mainActivity = mainActivity;
    }

    Context context;
    MainActivity mainActivity;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.past_event_item, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        Glide.with(context).load(past_data.get(i).getImage()).into(myViewHolder.thumbnail);
        myViewHolder.title.setText(past_data.get(i).getTitle());
        Log.e("Title",past_data.get(i).getTitle());
        myViewHolder.count.setText(past_data.get(i).getDescription());
        myViewHolder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(myViewHolder.overflow);
            }
        });
        myViewHolder.tv_des.setText(past_data.get(i).getDescription());
        myViewHolder.tv_entery_fee_value.setText("\u20B9"+" "+past_data.get(i).getPrice());
        String day=past_data.get(i).getDate();
        String sourceString = "<b>" + day.charAt(0)+day.charAt(1) + "</b> "+"/" +day.charAt(3)+day.charAt(4) +"/"+day.charAt(6)+day.charAt(7)+day.charAt(8)+day.charAt(9)+" "+"-"+" "+past_data.get(i).getTime() ;
        myViewHolder.date_time.setText(Html.fromHtml(sourceString));

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
        return past_data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView thumbnail;
        ImageView overflow;
        TextView title,date_time,tv_des,tv_entery_fee_value;
        TextView count;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail=(ImageView)itemView.findViewById(R.id.thumbnail);
            overflow=(ImageView)itemView.findViewById(R.id.overflow);
            title=(TextView)itemView.findViewById(R.id.title);
            count=(TextView)itemView.findViewById(R.id.count);
           tv_des=(TextView)itemView.findViewById(R.id.tv_description_value);
            tv_entery_fee_value=(TextView)itemView.findViewById(R.id.tv_entery_fee_value);
            date_time=(TextView)itemView.findViewById(R.id.count);
        }
    }
}
