package prasad.com.gamespaisa.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import prasad.com.gamespaisa.R;
import prasad.com.gamespaisa.model.ParticipantModel;

public class ParticipantAdapter extends RecyclerView.Adapter<ParticipantAdapter.MyHolder>{
    ArrayList<ParticipantModel> participant_data;
    Context context;

    public ParticipantAdapter(ArrayList<ParticipantModel> participant_data,Context context) {
        this.participant_data = participant_data;
        this.context=context;
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.participant_fragment, viewGroup, false);
        return new ParticipantAdapter.MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        Glide.with(context).load(participant_data.get(i).getImage_url()).into(myHolder.thumbnail);
        myHolder.title.setText(participant_data.get(i).getTitle());
        myHolder.tv_description_value.setText(participant_data.get(i).getDescription());
    }

    @Override
    public int getItemCount() {
        return participant_data.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        ImageView thumbnail;
        TextView title,tv_description_value;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail=(ImageView)itemView.findViewById(R.id.thumbnail);
            title=(TextView)itemView.findViewById(R.id.title);
            tv_description_value=(TextView)itemView.findViewById(R.id.tv_description_value);
        }

    }

}
