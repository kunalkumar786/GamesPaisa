package prasad.com.gamespaisa.fragment;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;


import prasad.com.gamespaisa.MainActivity;
import prasad.com.gamespaisa.R;
import prasad.com.gamespaisa.adapter.PastEventAdapter;
import prasad.com.gamespaisa.adapter.UpComingEventAdapter;
import prasad.com.gamespaisa.model.OnGoingEventModel;
import prasad.com.gamespaisa.model.PastEventModel;
import prasad.com.gamespaisa.model.UpComingEventModel;
import prasad.com.gamespaisa.utility.UtilityMethods;

public class PastFragment extends Fragment {
    //@BindView(R.id.recycler_view_past_event)
    RecyclerView recycler_view_past_event;
    //@BindView(R.id.rl_no_data_msg)
    RelativeLayout rl_no_data_msg;
    ArrayList<PastEventModel> past_event_data;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pastevent_fragment, container, false);
        rl_no_data_msg=(RelativeLayout)view.findViewById(R.id.rl_no_data_msg);
        recycler_view_past_event=(RecyclerView)view.findViewById(R.id.recycler_view_past_event);

        Bundle bundle = getArguments();
        if(bundle!=null) {
            past_event_data = bundle.getParcelableArrayList("past_event_data");
            setEventList(past_event_data);
            rl_no_data_msg.setVisibility(View.GONE);

        }else{
            rl_no_data_msg.setVisibility(View.VISIBLE);
        }
        return view;
    }


    public void setEventList(ArrayList<PastEventModel> upcoming_event_data){
        PastEventAdapter menuAdapter=new PastEventAdapter(upcoming_event_data, getActivity(),(MainActivity)getActivity());
        /* RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2)*//*new LinearLayoutManager(getActivity()*//*;
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(menuAdapter);
        recycler_view.setFocusable(false);*/
        UtilityMethods.dismissProgressDialog();
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recycler_view_past_event.setLayoutManager(mLayoutManager);
        recycler_view_past_event.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recycler_view_past_event.setItemAnimator(new DefaultItemAnimator());
        menuAdapter.notifyDataSetChanged();
        recycler_view_past_event.setAdapter(menuAdapter);


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
