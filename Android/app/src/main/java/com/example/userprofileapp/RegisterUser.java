package com.example.userprofileapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.StringRequest;
import com.example.userprofileapp.pojo.User;
import com.google.gson.Gson;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPostHC4;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.StringEntityHC4;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

/*

public class RegisterUser  extends AsyncTask<User,Integer, String> {

    String registerUserURL;
    FragmentActivity context;

    public RegisterUser(String URL,FragmentActivity con) {
        registerUserURL=URL;
        context=con;

    }

    @Override
    protected String doInBackground(User... users) {
        String result="Failure";
        Gson gson = new Gson();
        String param = gson.toJson(users);
       // String param=users;
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            URL requestURL = new URL(registerUserURL);
            StringBuilder sb = new StringBuilder();
            connection = (HttpURLConnection) requestURL.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            Log.d("sheetal",param.toString());
            if (users != null) {
                // Send the post body
                Log.d("sheetal","inside user not null");
               */
/* DataOutputStream wr = new DataOutputStream (connection.getOutputStream());
                wr.writeBytes(param);
                wr.flush ();
                wr.close ();*//*


               OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(param.toString());
                writer.flush();
                writer.close();
            }


            // Send the post body


            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Log.d("sheetal","inside reponse code");
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
               result=reader.readLine();
            }else{
                Log.d("sheetal","httpUrl else");

            }
        }catch (Exception e){
            Log.d("debugging",e.getMessage());
        }
        return  result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d("sheetal",s);
        if(s=="Success"){
            Toast.makeText(context, "User Registered", Toast.LENGTH_SHORT).show();
            context.getSupportFragmentManager().beginTransaction().replace(R.id.container,new LoginFragment(),"tag_LoginFrag").addToBackStack(null).commit();

        }else{
            Toast.makeText(context, "Something Went Wrong, Try Again", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
*/
/*
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(context.getBaseContext().CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {


        }*//*


    }
}
*/


public class RegisterUser {

    String registerUserURL;
    FragmentActivity context;
    User User;
    String jsonData;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public RegisterUser(String URL,FragmentActivity con,User user) throws IOException {
        registerUserURL=URL;
        context=con;
        User=user;
    }

    public void execute() {

/*

        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("email", "sh@as.com ") // A sample POST field
                .add("password", "12345678") // Another sample POST field
                .add("first_name", "sheetal") // Another sample POST field
                .add("last_name", "patil") // Another sample POST field
                .build();
        Request request = new Request.Builder()
                .url(registerUserURL) // The URL to send the data to
                .post(formBody)
                .build();
*/

      //  Response response= new Response.Builder().


       // String postBody="{\"email\": \"as@as.com\",\"password\": \"12345678\",\"first_name\": \"sheetal\",\"last_name\": \"patil\"}";

        Gson gson = new Gson();
        String param = gson.toJson(User);

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, param);

        Request request = new Request.Builder()
                .url(registerUserURL)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
              //  Log.d("sheetal",response.body().string());
                //result[0] =response.body().string();
               // Log.v(TAG, response.body().string());
                 jsonData = response.body().string();

            }
        });
        Log.d("sheetal","jsondata"+jsonData);
        if(jsonData =="success"){
            Toast.makeText(context, "User Registered", Toast.LENGTH_SHORT).show();
            context.getSupportFragmentManager().beginTransaction().replace(R.id.container,new LoginFragment(),"tag_LoginFrag").addToBackStack(null).commit();

        }else{
            Toast.makeText(context, "Something Went Wrong, Try Again", Toast.LENGTH_SHORT).show();
        }


    }




}

