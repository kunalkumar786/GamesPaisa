package prasad.com.gamespaisa.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import prasad.com.gamespaisa.MainActivity;
import prasad.com.gamespaisa.R;
import prasad.com.gamespaisa.adapter.ParticipantAdapter;
import prasad.com.gamespaisa.interfac.Api_interface;
import prasad.com.gamespaisa.model.OnGoingEventModel;
import prasad.com.gamespaisa.model.ParticipantModel;
import prasad.com.gamespaisa.web_service.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParticipantFragment extends Fragment {
RecyclerView rv_participant_list;
Bundle bundle;
RelativeLayout rl_sorry_message;
ArrayList<ParticipantModel> participantModelArrayList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View view=inflater.inflate(R.layout.participant_fragment,container,false);
        rv_participant_list=(RecyclerView)view.findViewById(R.id.rv_participant_list);
        rl_sorry_message=(RelativeLayout)view.findViewById(R.id.rl_sorry_message);
        ((MainActivity) getActivity()).setTitle("Participant");
        if(bundle==null){
            bundle=this.getArguments();
            getParticipantDetails(bundle.getString("user_id"),bundle.getString("event_id"));
        }
        return view;
    }
    public void getParticipantDetails(String user_id,String event_id){
        Api_interface apiService = ApiClient.getClient().create(Api_interface.class);
        Call<JsonElement> call = apiService.getParticipantList(user_id,event_id);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                try{
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<OnGoingEventModel>>() {
                    }.getType();
                    try {
                        participantModelArrayList=new ArrayList<>();
                        participantModelArrayList = gson.fromJson(String.valueOf(participantModelArrayList), type);
                        Log.e("ParticipantDataSize", "" + participantModelArrayList.size());
                   if(participantModelArrayList!=null&&participantModelArrayList.size()>0){
                       LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                       rv_participant_list.setLayoutManager(layoutManager);
                       ParticipantAdapter participantAdapter=new ParticipantAdapter(participantModelArrayList,getActivity());
                       rv_participant_list.setAdapter(participantAdapter);
                       rl_sorry_message.setVisibility(View.GONE);
                   }else{
                       rl_sorry_message.setVisibility(View.VISIBLE);
                   }
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                    }

                }catch(Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });
    }



}
