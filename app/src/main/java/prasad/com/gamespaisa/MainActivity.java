package prasad.com.gamespaisa;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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

import de.hdodenhof.circleimageview.CircleImageView;
import prasad.com.gamespaisa.adapter.NavigationMenuAdapter;
import prasad.com.gamespaisa.fragment.AboutUsFragment;
import prasad.com.gamespaisa.fragment.ContactUs;
import prasad.com.gamespaisa.fragment.EventDetailFragment;
import prasad.com.gamespaisa.fragment.NotificationFragment;
import prasad.com.gamespaisa.fragment.OnGoingFragment;
import prasad.com.gamespaisa.fragment.PastFragment;
import prasad.com.gamespaisa.fragment.ProfileFragment;
import prasad.com.gamespaisa.fragment.UpComingFragment;
import prasad.com.gamespaisa.fragment.WalletFragment;
import prasad.com.gamespaisa.interfac.Api_interface;
import prasad.com.gamespaisa.interfac.RefreshData;
import prasad.com.gamespaisa.material.LoginActivity;
import prasad.com.gamespaisa.model.EventModel;
import prasad.com.gamespaisa.model.OnGoingEventModel;
import prasad.com.gamespaisa.model.PastEventModel;
import prasad.com.gamespaisa.model.UpComingEventModel;
import prasad.com.gamespaisa.model.User;
import prasad.com.gamespaisa.utility.Session;
import prasad.com.gamespaisa.utility.UtilityMethods;
import prasad.com.gamespaisa.web_service.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recycler_view;
    TextView tv_name,tv_email;

    FrameLayout fl_container;

    ViewPager viewpager;

    TabLayout tabs;
    Fragment fragment;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    ArrayList<UpComingEventModel> upcoming_event_data;
    ArrayList<PastEventModel> pastEventData;
    ArrayList<OnGoingEventModel> onGoingEventData;
    ArrayList<EventModel> event_data;
    private boolean mToolBarNavigationListenerIsRegistered=false;
    Toolbar toolbar;
    public static final String NOTIFY_ACTIVITY_ACTION = "notify_activity";
    RefreshData refreshData;
    boolean refresh=false;
    CircleImageView iv_profile_image;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user=Session.getInstance(this).getLogin();
        fl_container=(FrameLayout)findViewById(R.id.fl_container);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        tabs = (TabLayout) findViewById(R.id.tabs);
        setSupportActionBar(toolbar);
        UtilityMethods.showProgressDialog(this);
        callHome_eventData();

        tabs.setupWithViewPager(viewpager);

        // setupViewPager(viewpager);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
       // drawer.addDrawerListener(toggle);
        drawer.setDrawerListener(toggle);
       // toggle.setDrawerSlideAnimationEnabled(true);
        //toggle.setDrawerIndicatorEnabled(true);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toggle.syncState();
        //enableViews(false);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        recycler_view = (RecyclerView) headerView.findViewById(R.id.recycler_view);
        tv_name = (TextView) headerView.findViewById(R.id.tv_username);
        tv_email = (TextView) headerView.findViewById(R.id.tv_email);
        iv_profile_image=(CircleImageView) headerView.findViewById(R.id.iv_profile_image);

      if(user.getProfile_pic()!=null) {
          try {
              Glide.with(this).load(R.drawable.logo).into(iv_profile_image);
          }catch(Exception e){
              e.printStackTrace();
              Glide.with(this).load(R.drawable.logo).into(iv_profile_image);
          }
          }else{
          Glide.with(this).load(R.drawable.logo).into(iv_profile_image);
      }
        user=Session.getInstance(this).getLogin();
        tv_name.setText(user.getUsername());
        tv_email.setText(user.getEmail());
        navigationView.setNavigationItemSelectedListener(this);
        ArrayList<String> menu_item = new ArrayList<>();
        menu_item.add("Home");
        menu_item.add("Profile");
        menu_item.add("Wallet");
        menu_item.add("Notifications");
        menu_item.add("Updates");
        menu_item.add("Contact Us");
        menu_item.add("Share App");
        menu_item.add("About Us");
        menu_item.add("Logout");
        NavigationMenuAdapter menuAdapter = new NavigationMenuAdapter(menu_item, MainActivity.this, MainActivity.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(menuAdapter);
        recycler_view.setFocusable(false);
        recycler_view.setNestedScrollingEnabled(false);
        recycler_view.smoothScrollToPosition(0);
        //startActivity(new Intent(this, RegisterActivity.class));
        //startActivity(new Intent(this, LoginActivity.class));
        //Intent i=new Intent(this,LoginActivity.class);
        //startActivity(i);
        UpComingFragment upComingFragment = new UpComingFragment();
        changeFragment(upComingFragment);
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                    @Override
                    public void onBackStackChanged() {
                        try {
                            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                            Fragment fr = getSupportFragmentManager().findFragmentById(R.id.fl_container);
                            final String fm_name = fr.getClass().getSimpleName();
                            Log.e("backstack: ", ": " + fm_name);

                            if (fm_name.contentEquals("ProfileFragment")) {
                                homeData();
                            }else if(fm_name.contentEquals("EventDetailFragment")){
                                homeData();
                            }else if(fm_name.contentEquals("AboutUsFragment")){
                                homeData();
                            }else if(fm_name.contentEquals("ContactUs")){
                                homeData();
                            }else if(fm_name.equals("NotificationFragment")){homeData();}
                            else if(fm_name.contentEquals("WalletFragment")){
                                homeData();
                            }else if(fm_name.equals("OnGoingFragment")){
                                homeData();
                            }else if(fm_name.equals("PastFragment")){
                                homeData();
                            }else if(fm_name.equals("Thanks_fragment")){
                                homeData();
                            }else if(fm_name.equals("ParticipantFragment")){
                                homeData();
                            }
                            else if (fm_name.contentEquals("My_order_fragment") || fm_name.contentEquals("Thanks_fragment")) {
                                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                                toggle.setDrawerIndicatorEnabled(false);
                                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                                toggle.syncState();

                                toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Fragment fm = new UpComingFragment();
                                        FragmentManager fragmentManager = getSupportFragmentManager();
                                        fragmentManager.beginTransaction().replace(R.id.contentPanel, fm).addToBackStack(null).commit();
                                    }
                                });
                            } else {

                                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                                toggle.setDrawerIndicatorEnabled(false);
                                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                                toggle.syncState();
                                toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        onBackPressed();
                                    }
                                });
                            }

                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                    }
                });





    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            /*if(fragment instanceof EventDetailFragment){
                changeFragment(new UpComingFragment());
            }*/
        } else {
            Fragment fr = getSupportFragmentManager().findFragmentById(R.id.fl_container);
            final String fm_name = fr.getClass().getSimpleName();
            if (fm_name.contentEquals("ProfileFragment")) {
                homeData();
            }else if(fm_name.contentEquals("EventDetailFragment")){
                homeData();
            }else if(fm_name.contentEquals("AboutUsFragment")){
                homeData();
            }else if(fm_name.contentEquals("ContactUs")){
                homeData();
            }else if(fm_name.equals("NotificationFragment")){homeData();}
            else if(fm_name.contentEquals("WalletFragment")){
                homeData();
            }else if(fm_name.equals("OnGoingFragment")){
                homeData();
            }else if(fm_name.equals("PastFragment")){
                homeData();
            }else if(fm_name.equals("Thanks_fragment")){
                homeData();
            }else if(fm_name.equals("ParticipantFragment")){
                homeData();
            }
            else if (fm_name.contentEquals("My_order_fragment") || fm_name.contentEquals("Thanks_fragment")) {
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                toggle.setDrawerIndicatorEnabled(false);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                toggle.syncState();

                toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Fragment fm = new UpComingFragment();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.contentPanel, fm).addToBackStack(null).commit();
                    }
                });
            } else {

                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                toggle.setDrawerIndicatorEnabled(false);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                toggle.syncState();
                toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                });
            }

            //super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        IntentFilter filter = new IntentFilter(NOTIFY_ACTIVITY_ACTION );
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fl_container);
        final String fm_name = fragment.getClass().getSimpleName();
        if(fm_name.contentEquals("EventDetailFragment")) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }if(fm_name.contentEquals("AddMoneyFragment")) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }else if(fm_name.contentEquals("ProfileFragment")){
            fragment.onActivityResult(requestCode, resultCode, data);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*public void doEffectA(View view){
        startActivity(new Intent(this, LoginActivity.class));
    }*/
    public void customNavigationSelection(String condition) {
        switch (condition) {
            case "Home":
                tabs.setVisibility(View.VISIBLE);
                viewpager.setVisibility(View.VISIBLE);
                fl_container.setVisibility(View.GONE);
                callHome_eventData();
                closeDrawer();
                break;
            case "Logout":
                Session.getInstance(this).setLogout();
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                finish();


            break;

            case "Profile":
                //Toast.makeText(this, condition + " is selected!", Toast.LENGTH_SHORT).show();
                tabs.setVisibility(View.GONE);
                viewpager.setVisibility(View.GONE);
                fl_container.setVisibility(View.VISIBLE);
                changeFragment(new ProfileFragment());
                closeDrawer();
                break;
            case "Wallet":
                tabs.setVisibility(View.GONE);
                viewpager.setVisibility(View.GONE);
                fl_container.setVisibility(View.VISIBLE);
                changeFragment(new WalletFragment());
                closeDrawer();
                break;
            case "Notifications":
                tabs.setVisibility(View.GONE);
                viewpager.setVisibility(View.GONE);
                fl_container.setVisibility(View.VISIBLE);
                changeFragment(new NotificationFragment());
                closeDrawer();
                break;
            case "Updates":

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=prasad.com.gamespaisa"));
                startActivity(intent);
                closeDrawer();
                break;
            case "Contact Us":
                tabs.setVisibility(View.GONE);
                viewpager.setVisibility(View.GONE);
                fl_container.setVisibility(View.VISIBLE);
                changeFragment(new ContactUs());
                closeDrawer();
                break;
            case "Share App":
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                    String shareMessage = "\nLet me recommend you this application\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch (Exception e) {
                    //e.toString();
                }
                closeDrawer();
                break;
            case "About Us":
                tabs.setVisibility(View.GONE);
                viewpager.setVisibility(View.GONE);
                fl_container.setVisibility(View.VISIBLE);
                changeFragment(new AboutUsFragment());
                //Toast.makeText(this, condition + " is selected!", Toast.LENGTH_SHORT).show();
                closeDrawer();
                break;


        }
    }

    public void closeDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    public void changeFragment(Fragment fragment) {
        // TODO Auto-generated method stub
        FragmentManager manager = getSupportFragmentManager();
        this.fragment = fragment;
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        transaction.replace(R.id.fl_container, fragment);
        transaction.addToBackStack(fragment.getClass().getName());
        //manager.popBackStack();
        transaction.commit();
        //finish();
    }

    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

  private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        UpComingFragment upComingFragment = new UpComingFragment();
        Bundle home_data = new Bundle();
        home_data.putParcelableArrayList("upcoming_list_data", this.upcoming_event_data);
        //Toast.makeText(MainActivity.this,"SetupPager",Toast.LENGTH_LONG).show();
        upComingFragment.setArguments(home_data);

        OnGoingFragment onGoingFragment = new OnGoingFragment();
        Bundle ongoing_data = new Bundle();
        ongoing_data.putParcelableArrayList("ongoing_data", onGoingEventData);
        onGoingFragment.setArguments(ongoing_data);

        PastFragment pastFragment = new PastFragment();
        Bundle past_event_data = new Bundle();
        past_event_data.putParcelableArrayList("past_event_data", pastEventData);
        pastFragment.setArguments(past_event_data);

        adapter.addFragment(upComingFragment, "UPCOMING");
        adapter.addFragment(onGoingFragment, "ONGOING");
        adapter.addFragment(pastFragment, "RESULT");
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public void callHome_eventData() {
        Api_interface apiService = ApiClient.getClient().create(Api_interface.class);
        Call<JsonElement> call = apiService.getEvent(user.getUserid());
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Log.e("ResponseData===", "" + new Gson().toJson(response.body()));
                try {
                    JSONObject jsonObject = new JSONObject("" + new Gson().toJson(response.body()));
                    JSONObject data = jsonObject.getJSONObject("data");
                    JSONArray ongoing = data.getJSONArray("ongoing");
                    JSONArray past_event = data.getJSONArray("past");
                    JSONArray upcoming = data.getJSONArray("upcoming");
                    try {
                        if (ongoing != null && ongoing.length() > 0) {
                            Gson gson = new Gson();
                            Type type = new TypeToken<List<OnGoingEventModel>>() {
                            }.getType();
                            try {
                                onGoingEventData=new ArrayList<>();
                                onGoingEventData = gson.fromJson(String.valueOf(ongoing), type);
                                Log.e("EventDataSize", "" + onGoingEventData.size());
                            } catch (JsonSyntaxException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (past_event != null && past_event.length() > 0) {
                            Gson gson = new Gson();
                            Type type = new TypeToken<List<PastEventModel>>() {
                            }.getType();
                            try {
                                pastEventData=new ArrayList<>();
                                pastEventData = gson.fromJson(String.valueOf(past_event), type);
                                Log.e("EventDataSize", "" + pastEventData.size());
                            } catch (JsonSyntaxException e) {
                                e.printStackTrace();
                            }
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (upcoming != null && upcoming.length() > 0) {
                            Gson gson = new Gson();
                            Type type = new TypeToken<List<UpComingEventModel>>() {
                            }.getType();
                            try {
                                upcoming_event_data=new ArrayList<>();
                                upcoming_event_data = gson.fromJson(String.valueOf(upcoming), type);
                                Log.e("UpComingEvent", "" + upcoming_event_data.size());
                            } catch (JsonSyntaxException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ArrayList<EventModel> event_data = new ArrayList<>();
                    EventModel eventModel = new EventModel();
                    eventModel.setOnGoingData(onGoingEventData);
                    eventModel.setPastEventData(pastEventData);
                    eventModel.setUpComingEventData(upcoming_event_data);
                    event_data.add(eventModel);
                    //  setEventList(event_data);
                    UtilityMethods.dismissProgressDialog();
                    setupViewPager(viewpager);
                    if(refresh) {
                        if (upcoming_event_data != null && upcoming_event_data.size() > 0) {
                            refreshData.RefreshData(upcoming_event_data);
                        }
                    refresh = false;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e("ResponseData", "" + t.getMessage());
            }
        });

    }

    public void hideViewPager() {
        tabs.setVisibility(View.GONE);
        viewpager.setVisibility(View.GONE);
        fl_container.setVisibility(View.VISIBLE);
    }

public void homeData(){
    drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    toggle.setDrawerIndicatorEnabled(false);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    toggle.syncState();
    toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            tabs.setVisibility(View.VISIBLE);
            viewpager.setVisibility(View.VISIBLE);
            fl_container.setVisibility(View.GONE);
            //callHome_eventData();
            setupViewPager(viewpager);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            // Remove back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            // Show hamburger
            toggle.setDrawerIndicatorEnabled(true);
            // Remove the/any drawer toggle listener
            toggle.setToolbarNavigationClickListener(null);
            mToolBarNavigationListenerIsRegistered = false;
        }
    });
}
    private BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String string = bundle.getString("addtional_param");
                int resultCode = bundle.getInt("addtional_param2");
                Log.e("ResultCode",""+resultCode);

                if (resultCode == 2) {
                    callHome_eventData();
                    tabs.setVisibility(View.VISIBLE);
                    viewpager.setVisibility(View.VISIBLE);
                    fl_container.setVisibility(View.GONE);
                    refresh=true;
                   // setupViewPager(viewpager);
                } else {

                }
            }
        }
    };

    public void setOnDataListener(RefreshData refreshData ){
        this.refreshData=refreshData;
    }


}