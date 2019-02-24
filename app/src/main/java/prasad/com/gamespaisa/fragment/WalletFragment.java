package prasad.com.gamespaisa.fragment;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import prasad.com.gamespaisa.MainActivity;
import prasad.com.gamespaisa.R;
import prasad.com.gamespaisa.interfac.UpdateWalletBalance;
import prasad.com.gamespaisa.utility.Session;

public class WalletFragment extends Fragment implements UpdateWalletBalance {
    ViewPager viewPager;
    TabLayout tabs_layout;
    TextView tv_total_available_balance_value;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wallet_fragment, container, false);
        ((MainActivity) getActivity()).setTitle("Wallet");
        tv_total_available_balance_value=(TextView)view.findViewById(R.id.tv_total_available_balance_value);
        viewPager = (ViewPager)view.findViewById(R.id.viewPager);
        tabs_layout =(TabLayout)view.findViewById(R.id.tabs_layout);
        tabs_layout.setTabTextColors(ColorStateList.valueOf(Color.parseColor("#ffffff")));
        tabs_layout.setupWithViewPager(viewPager);
        setupViewPager(viewPager);
        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        AddMoneyFragment addMoneyFragment=   new AddMoneyFragment();
        addMoneyFragment.setWalletBalance(WalletFragment.this);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager()/*getFragmentManager()*/);
        adapter.addFragment(addMoneyFragment, "ADD MONEY");
        adapter.addFragment(new WithDrawFragment(), "WITHDRAW");
        adapter.addFragment(new TransactionFragment(), "TRANSACTION");
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);

    }

    @Override
    public void updateWalletBalance(String amount) {
        int wallet_amount=0;
        int total_amount=0;
        if(Session.getInstance(getActivity()).getWallet()!=null){
             wallet_amount=Integer.parseInt(Session.getInstance(getActivity()).getWallet());
        }
       total_amount=wallet_amount+Integer.parseInt(amount);
        tv_total_available_balance_value.setText(total_amount);
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

        @Override
        public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            super.setPrimaryItem(container, position, object);
            if(position==0)
            {
                new AddMoneyFragment();
            }else if(position==1){
                new WithDrawFragment();
            }else if(position==2){
                new TransactionFragment();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getChildFragmentManager().findFragmentById(R.id.viewPager);
        final String fm_name = fragment.getClass().getSimpleName();
        if(fm_name.contentEquals("AddMoneyFragment")) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
