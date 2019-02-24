package prasad.com.gamespaisa.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import prasad.com.gamespaisa.R;
import prasad.com.gamespaisa.adapter.AddWithDrawalMoneyTransactionAdapter;
import prasad.com.gamespaisa.model.AddWithdrawalTransactionModel;

public class TransactionFragment extends Fragment {
   RecyclerView rv_history;
   AddWithDrawalMoneyTransactionAdapter addWithDrawalMoneyTransactionAdapter;
   ArrayList<AddWithdrawalTransactionModel> add_transaction_data;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.transaction_fragment,container,false);
        rv_history=(RecyclerView)view.findViewById(R.id.rv_history);
        addWithDrawalMoneyTransactionAdapter=new AddWithDrawalMoneyTransactionAdapter(getActivity(),add_transaction_data);
        rv_history.setAdapter(addWithDrawalMoneyTransactionAdapter);

        return view;
    }
}
