package com.example.userprofileapp;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.example.userprofileapp.pojo.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginUser extends AsyncTask<User,Integer, String> {

    String LoginUserURL;
    FragmentActivity context;

    public LoginUser(String URL,FragmentActivity con) {
        LoginUserURL=URL;
        context=con;

    }

    @Override
    protected String doInBackground(User... users) {
        String result="Failure";
        String param=users.toString();
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            URL requestURL = new URL(LoginUserURL);
            StringBuilder sb = new StringBuilder();
            connection = (HttpURLConnection) requestURL.openConnection();
            // Send the post body
            if (param != null) {
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(param.toString());
                writer.flush();
            }

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                result=reader.readLine();
            }
        }catch (Exception e){
            Log.d("debugging",e.getMessage());
        }
        return  result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(s=="Success"){

            Toast.makeText(context, "User Registered", Toast.LENGTH_SHORT).show();
            context.getSupportFragmentManager().beginTransaction().replace(R.id.container,new LoginFragment(),"tag_LoginFrag").addToBackStack(null).commit();

        }else{
            Toast.makeText(context, "Something Went Wrong, Try Again", Toast.LENGTH_SHORT).show();
        }
    }

}
