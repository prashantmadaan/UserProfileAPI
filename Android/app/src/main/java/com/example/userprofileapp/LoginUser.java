package com.example.userprofileapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.userprofileapp.pojo.User;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import androidx.fragment.app.FragmentActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class LoginUser{

    String LoginUserURL;
    FragmentActivity context;
    User User;
    String jsonData,obj,token,message;
    Boolean flag;
    JSONObject jsonObject;
    SharedPreferences sharedPreferences ;
    SharedPreferences.Editor editor;
    Response res;
    User newUser= new User();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public LoginUser(String URL,FragmentActivity con,User user) throws IOException {

        LoginUserURL=URL;
        context=con;
        User=user;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);;
        editor =PreferenceManager.getDefaultSharedPreferences(context).edit();
    }
    public void execute() {
        Gson gson = new Gson();
        String param = gson.toJson(User);

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, param);

        Request request = new Request.Builder()
                .url(LoginUserURL)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

           @Override
            public void onResponse(Call call, Response response) throws IOException {

               try {
                   jsonObject = new JSONObject(response.body().string());
                   obj = jsonObject.getString("status");
                   message=jsonObject.getString("message");
                   token = jsonObject.getString("token");

                   newUser.setStatus(obj);
                   newUser.setToken(token);
                   newUser.setMessage(message);
                   newUser.setEmail(User.getEmail());

                   Log.d("chella","Response from Cache "+newUser.getStatus());
                   Log.d("2chella","jsondata "+newUser.getMessage());
                   Log.d("chella","token "+ newUser.getToken());

                   if(newUser.getStatus().equalsIgnoreCase("Success")){
                       Log.d("sheetal","in sucees if");
                      // Toast.makeText(context, "User Logged in", Toast.LENGTH_SHORT).show();
                        UserProfileViewFragment userProfileViewFragment = new UserProfileViewFragment();
                        Bundle b = new Bundle();
                        b.putSerializable("user",newUser);
                        userProfileViewFragment.setArguments(b);
                       context.getSupportFragmentManager().beginTransaction().replace(R.id.container,userProfileViewFragment,"userProfile").addToBackStack(null).commit();

                   }else{
                       Log.d("sheetal","in sucees else");
                     //  Toast.makeText(context, "Something Went Wrong, Try Again", Toast.LENGTH_SHORT).show();

                   }

               } catch (JSONException e) {
                   Log.d("chella","Exception in parsing the JSON ");
               }

            }
        });



    }
    }