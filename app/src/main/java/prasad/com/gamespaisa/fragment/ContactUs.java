package prasad.com.gamespaisa.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import prasad.com.gamespaisa.MainActivity;
import prasad.com.gamespaisa.R;

public class ContactUs extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_us, container, false);
        ((MainActivity) getActivity()).setTitle("Contact Us");
        return view;
    }
}
