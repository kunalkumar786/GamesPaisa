package prasad.com.gamespaisa.fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import prasad.com.gamespaisa.MainActivity;
import prasad.com.gamespaisa.R;

/**
 *
 */

public class Thanks_fragment extends Fragment implements View.OnClickListener {

    TextView tv_info;
    Button btn_home, btn_order;
    Typeface tf_popin_reg,tf_popin_semibold,tf_pxma_novabold,tf_pxma_novalight,tf_pxma_reg,tf_pxma_semibold,tf_roboto_medum,tf_roboto_reg;
    public Thanks_fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_thanks, container, false);
     /*   tf_popin_reg = Typeface.createFromAsset(getActivity().getAssets(),
                "font/Poppins-Regular.ttf");
        tf_popin_semibold = Typeface.createFromAsset(getActivity().getAssets(),
                "font/Poppins-SemiBold.ttf");
        tf_pxma_novabold = Typeface.createFromAsset(getActivity().getAssets(),
                "font/Proxima Nova Bold.ttf");
        tf_pxma_novalight = Typeface.createFromAsset(getActivity().getAssets(),
                "font/Proxima Nova Light.ttf");
        tf_pxma_reg = Typeface.createFromAsset(getActivity().getAssets(),
                "font/proxima-nova-regular.ttf");
        tf_pxma_semibold = Typeface.createFromAsset(getActivity().getAssets(),
                "font/Proxima-Nova-Semibold.ttf");
        tf_roboto_medum = Typeface.createFromAsset(getActivity().getAssets(),
                "font/Roboto-Medium.ttf");
        tf_roboto_reg = Typeface.createFromAsset(getActivity().getAssets(),
                "font/Roboto-Regular.ttf");*/
        ((MainActivity) getActivity()).setTitle("Congratulations");

        // handle the touch event if true
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // check user can press back button or not
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                    Fragment fm = new UpComingFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                            .addToBackStack(null).commit();
                    return true;
                }
                return false;
            }
        });

        //String data = getArguments().getString("msg");

        tv_info = (TextView) view.findViewById(R.id.tv_thank_info);
        btn_home = (Button) view.findViewById(R.id.btn_thank_home);
        btn_order = (Button) view.findViewById(R.id.btn_thank_order);
        btn_home.setTypeface(tf_pxma_semibold);
        btn_order.setTypeface(tf_pxma_semibold);
       // tv_info.setText(Html.fromHtml(data));

        btn_home.setOnClickListener(this);
        btn_order.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btn_thank_home) {
           // ((MainActivity)getActivity()).callHome_eventData();
           /* Fragment fm = new UpComingFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                    .addToBackStack(null).commit();*/
            Intent intent=new Intent(getActivity(),MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_thank_order) {

        /*    Fragment fm = new My_order_fragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                    .addToBackStack(null).commit();*/
        }

    }
}
