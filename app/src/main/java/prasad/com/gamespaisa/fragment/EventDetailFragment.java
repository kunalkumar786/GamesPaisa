package prasad.com.gamespaisa.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;






import com.payumoney.core.PayUmoneyConfig;
import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;
import com.payumoney.sdkui.ui.utils.ResultModel;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import prasad.com.gamespaisa.LandingActivity;
import prasad.com.gamespaisa.MainActivity;
import prasad.com.gamespaisa.R;
import prasad.com.gamespaisa.SplashActivity;
import prasad.com.gamespaisa.interfac.Api_interface;
import prasad.com.gamespaisa.model.EventModel;
import prasad.com.gamespaisa.model.User;
import prasad.com.gamespaisa.payu_money.AppEnvironment;
import prasad.com.gamespaisa.payu_money.AppPreference;
import prasad.com.gamespaisa.payu_money.BaseApplication;
import prasad.com.gamespaisa.utility.Constants;
import prasad.com.gamespaisa.utility.Session;
import prasad.com.gamespaisa.web_service.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDetailFragment extends Fragment implements View.OnClickListener  {
ImageView thumbnail;
TextView title,tv_description,tv_address,tv_time,tv_price,tv_header_title,tv_extra,tv_win_prize,tv_total,tv_total_available;
Bundle bundle;
Button tv_update,tv_view_participant;
EventModel eventModel;
ImageView iv_back_button;
    private String merchantKey, userCredentials;
   // private PayUChecksum checksum;
    //PayuHashes payuHashes;
   // private PayuConfig payuConfig;
    View view;
    private boolean isDisableExitConfirmation;
    //private PaymentParams mPaymentParams;
    private PayUmoneySdkInitializer.PaymentParam mPaymentParams_correct;
    private AppPreference mAppPreference;

    User user;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.event_detail, container, false);
        mAppPreference = new AppPreference();
       // Log.e("Mobile_number==>",Session.getInstance(getActivity()).getConta());
       user=Session.getInstance(getActivity()).getLogin();
      //  OnetapCallback.setOneTapCallback(EventDetailFragment.this);

     //   Payu.setInstance(getActivity());
        Log.e("EMAIL_ID==>",user.getEmail());
        Log.e("CONTACT==>",user.getMobile());
        thumbnail=(ImageView)view.findViewById(R.id.thumbnail);
        tv_view_participant=(Button)view.findViewById(R.id.tv_view_participant);
        iv_back_button=(ImageView)view.findViewById(R.id.iv_back_button);
        title=(TextView)view.findViewById(R.id.title);
        tv_description=(TextView)view.findViewById(R.id.tv_description);
        tv_address=(TextView)view.findViewById(R.id.tv_address);
        tv_time=(TextView)view.findViewById(R.id.tv_time);
        tv_total=(TextView)view.findViewById(R.id.tv_total);
        tv_total_available=(TextView)view.findViewById(R.id.tv_total_available);
        tv_price=(TextView)view.findViewById(R.id.tv_price);
        tv_extra=(TextView)view.findViewById(R.id.tv_extra);
        tv_win_prize=(TextView)view.findViewById(R.id.tv_win_prize);
        tv_header_title=(TextView)view.findViewById(R.id.tv_header_title);
        tv_update=(Button)view.findViewById(R.id.tv_update);
        tv_update.setOnClickListener(this);
        tv_view_participant.setOnClickListener(this);
        if(bundle==null){
            bundle=getArguments();
        eventModel=bundle.getParcelable("description");
        if(bundle!=null){
            ((MainActivity) getActivity()).setTitle(eventModel.getTitle());
          //  tv_header_title.setText(eventModel.getTitle());
            //((MainActivity) getActivity()).getSupportActionBar().hide();
            title.setText(eventModel.getTitle());
            String des="<b>"+"Description : "+"</b>"+eventModel.getDescription();
            String address="<b>"+"Details : "+"</b>"+eventModel.getAddress();
            try {
                String day = eventModel.getDate();

                String sourceString = "<b>" + "Schedule : " + "" + day.charAt(0) + day.charAt(1) + "</b> " + "/" + day.charAt(3) + day.charAt(4) + "/" + day.charAt(6) + day.charAt(7) + day.charAt(8) + day.charAt(9) + " " + "-" + " " + eventModel.getTime();
                tv_time.setText(Html.fromHtml(sourceString));
            }catch(Exception e){
                e.printStackTrace();
            }
            String entery="<b>"+"Entery Fee \u20B9 "+"</b>"+eventModel.getPrice();
            String extra="<b>"+"Extra \u20B9 "+"</b>"+eventModel.getExtra();
            String win_prize="<b>"+"Win Prize  \u20B9 "+"</b>"+eventModel.getWin_price();
            String total_ticket="<b>"+"Total Ticket "+"</b>"+eventModel.getTotal_ticket();
            String total_available="<b>"+"Available Ticket "+"</b>"+eventModel.getAvailable_ticket();
            tv_total.setText(Html.fromHtml(total_ticket));
            tv_total_available.setText(Html.fromHtml(total_available));
            tv_description.setText(Html.fromHtml(des));
            tv_address.setText(Html.fromHtml(address));


            tv_price.setText(Html.fromHtml(entery));
            tv_extra.setText(Html.fromHtml(extra));
            tv_win_prize.setText(Html.fromHtml(win_prize));
            if(eventModel.getImage()!=null&&!eventModel.getImage().equals("")){
                Glide.with(getActivity()).load(eventModel.getImage()).into(thumbnail);
            }else{
                Glide.with(getActivity()).load(R.drawable.logo).into(thumbnail);
            }
        }
            iv_back_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(getActivity(), LandingActivity.class);
                    startActivity(i);
                }
            });
        }
        return view;
    }

    private void launchPayUMoneyFlow() {
        PayUmoneyConfig payUmoneyConfig = PayUmoneyConfig.getInstance();

        //Use this to set your custom text on result screen button
        payUmoneyConfig.setDoneButtonText(((TextView) view.findViewById(R.id.title)).getText().toString());

        //Use this to set your custom title for the activity
        payUmoneyConfig.setPayUmoneyActivityTitle(((TextView)view.findViewById(R.id.tv_description)).getText().toString());

        payUmoneyConfig.disableExitConfirmation(isDisableExitConfirmation);

        PayUmoneySdkInitializer.PaymentParam.Builder builder = new PayUmoneySdkInitializer.PaymentParam.Builder();

        double amount = 0;
        try {
            amount = Double.parseDouble(eventModel.getPrice());

        } catch (Exception e) {
            e.printStackTrace();
        }
       // mPaymentParams=new PaymentParams();
        String txnId = System.currentTimeMillis() + "";
        //String txnId = "TXNID720431525261327973";
        String phone = "7737607332"/*user.getMobile()*//*mobile_til.getEditText().getText().toString().trim()*/;
        String productName = "Ticket "+title.getText().toString()/*mAppPreference.getProductInfo()*/;
        String firstName = user.getUsername();
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
            tv_update.setEnabled(true);
        }
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.tv_update:
                if(eventModel.getIs_purchage_by_me().equals("false")) {
                    launchPayUMoneyFlow();
                }else{
                    Toast.makeText(getActivity(),"Already Purchased",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.tv_view_participant:
                ParticipantFragment participantFragment=new ParticipantFragment();
                Bundle bundle=new Bundle();
                bundle.putString("user_id",user.getUserid());
                bundle.putString("event_id",eventModel.getId());
                participantFragment.setArguments(bundle);
                ((MainActivity)getActivity()).changeFragment(participantFragment);
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("MainActivity", "request code " + requestCode + " resultcode " + resultCode);
        if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == getActivity().RESULT_OK && data !=
                null) {
            TransactionResponse transactionResponse = data.getParcelableExtra(PayUmoneyFlowManager
                    .INTENT_EXTRA_TRANSACTION_RESPONSE);

            ResultModel resultModel = data.getParcelableExtra(PayUmoneyFlowManager.ARG_RESULT);

            // Check which object is non-null
            if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {
                if (transactionResponse.getTransactionStatus().equals(TransactionResponse.TransactionStatus.SUCCESSFUL)) {
                    //Success Transaction
                  Thanks_fragment thanks_fragment=  new Thanks_fragment();
                  Bundle bundle=new Bundle();
                  bundle.putString("msg",transactionResponse.getTransactionDetails());
                  thanks_fragment.setArguments(bundle);
                    ((MainActivity)getActivity()).changeFragment(thanks_fragment);
                    String payuResponse = transactionResponse.getPayuResponse();
                    Log.e("payuResponse",payuResponse);
                    try {
                        JSONObject jsonObject=new JSONObject(payuResponse);
                    String txn_d=jsonObject.getString("txnid");
                        updateCart(user.getUserid(),eventModel.getId(),txn_d);
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
   /* private PaymentParams calculateServerSideHashAndInitiatePayment1(final PaymentParams params) {

        StringBuilder stringBuilder = new StringBuilder();
        //HashMap<String, String> params = paymentParam.getParams();
        stringBuilder.append(params.getKey() + "|");
        stringBuilder.append(params.getTxnId() + "|");
        stringBuilder.append(params.getAmount() + "|");
        stringBuilder.append(params.getProductInfo() + "|");
        stringBuilder.append(params.getFirstName() + "|");
        stringBuilder.append(params.getEmail() + "|");
        stringBuilder.append(params.getUdf1() + "|");
        stringBuilder.append(params.getUdf2() + "|");
        stringBuilder.append(params.getUdf3() + "|");
        stringBuilder.append(params.getUdf4() + "|");
        stringBuilder.append(params.getUdf5() + "||||||");

      *//*  AppEnvironment appEnvironment = ((BaseApplication) getActivity().getApplication()).getAppEnvironment();*//*
        AppEnvironment appEnvironment=AppEnvironment.SANDBOX;
        stringBuilder.append(appEnvironment.salt());

        String hash = hashCal(stringBuilder.toString());
        //paymentParam.setMerchantHash(hash);
         payuHashes = new PayuHashes();

        payuHashes.setPaymentHash(hash);
        return mPaymentParams;
    }*/

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

   /* public void generateHashFromServer(PaymentParams params) {
        //nextButton.setEnabled(false); // lets not allow the user to click the button again and again.

        //HashMap<String, String> params = paymentParam.getParams();

        // lets create the post params
        StringBuffer postParamsBuffer = new StringBuffer();
        postParamsBuffer.append(concatParams(PayUmoneyConstants.KEY, params.getKey()));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.AMOUNT, params.getAmount()));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.TXNID, params.getTxnId()));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.EMAIL, params.getEmail()));
        postParamsBuffer.append(concatParams("productinfo", params.getProductInfo()));
        postParamsBuffer.append(concatParams("firstname", params.getFirstName()));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF1, params.getUdf1()));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF2, params.getUdf2()));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF3, params.getUdf3()));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF4, params.getUdf4()));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF5, params.getUdf5()));

        String postParams = postParamsBuffer.charAt(postParamsBuffer.length() - 1) == '&' ? postParamsBuffer.substring(0, postParamsBuffer.length() - 1).toString() : postParamsBuffer.toString();

        // lets make an api call
        GetHashesFromServerTask getHashesFromServerTask = new GetHashesFromServerTask();
        getHashesFromServerTask.execute(postParams);
    }*/
    protected String concatParams(String key, String value) {
        return key + "=" + value + "&";
    }



   /* private class GetHashesFromServerTask extends AsyncTask<String, String, PayuHashes> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
        }

        @Override
        protected PayuHashes doInBackground(String... postParams) {
            PayuHashes payuHashes = new PayuHashes();
            String merchantHash = "";
            try {
                //TODO Below url is just for testing purpose, merchant needs to replace this with their server side hash generation url
                URL url = new URL("https://payu.herokuapp.com/get_hash");

                String postParam = postParams[0];

                byte[] postParamsByte = postParam.getBytes("UTF-8");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty("Content-Length", String.valueOf(postParamsByte.length));
                conn.setDoOutput(true);
                conn.getOutputStream().write(postParamsByte);

                InputStream responseInputStream = conn.getInputStream();
                StringBuffer responseStringBuffer = new StringBuffer();
                byte[] byteContainer = new byte[1024];
                for (int i; (i = responseInputStream.read(byteContainer)) != -1; ) {
                    responseStringBuffer.append(new String(byteContainer, 0, i));
                }

                JSONObject response = new JSONObject(responseStringBuffer.toString());

                Iterator<String> payuHashIterator = response.keys();
                while (payuHashIterator.hasNext()) {
                    String key = payuHashIterator.next();
                    switch (key) {
                        *//**
                         * This hash is mandatory and needs to be generated from merchant's server side
                         *
                         *//*
                        *//*case "payment_hash":
                            merchantHash = response.getString(key);
                            break;
                       *//*
                        case "payment_hash":
                            payuHashes.setPaymentHash(response.getString(key));
                            break;
                        *//**
                         * vas_for_mobile_sdk_hash is one of the mandatory hashes that needs to be generated from merchant's server side
                         * Below is formula for generating vas_for_mobile_sdk_hash -
                         *
                         * sha512(key|command|var1|salt)
                         *
                         * here, var1 will be "default"
                         *
                         *//*
                        case "vas_for_mobile_sdk_hash":
                            payuHashes.setVasForMobileSdkHash(response.getString(key));
                            break;
                        *//**
                         * payment_related_details_for_mobile_sdk_hash is one of the mandatory hashes that needs to be generated from merchant's server side
                         * Below is formula for generating payment_related_details_for_mobile_sdk_hash -
                         *
                         * sha512(key|command|var1|salt)
                         *
                         * here, var1 will be user credentials. If you are not using user_credentials then use "default"
                         *
                         *//*
                        case "payment_related_details_for_mobile_sdk_hash":
                            payuHashes.setPaymentRelatedDetailsForMobileSdkHash(response.getString(key));
                            break;

                        //TODO Below hashes only needs to be generated if you are using Store card feature
                        *//**
                         * delete_user_card_hash is used while deleting a stored card.
                         * Below is formula for generating delete_user_card_hash -
                         *
                         * sha512(key|command|var1|salt)
                         *
                         * here, var1 will be user credentials. If you are not using user_credentials then use "default"
                         *
                         *//*
                        case "delete_user_card_hash":
                            payuHashes.setDeleteCardHash(response.getString(key));
                            break;
                        *//**
                         * get_user_cards_hash is used while fetching all the cards corresponding to a user.
                         * Below is formula for generating get_user_cards_hash -
                         *
                         * sha512(key|command|var1|salt)
                         *
                         * here, var1 will be user credentials. If you are not using user_credentials then use "default"
                         *
                         *//*
                        case "get_user_cards_hash":
                            payuHashes.setStoredCardsHash(response.getString(key));
                            break;
                        *//**
                         * edit_user_card_hash is used while editing details of existing stored card.
                         * Below is formula for generating edit_user_card_hash -
                         *
                         * sha512(key|command|var1|salt)
                         *
                         * here, var1 will be user credentials. If you are not using user_credentials then use "default"
                         *
                         *//*
                        case "edit_user_card_hash":
                            payuHashes.setEditCardHash(response.getString(key));
                            break;
                        *//**
                         * save_user_card_hash is used while saving card to the vault
                         * Below is formula for generating save_user_card_hash -
                         *
                         * sha512(key|command|var1|salt)
                         *
                         * here, var1 will be user credentials. If you are not using user_credentials then use "default"
                         *
                         *//*
                        case "save_user_card_hash":
                            payuHashes.setSaveCardHash(response.getString(key));
                            break;

                        //TODO This hash needs to be generated if you are using any offer key
                        *//**
                         * check_offer_status_hash is used while using check_offer_status api
                         * Below is formula for generating check_offer_status_hash -
                         *
                         * sha512(key|command|var1|salt)
                         *
                         * here, var1 will be Offer Key.
                         *
                         *//*
                        case "check_offer_status_hash":
                            payuHashes.setCheckOfferStatusHash(response.getString(key));
                            break;
                        default:
                            break;                    }
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return   payuHashes ;
        }

        @Override
        protected void onPostExecute(PayuHashes merchantHash) {
            super.onPostExecute(merchantHash);

            progressDialog.dismiss();
            tv_update.setEnabled(true);
           // launchSdkUI(merchantHash);
            *//*if (merchantHash.isEmpty() || merchantHash.equals("")) {
                Toast.makeText(getActivity(), "Could not generate hash", Toast.LENGTH_SHORT).show();
            } else {
                //mPaymentParams.setMerchantHash(merchantHash);
              //  launchSdkUI(PayuHashes payuHashes);
                if (AppPreference.selectedTheme != -1) {
                   // PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams, getActivity(), AppPreference.selectedTheme, mAppPreference.isOverrideResultScreen());
                } else {
                    //PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams, getActivity(), R.style.AppTheme_default, mAppPreference.isOverrideResultScreen());
                }
            }*//*
        }
    }*/
    public static class EditTextInputWatcher implements TextWatcher {

        private TextInputLayout textInputLayout;

        EditTextInputWatcher(TextInputLayout textInputLayout) {
            this.textInputLayout = textInputLayout;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString().length() > 0) {
                textInputLayout.setError(null);
                textInputLayout.setErrorEnabled(false);
            }
        }
    }

    public class DecimalDigitsInputFilter implements InputFilter {

        Pattern mPattern;

        public DecimalDigitsInputFilter(int digitsBeforeZero, int digitsAfterZero) {
            mPattern = Pattern.compile("[0-9]{0," + (digitsBeforeZero - 1) + "}+((\\.[0-9]{0," + (digitsAfterZero - 1) + "})?)||(\\.)?");
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            Matcher matcher = mPattern.matcher(dest);
            if (!matcher.matches())
                return "";
            return null;
        }

    }

    public boolean validateDetails(String email, String mobile) {
        email = email.trim();
        mobile = mobile.trim();

        if (TextUtils.isEmpty(mobile)) {
            //setErrorInputLayout(mobile_et, getString(R.string.err_phone_empty), mobile_til);
            return false;
        } else if (!isValidPhone(mobile)) {
            //setErrorInputLayout(mobile_et, getString(R.string.err_phone_not_valid), mobile_til);
            return false;
        } else if (TextUtils.isEmpty(email)) {
          //  setErrorInputLayout(email_et, getString(R.string.err_email_empty), email_til);
            return false;
        } else if (!isValidEmail(email)) {
          //  setErrorInputLayout(email_et, getString(R.string.email_not_valid), email_til);
            return false;
        } else {
            return true;
        }
    }
    public static void setErrorInputLayout(EditText editText, String msg, TextInputLayout textInputLayout) {
        textInputLayout.setError(msg);
        editText.requestFocus();
    }

    public static boolean isValidEmail(String strEmail) {
        return strEmail != null && android.util.Patterns.EMAIL_ADDRESS.matcher(strEmail).matches();
    }

    public static boolean isValidPhone(String phone) {
        Pattern pattern = Pattern.compile(AppPreference.PHONE_PATTERN);

        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }
    public void updateCart(String user_id,String event_id,String transaction_id){
        Api_interface apiService = ApiClient.getClient().create(Api_interface.class);
        Call<JsonElement> call = apiService.updatePurchase(user_id,event_id,transaction_id);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                try {
                    JSONObject jsonObject = new JSONObject("" + new Gson().toJson(response.body()));
                Log.e("JsonObjectData",""+jsonObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });
    }

   /* public void launchSdkUI(PayuHashes payuHashes) {

        Intent intent = new Intent(getActivity(), PayUBaseActivity.class);
        Bundle bundle=new Bundle();
        payuConfig = new PayuConfig();
        payuConfig.setEnvironment(PayuConstants.STAGING_ENV);
        bundle.putParcelable(PayuConstants.PAYU_CONFIG, payuConfig);
        bundle.putParcelable(PayuConstants.PAYMENT_PARAMS, mPaymentParams);
        bundle.putParcelable(PayuConstants.PAYU_HASHES, payuHashes);
       intent.putExtra("data",bundle);
        // intent.putExtra(PayuConstants.PAYU_CONFIG, PayuConstants.STAGING_ENV);
        //intent.putExtra(PayuConstants.PAYMENT_PARAMS, mPaymentParams);
        //intent.putExtra(PayuConstants.PAYU_HASHES, payuHashes);
        //getActivity().startActivityForResult(intent,PayuConstants.PAYU_REQUEST_CODE);
        //Lets fetch all the one click card tokens first
       fetchMerchantHashes(intent);

    }*/

   /* private void fetchMerchantHashes(final Intent intent) {
        AppEnvironment appEnvironment=AppEnvironment.SANDBOX;
        // now make the api call.
        StringBuilder stringBuilder = new StringBuilder();
        //HashMap<String, String> params = paymentParam.getParams();
        stringBuilder.append(appEnvironment.merchant_Key() + "|");
        stringBuilder.append("online" + "|");
        stringBuilder.append( appEnvironment.merchant_Key() + ":" + user.getEmail() + "|");
        stringBuilder.append( appEnvironment.salt() + "|");




        final String postParams *//*String.valueOf(stringBuilder)*//* = "merchant_key=" + appEnvironment.merchant_Key() + "&user_credentials=" + appEnvironment.merchant_Key() + ":" +"kunalkumar891@gmail.com";
        final Intent baseActivityIntent = intent;
        new AsyncTask<Void, Void, HashMap<String, String>>() {

            @Override
            protected HashMap<String, String> doInBackground(Void... params) {
                try {
                    //TODO Replace below url with your server side file url.
                    URL url = new URL("https://payu.herokuapp.com/get_merchant_hashes");

                    byte[] postParamsByte = postParams.getBytes("UTF-8");

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setRequestProperty("Content-Length", String.valueOf(postParamsByte.length));
                    conn.setDoOutput(true);
                    conn.getOutputStream().write(postParamsByte);

                    InputStream responseInputStream = conn.getInputStream();
                    StringBuffer responseStringBuffer = new StringBuffer();
                    byte[] byteContainer = new byte[1024];
                    for (int i; (i = responseInputStream.read(byteContainer)) != -1; ) {
                        responseStringBuffer.append(new String(byteContainer, 0, i));
                    }

                    JSONObject response = new JSONObject(responseStringBuffer.toString());

                    HashMap<String, String> cardTokens = new HashMap<String, String>();
                    JSONArray oneClickCardsArray = response.getJSONArray("data");
                    int arrayLength;
                    if ((arrayLength = oneClickCardsArray.length()) >= 1) {
                        for (int i = 0; i < arrayLength; i++) {
                            cardTokens.put(oneClickCardsArray.getJSONArray(i).getString(0), oneClickCardsArray.getJSONArray(i).getString(1));
                        }
                        return cardTokens;
                    }
                    // pass these to next activity

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(HashMap<String, String> oneClickTokens) {
                super.onPostExecute(oneClickTokens);

                baseActivityIntent.putExtra(PayuConstants.ONE_CLICK_CARD_TOKENS, oneClickTokens);
                getActivity().startActivityForResult(baseActivityIntent, PayuConstants.PAYU_REQUEST_CODE);
            }
        }.execute();
    }*/

  /*  private PayUmoneySdkInitializer.PaymentParam calculateCorrectServerSideHashAndInitiatePayment1(final PayUmoneySdkInitializer.PaymentParam paymentParam) {
       // PayuHashes payuHashes = new PayuHashes();
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

        AppEnvironment appEnvironment = AppEnvironment.SANDBOX;
        stringBuilder.append(appEnvironment.salt());

        String hash = hashCal(stringBuilder.toString());
        paymentParam.setMerchantHash(hash);
        payuHashes.setPaymentHash(params.get("hash"));
        //Toast.makeText(getActivity(),"Merchant_Hash++>>"+params.get("hash"),Toast.LENGTH_LONG).show();
        launchSdkUI(payuHashes);
        return paymentParam;
    }
*/
    /*public void generateHashFromSDK(PaymentParams mPaymentParams, String salt) {

        PostData postData = new PostData();

        // payment Hash;
        checksum = null;
        checksum = new PayUChecksum();
        checksum.setAmount(mPaymentParams.getAmount());
        checksum.setKey(mPaymentParams.getKey());
        checksum.setTxnid(mPaymentParams.getTxnId());
        checksum.setEmail(mPaymentParams.getEmail());
        checksum.setSalt(salt);
        checksum.setProductinfo(mPaymentParams.getProductInfo());
        checksum.setFirstname(mPaymentParams.getFirstName());
        checksum.setUdf1(mPaymentParams.getUdf1());
        checksum.setUdf2(mPaymentParams.getUdf2());
        checksum.setUdf3(mPaymentParams.getUdf3());
        checksum.setUdf4(mPaymentParams.getUdf4());
        checksum.setUdf5(mPaymentParams.getUdf5());

        postData = checksum.getHash();
        if (postData.getCode() == PayuErrors.NO_ERROR) {
            payuHashes.setPaymentHash(postData.getResult());
        }

        // checksum for payemnt related details
        // var1 should be either user credentials or default
        String var1 = mPaymentParams.getUserCredentials() == null ? PayuConstants.DEFAULT : mPaymentParams.getUserCredentials();
        String key = mPaymentParams.getKey();

        if ((postData = calculateHash(key, PayuConstants.PAYMENT_RELATED_DETAILS_FOR_MOBILE_SDK, var1, salt)) != null && postData.getCode() == PayuErrors.NO_ERROR) // Assign post data first then check for success
            payuHashes.setPaymentRelatedDetailsForMobileSdkHash(postData.getResult());
        //vas
        if ((postData = calculateHash(key, PayuConstants.VAS_FOR_MOBILE_SDK, PayuConstants.DEFAULT, salt)) != null && postData.getCode() == PayuErrors.NO_ERROR)
            payuHashes.setVasForMobileSdkHash(postData.getResult());

        // getIbibocodes
        if ((postData = calculateHash(key, PayuConstants.GET_MERCHANT_IBIBO_CODES, PayuConstants.DEFAULT, salt)) != null && postData.getCode() == PayuErrors.NO_ERROR)
            payuHashes.setMerchantIbiboCodesHash(postData.getResult());

        if (!var1.contentEquals(PayuConstants.DEFAULT)) {
            // get user card
            if ((postData = calculateHash(key, PayuConstants.GET_USER_CARDS, var1, salt)) != null && postData.getCode() == PayuErrors.NO_ERROR) // todo rename storedc ard
                payuHashes.setStoredCardsHash(postData.getResult());
            // save user card
            if ((postData = calculateHash(key, PayuConstants.SAVE_USER_CARD, var1, salt)) != null && postData.getCode() == PayuErrors.NO_ERROR)
                payuHashes.setSaveCardHash(postData.getResult());
            // delete user card
            if ((postData = calculateHash(key, PayuConstants.DELETE_USER_CARD, var1, salt)) != null && postData.getCode() == PayuErrors.NO_ERROR)
                payuHashes.setDeleteCardHash(postData.getResult());
            // edit user card
            if ((postData = calculateHash(key, PayuConstants.EDIT_USER_CARD, var1, salt)) != null && postData.getCode() == PayuErrors.NO_ERROR)
                payuHashes.setEditCardHash(postData.getResult());
        }

        if (mPaymentParams.getOfferKey() != null) {
            postData = calculateHash(key, PayuConstants.OFFER_KEY, mPaymentParams.getOfferKey(), salt);
            if (postData.getCode() == PayuErrors.NO_ERROR) {
                payuHashes.setCheckOfferStatusHash(postData.getResult());
            }
        }

        if (mPaymentParams.getOfferKey() != null && (postData = calculateHash(key, PayuConstants.CHECK_OFFER_STATUS, mPaymentParams.getOfferKey(), salt)) != null && postData.getCode() == PayuErrors.NO_ERROR) {
            payuHashes.setCheckOfferStatusHash(postData.getResult());
        }

        // we have generated all the hases now lest launch sdk's ui
        launchSdkUI(payuHashes);
    }*/
   /* private PostData calculateHash(String key, String command, String var1, String salt) {
        checksum = null;
        checksum = new PayUChecksum();
        checksum.setKey(key);
        checksum.setCommand(command);
        checksum.setVar1(var1);
        checksum.setSalt(salt);
        return checksum.getHash();
    }
*/

   /* public HashMap<String, String> getAllOneClickHashHelper(String merchantKey, String userCredentials) {

        // now make the api call.
        final String postParams = "merchant_key=" + merchantKey + "&user_credentials=" + userCredentials;
        HashMap<String, String> cardTokens = new HashMap<String, String>();

        try {
            //TODO Replace below url with your server side file url.
            URL url = new URL("https://payu.herokuapp.com/get_merchant_hashes");

            byte[] postParamsByte = postParams.getBytes("UTF-8");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(postParamsByte.length));
            conn.setDoOutput(true);
            conn.getOutputStream().write(postParamsByte);

            InputStream responseInputStream = conn.getInputStream();
            StringBuffer responseStringBuffer = new StringBuffer();
            byte[] byteContainer = new byte[1024];
            for (int i; (i = responseInputStream.read(byteContainer)) != -1; ) {
                responseStringBuffer.append(new String(byteContainer, 0, i));
            }

            JSONObject response = new JSONObject(responseStringBuffer.toString());

            JSONArray oneClickCardsArray = response.getJSONArray("data");
            int arrayLength;
            if ((arrayLength = oneClickCardsArray.length()) >= 1) {
                for (int i = 0; i < arrayLength; i++) {
                    cardTokens.put(oneClickCardsArray.getJSONArray(i).getString(0), oneClickCardsArray.getJSONArray(i).getString(1));
                }

            }
            // pass these to next activity

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cardTokens;
    }*/
   /* private void storeMerchantHash(String cardToken, String merchantHash) {

        final String postParams = "merchant_key=" + merchantKey + "&user_credentials=" + userCredentials + "&card_token=" + cardToken + "&merchant_hash=" + merchantHash;

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {

                    //TODO Deploy a file on your server for storing cardToken and merchantHash nad replace below url with your server side file url.
                    URL url = new URL("https://payu.herokuapp.com/store_merchant_hash");

                    byte[] postParamsByte = postParams.getBytes("UTF-8");

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setRequestProperty("Content-Length", String.valueOf(postParamsByte.length));
                    conn.setDoOutput(true);
                    conn.getOutputStream().write(postParamsByte);

                    InputStream responseInputStream = conn.getInputStream();
                    StringBuffer responseStringBuffer = new StringBuffer();
                    byte[] byteContainer = new byte[1024];
                    for (int i; (i = responseInputStream.read(byteContainer)) != -1; ) {
                        responseStringBuffer.append(new String(byteContainer, 0, i));
                    }

                    JSONObject response = new JSONObject(responseStringBuffer.toString());


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                this.cancel(true);
            }
        }.execute();
    }*/





}
