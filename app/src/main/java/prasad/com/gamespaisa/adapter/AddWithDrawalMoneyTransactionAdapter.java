package prasad.com.gamespaisa.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import prasad.com.gamespaisa.R;
import prasad.com.gamespaisa.model.AddWithdrawalTransactionModel;

public class AddWithDrawalMoneyTransactionAdapter extends RecyclerView.Adapter<AddWithDrawalMoneyTransactionAdapter.MyHolder> {

    ArrayList<AddWithdrawalTransactionModel> add_transaction;
    Context context;

    public AddWithDrawalMoneyTransactionAdapter(Context context, ArrayList<AddWithdrawalTransactionModel> add_transaction) {
    this.context=context;
    this.add_transaction=add_transaction;

    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaction_history_item, parent, false);

        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        AddWithdrawalTransactionModel addWithdrawalTransactionModel=add_transaction.get(i);
        myHolder.tv_withdraw_wallet.setText(addWithdrawalTransactionModel.getDate());
        myHolder.tv_credit_value.setText(addWithdrawalTransactionModel.getAmount_value());
    }

    @Override
    public int getItemCount() {
        return add_transaction.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        TextView tv_withdraw_wallet,tv_credit_value;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tv_withdraw_wallet=(TextView)itemView.findViewById(R.id.tv_withdraw_wallet);
            tv_credit_value=(TextView)itemView.findViewById(R.id.tv_credit_value);


        }
    }

}
