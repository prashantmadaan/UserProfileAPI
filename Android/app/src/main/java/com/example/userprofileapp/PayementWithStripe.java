package com.example.userprofileapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.Stripe;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardMultilineWidget;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class PayementWithStripe extends AppCompatActivity {

    CardMultilineWidget cardMultilineWidget;
    String token;
    Double amount;
    String chargeURL="http://192.168.48.2:3000/charge";

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payement_with_stripe);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Payment");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        token = getIntent().getStringExtra("TOKEN");
        amount = getIntent().getDoubleExtra("TOTAL_AMOUNT",0.0);

        cardMultilineWidget = findViewById(R.id.card_input_widget);
        Button save =  findViewById(R.id.saveButton);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveCard();
            }
        });

    }

    private void saveCard() {

        Card card =  cardMultilineWidget.getCard();
        if(card == null){
            Toast.makeText(getApplicationContext(),"Invalid card",Toast.LENGTH_SHORT).show();
        }else {
            if (!card.validateCard()) {
                Toast.makeText(getApplicationContext(), "Invalid card", Toast.LENGTH_SHORT).show();
            } else {
                CreateToken(card);
            }
        }
    }

    private void CreateToken( Card card) {
        Stripe stripe = new Stripe(getApplicationContext(), getString(R.string.publishablekey));
        stripe.createToken(
                card,
                new ApiResultCallback<Token>(){
                    public void onSuccess(Token token) {

                        // Send token to your server
                        Log.e("Stripe Token", token.getId());
                        Intent intent = new Intent();
                        intent.putExtra("card",token.getCard().getLast4());
                        intent.putExtra("stripe_token",token.getId());
                        intent.putExtra("cardtype",token.getCard().getBrand());
                        setResult(0077,intent);
                        Log.d("chella","Token is"+token.toString());


//                        Gson gson = new Gson();
//                        String param = gson.toJson({"amount":});
//
//                        OkHttpClient client = new OkHttpClient();
//
//                        RequestBody body = RequestBody.create(JSON, param);
//
//                        Request request = new Request.Builder()
//                                .url(chargeURL)
//                                .post(body)
//                                .build();
//
//                        client.newCall(request).enqueue(new Callback() {
//                            @Override
//                            public void onFailure(Call call, IOException e) {
//                                call.cancel();
//                            }
//
//                            @Override
//                            public void onResponse(Call call, Response response) throws IOException {
//
//                                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//                                editor =PreferenceManager.getDefaultSharedPreferences(context).edit();
//
//                                try {
//                                    jsonObject = new JSONObject(response.body().string());
//                                    obj = jsonObject.getString("status");
//                                    message=jsonObject.getString("message");
//                                    newUser.setStatus(obj);
//                                    newUser.setMessage(message);
//                                    if(newUser.getStatus().equalsIgnoreCase("Success")){
//                                        token = jsonObject.getString("token");
//                                        fname =jsonObject.getString("first_name");
//                                        newUser.setToken(token);
//                                        newUser.setEmail(User.getEmail());
//                                        newUser.setFname(fname);
//                                        Log.d("chella","First Name :"+newUser.getFname());
//                                        Log.d("chella","Response from Cache "+newUser.getStatus());
//                                        Log.d("2chella","jsondata "+newUser.getMessage());
//                                        Log.d("chella","token "+ newUser.getToken());
//
//
//                                        Log.d("sheetal","in sucees if");
//                                        // Toast.makeText(context, "User Logged in", Toast.LENGTH_SHORT).show();
//                                        UserProfileViewFragment userProfileViewFragment = new UserProfileViewFragment();
//                                        Bundle b = new Bundle();
//                                        b.putSerializable("user",newUser);
//                                        userProfileViewFragment.setArguments(b);
//                                        // context.getSupportFragmentManager().beginTransaction().replace(R.id.container,userProfileViewFragment,"userProfile").addToBackStack(null).commit();
//
//                                        ProductFragment.newInstance(newUser.getToken(),"HOME");
//                                        // Log.d("chella","User name"+User.getFname());
//
//                                        editor.putString("FNAME",newUser.getFname());
//                                        editor.putString("TOKEN",newUser.getToken());
//                                        editor.apply();
//
//                                        Log.d("chella","Shared Pref :"+ sharedPreferences.getString("TOKEN","default"));
//                                        context.getSupportFragmentManager().beginTransaction().replace(R.id.container,new ProductFragment(),"product").addToBackStack(null).commit();
//                                    }else{
//                                        Looper.prepare();
//                                        Log.d("sheetal","in sucees else");
//                                        Toast.makeText((context.getBaseContext()), "User Not Found", Toast.LENGTH_SHORT).show();
//                                        Looper.loop();
//                                    }
//
//                                } catch (JSONException e) {
//                                    Log.d("chella","Exception in parsing the JSON ");
//                                }
//
//                            }
//                        });

                        //finish();
                    }
                    public void onError(Exception error) {

                        // Show localized error message
                        Toast.makeText(getApplicationContext(),
                                error.getLocalizedMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        );
    }
}
