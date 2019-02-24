package prasad.com.gamespaisa.fragment;

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

import java.util.ArrayList;
import java.util.List;

import prasad.com.gamespaisa.MainActivity;
import prasad.com.gamespaisa.R;

public class WalletFragment extends Fragment {
    ViewPager viewPager;
    TabLayout tabs_layout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wallet_fragment, container, false);
        ((MainActivity) getActivity()).setTitle("Wallet");
        viewPager = (ViewPager)view.findViewById(R.id.viewPager);
        tabs_layout =(TabLayout)view.findViewById(R.id.tabs_layout);
        tabs_layout.setTabTextColors(ColorStateList.valueOf(Color.parseColor("#ffffff")));
        tabs_layout.setupWithViewPager(viewPager);
        setupViewPager(viewPager);
        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        adapter.addFragment(new AddMoneyFragment(), "ADD MONEY");
        adapter.addFragment(new WithDrawFragment(), "WITHDRAW");
        adapter.addFragment(new TransactionFragment(), "TRANSACTION");
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



}
