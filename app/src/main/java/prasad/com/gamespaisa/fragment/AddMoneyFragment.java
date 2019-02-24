package prasad.com.gamespaisa.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.payumoney.core.PayUmoneyConfig;
import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;
import com.payumoney.sdkui.ui.utils.ResultModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import prasad.com.gamespaisa.R;
import prasad.com.gamespaisa.interfac.UpdateWalletBalance;
import prasad.com.gamespaisa.model.User;
import prasad.com.gamespaisa.payu_money.AppEnvironment;
import prasad.com.gamespaisa.payu_money.AppPreference;
import prasad.com.gamespaisa.utility.Session;

public class AddMoneyFragment extends Fragment implements View.OnClickListener {
    View view;
    private boolean isDisableExitConfirmation;
    //private PaymentParams mPaymentParams;
    private PayUmoneySdkInitializer.PaymentParam mPaymentParams_correct;
    private AppPreference mAppPreference;
    EditText et_amount_value;
    private String merchantKey, userCredentials;
    private Button bt_add_money;
    User user;
    UpdateWalletBalance updateWalletBalance;
    @Nullable
    @Override
   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.add_money_fragment,container,false);
        et_amount_value=(EditText)view.findViewById(R.id.et_amount_value);
        bt_add_money=(Button) view.findViewById(R.id.bt_add_money);
        user= Session.getInstance(getActivity()).getLogin();
        bt_add_money.setOnClickListener(AddMoneyFragment.this);
        mAppPreference = new AppPreference();

        return view;
    }

    private void launchPayUMoneyFlow() {
        PayUmoneyConfig payUmoneyConfig = PayUmoneyConfig.getInstance();

        //Use this to set your custom text on result screen button
        payUmoneyConfig.setDoneButtonText(((EditText) view.findViewById(R.id.et_amount_value)).getText().toString());

        //Use this to set your custom title for the activity
       // payUmoneyConfig.setPayUmoneyActivityTitle(((TextView)view.findViewById(R.id.tv_description)).getText().toString());

        payUmoneyConfig.disableExitConfirmation(isDisableExitConfirmation);

        PayUmoneySdkInitializer.PaymentParam.Builder builder = new PayUmoneySdkInitializer.PaymentParam.Builder();

        double amount = 0;
        try {
            amount = Double.parseDouble(et_amount_value.getText().toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        // mPaymentParams=new PaymentParams();
        String txnId = System.currentTimeMillis() + "";
        //String txnId = "TXNID720431525261327973";
        String phone = "7737607332"/*user.getMobile()*//*mobile_til.getEditText().getText().toString().trim()*/;
        String productName = "Credit Amount "/*+title.getText().toString()*//*mAppPreference.getProductInfo()*/;
        String firstName = "Add Credit";/*user.getUsername();*/
        String email = /*user.getEmail();*/"kunalkumar891@gmail.com";/*email_til.getEditText().getText().toString().trim();*/
        String udf1 = "";
        String udf2 = "";
        String udf3 = "";
        String udf4 = "";
        String udf5 = "";
        String udf6 = "";
        String udf7 = "";
        String udf8 = "";
        String udf9 = "";
        String udf10 = "";

        //AppEnvironment appEnvironment = ((BaseApplication)MainActivity.this.getApplication()).getAppEnvironment();
        AppEnvironment appEnvironment=AppEnvironment.SANDBOX;
        builder.setAmount(String.valueOf(amount))
                .setTxnId(txnId)
                .setPhone(phone)
                .setProductName(productName)
                .setFirstName(firstName)
                .setEmail(email)
                .setsUrl(appEnvironment.surl())
                .setfUrl(appEnvironment.furl())
                .setUdf1(udf1)
                .setUdf2(udf2)
                .setUdf3(udf3)
                .setUdf4(udf4)
                .setUdf5(udf5)
                .setUdf6(udf6)
                .setUdf7(udf7)
                .setUdf8(udf8)
                .setUdf9(udf9)
                .setUdf10(udf10)
                .setIsDebug(appEnvironment.debug())
                .setKey(appEnvironment.merchant_Key())
                .setMerchantId(appEnvironment.merchant_ID());



        /* mPaymentParams.setKey(*//*"gtKFFX"*//*appEnvironment.merchant_Key());
        mPaymentParams.setAmount(String.valueOf(amount));
        mPaymentParams.setProductInfo(productName);
        mPaymentParams.setFirstName(firstName);
        mPaymentParams.setEmail(email);
        mPaymentParams.setPhone(phone);
        mPaymentParams.setUserCredentials(appEnvironment.merchant_Key()+":"+email*//*+user.getEmail()*//*);
         *//*
         * Transaction Id should be kept unique for each transaction.
         * *//*
        mPaymentParams.setTxnId("" + System.currentTimeMillis());*/

        /**
         * Surl --> Success url is where the transaction response is posted by PayU on successful transaction
         * Furl --> Failre url is where the transaction response is posted by PayU on failed transaction
         */
        /* mPaymentParams.setSurl(appEnvironment.surl()*//*"https://payuresponse.firebaseapp.com/success"*//*);
        mPaymentParams.setFurl(appEnvironment.furl()*//*"https://payuresponse.firebaseapp.com/failure"*//*);
        mPaymentParams.setNotifyURL(mPaymentParams.getSurl());  //for lazy pay

        *//*
         * udf1 to udf5 are options params where you can pass additional information related to transaction.
         * If you don't want to use it, then send them as empty string like, udf1=""
         * *//*
        mPaymentParams.setUdf1("udf1");
        mPaymentParams.setUdf2("udf2");
        mPaymentParams.setUdf3("udf3");
        mPaymentParams.setUdf4("udf4");
        mPaymentParams.setUdf5("udf5");*/
        userCredentials = merchantKey + ":" + email;
        merchantKey=appEnvironment.merchant_Key();


        //payuHashes = new PayuHashes();
        //generateHashFromSDK(mPaymentParams,appEnvironment.salt());



        try {
            mPaymentParams_correct = builder.build();


            //  /*mPaymentParams=*/generateHashFromServer(mPaymentParams);
            mPaymentParams_correct= calculateServerSideHashAndInitiatePayment1(mPaymentParams_correct);
            // mPaymentParams_correct = calculateCorrectServerSideHashAndInitiatePayment1(mPaymentParams_correct);

            //Toast.makeText(getActivity(),"Merchant_Hash"+mPaymentParams.getHash(),Toast.LENGTH_LONG).show();
            if (AppPreference.selectedTheme != -1) {
                PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams_correct,getActivity(), AppPreference.selectedTheme,mAppPreference.isOverrideResultScreen());
            } else {
                //launchSdkUI(payuHashes);
                PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams_correct,getActivity(), R.style.AppTheme_default, mAppPreference.isOverrideResultScreen());


            }

        } catch (Exception e) {
            // some exception occurred
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            bt_add_money.setEnabled(true);
        }
    }

    private PayUmoneySdkInitializer.PaymentParam calculateServerSideHashAndInitiatePayment1(final PayUmoneySdkInitializer.PaymentParam paymentParam) {

        StringBuilder stringBuilder = new StringBuilder();
        HashMap<String, String> params = paymentParam.getParams();
        stringBuilder.append(params.get(PayUmoneyConstants.KEY) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.TXNID) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.AMOUNT) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.PRODUCT_INFO) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.FIRSTNAME) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.EMAIL) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF1) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF2) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF3) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF4) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF5) + "||||||");

        /*  AppEnvironment appEnvironment = ((BaseApplication) getActivity().getApplication()).getAppEnvironment();*/
        AppEnvironment appEnvironment=AppEnvironment.SANDBOX;
        stringBuilder.append(appEnvironment.salt());

        String hash = hashCal(stringBuilder.toString());
        paymentParam.setMerchantHash(hash);
        //  payuHashes = new PayuHashes();
        // payuHashes.setPaymentHash(hash);
        return paymentParam;
    }
    public static String hashCal(String str) {
        byte[] hashseq = str.getBytes();
        StringBuilder hexString = new StringBuilder();
        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-512");
            algorithm.reset();
            algorithm.update(hashseq);
            byte messageDigest[] = algorithm.digest();
            for (byte aMessageDigest : messageDigest) {
                String hex = Integer.toHexString(0xFF & aMessageDigest);
                if (hex.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(hex);
            }
        } catch (NoSuchAlgorithmException ignored) {
        }
        return hexString.toString();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bt_add_money:
            launchPayUMoneyFlow();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("OnActivityResult", "request code " + requestCode + " resultcode " + resultCode);
        if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == getActivity().RESULT_OK && data !=
                null) {
            TransactionResponse transactionResponse = data.getParcelableExtra(PayUmoneyFlowManager
                    .INTENT_EXTRA_TRANSACTION_RESPONSE);

            ResultModel resultModel = data.getParcelableExtra(PayUmoneyFlowManager.ARG_RESULT);

            // Check which object is non-null
            if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {
                if (transactionResponse.getTransactionStatus().equals(TransactionResponse.TransactionStatus.SUCCESSFUL)) {
                    //Success Transaction
                    /*Thanks_fragment thanks_fragment=  new Thanks_fragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("msg",transactionResponse.getTransactionDetails());
                    thanks_fragment.setArguments(bundle);
                    ((MainActivity)getActivity()).changeFragment(thanks_fragment);*/
                    String payuResponse = transactionResponse.getPayuResponse();
                    Log.e("payuResponse",payuResponse);
                    try {
                        JSONObject jsonObject=new JSONObject(payuResponse);
                        String txn_d=jsonObject.getString("txnid");
                        String status=jsonObject.getString("status");
                        if(status.equals("success")){
                            updateWalletMoney(et_amount_value.getText().toString());
                            updateWalletBalance.updateWalletBalance(et_amount_value.getText().toString());
                        }
                       // updateCart(user.getUserid(),eventModel.getId(),txn_d);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //

                } else {
                    //Failure Transaction
                }

                // Response from Payumoney
                String payuResponse = transactionResponse.getPayuResponse();

                // Response from SURl and FURL
                String merchantResponse = transactionResponse.getTransactionDetails();

              /*  new AlertDialog.Builder(getActivity())
                        .setCancelable(false)
                        .setMessage("Payu's Data : " + payuResponse + "\n\n\n Merchant's Data: " + merchantResponse)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }).show();*/
            } else if (resultModel != null && resultModel.getError() != null) {
                Log.d("Purcahse Error", "Error response : " + resultModel.getError().getTransactionResponse());
            } else {
                Log.d("Purcahse Error", "Both objects are null!");
            }
        }

    }

    private void updateWalletMoney(String wallet) {

        Session.getInstance(getActivity()).setWalletValue(wallet);

    }


   public void setWalletBalance(UpdateWalletBalance updateWalletBalance){
        this.updateWalletBalance=updateWalletBalance;
   }
}
