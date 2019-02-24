package prasad.com.gamespaisa.fragment;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


import prasad.com.gamespaisa.MainActivity;
import prasad.com.gamespaisa.R;
import prasad.com.gamespaisa.adapter.UpComingEventAdapter;
import prasad.com.gamespaisa.interfac.Api_interface;
import prasad.com.gamespaisa.interfac.RefreshData;
import prasad.com.gamespaisa.model.EventModel;
import prasad.com.gamespaisa.model.OnGoingEventModel;
import prasad.com.gamespaisa.model.PastEventModel;
import prasad.com.gamespaisa.model.UpComingEventModel;
import prasad.com.gamespaisa.utility.UtilityMethods;
import prasad.com.gamespaisa.web_service.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpComingFragment extends Fragment implements RefreshData {
/*@BindView(R.id.recycler_view)*/
RecyclerView recycler_view;
ArrayList<EventModel> event_data;
ArrayList<UpComingEventModel> upcoming_event_data,upcoming_data;
ArrayList<PastEventModel> pastEventData;
ArrayList<OnGoingEventModel> onGoingEventData;

RelativeLayout rl_no_data_msg;

@Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        recycler_view=(RecyclerView)view.findViewById(R.id.recycler_view);
    rl_no_data_msg=(RelativeLayout)view.findViewById(R.id.rl_no_data_msg);

         Bundle bundle = getArguments();
      if(bundle!=null) {
          upcoming_data=new ArrayList<>();
          upcoming_data = bundle.getParcelableArrayList("upcoming_list_data");

          Log.e("UpComingListSize==",""+upcoming_data.size());
          setEventList(upcoming_data);

          rl_no_data_msg.setVisibility(View.GONE);

      }else{
          rl_no_data_msg.setVisibility(View.VISIBLE);
      }
       // UtilityMethods.showProgressDialog(getActivity());

        ((MainActivity) getActivity()).setTitle(getResources().getString(R.string.app_name));
        //callHome_eventData();
        return view;
    }
   /* void callHome_eventData(){
        Api_interface apiService = ApiClient.getClient().create(Api_interface.class);
        Call<JsonElement> call=apiService.getEvent();
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Log.e("ResponseData===",""+new Gson().toJson(response.body()));
                try {
                    JSONObject jsonObject=new JSONObject(""+new Gson().toJson(response.body()));
                    JSONObject data=jsonObject.getJSONObject("data");
                    JSONArray ongoing=data.getJSONArray("ongoing");
                    JSONArray past_event=data.getJSONArray("past");
                    JSONArray upcoming=data.getJSONArray("upcoming");
                  try {
                      if (ongoing != null && ongoing.length() > 0) {
                          Gson gson = new Gson();
                          Type type = new TypeToken<List<OnGoingEventModel>>() {
                          }.getType();
                          try {
                              onGoingEventData = gson.fromJson(String.valueOf(ongoing), type);
                              Log.e("EventDataSize", "" + onGoingEventData.size());
                          } catch (JsonSyntaxException e) {
                              e.printStackTrace();
                          }
                      }
                  }catch(Exception e){
                      e.printStackTrace();
                  }
                try{
                    if (past_event != null && past_event.length() > 0) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<PastEventModel>>() { }.getType();
                        try {
                            pastEventData = gson.fromJson(String.valueOf(past_event), type);
                            Log.e("EventDataSize", "" + pastEventData.size());
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        }
                    }


                }catch(Exception e){
                    e.printStackTrace();
                }
                try{
                    if (upcoming != null && upcoming.length() > 0) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<UpComingEventModel>>() { }.getType();
                        try {
                            upcoming_event_data = gson.fromJson(String.valueOf(upcoming), type);
                            Log.e("UpComingEvent", "" + upcoming_event_data.size());
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
                    ArrayList<EventModel> event_data=new ArrayList<>();
                EventModel eventModel=new EventModel();
                eventModel.setOnGoingData(onGoingEventData);
                eventModel.setPastEventData(pastEventData);
                eventModel.setUpComingEventData(upcoming_event_data);
                event_data.add(eventModel);
                //setEventList(event_data);



                  } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e("ResponseData",""+t.getMessage());
            }
        });

    }*/

    public void setEventList(ArrayList<UpComingEventModel> upcoming_event_data){
    if(upcoming_event_data!=null&&upcoming_event_data.size()>0) {
        UpComingEventAdapter menuAdapter = new UpComingEventAdapter(upcoming_event_data, getActivity(), (MainActivity) getActivity());
        /* RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2)*//*new LinearLayoutManager(getActivity()*//*;
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(menuAdapter);
        recycler_view.setFocusable(false);*/
        UtilityMethods.dismissProgressDialog();
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(5), true));
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        menuAdapter.notifyDataSetChanged();
        recycler_view.setAdapter(menuAdapter);
        ((MainActivity) getActivity()).setOnDataListener(this);

    }
    }

    @Override
    public void RefreshData(ArrayList<UpComingEventModel> upcoming_data) {
    if(upcoming_data!=null) {
        UpComingEventAdapter menuAdapter = new UpComingEventAdapter(upcoming_data, getActivity(), (MainActivity) getActivity());
        /* RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2)*//*new LinearLayoutManager(getActivity()*//*;
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(menuAdapter);
        recycler_view.setFocusable(false);*/
        UtilityMethods.dismissProgressDialog();
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(5), true));
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        menuAdapter.notifyDataSetChanged();
        recycler_view.setAdapter(menuAdapter);
    }
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


}
