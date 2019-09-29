package com.example.userprofileapp;


import android.media.Image;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.userprofileapp.pojo.Product;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.FragmentActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProductAPI {
    String productURL;
    FragmentActivity context;
    Product product;
    String jsonData;
    List<Product> products = new ArrayList<>();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public ProductAPI(String URL,FragmentActivity con,Product prod) throws IOException {
        productURL=URL;
        context=con;
        product=prod;
    }

    public List<Product> execute() {
        Gson gson = new Gson();
        String param = gson.toJson(product);

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, param);

        Request request = new Request.Builder()
                .url(productURL)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                jsonData = response.body().string();
                Log.d("chella", "jsondata" + jsonData);
                if (jsonData.equalsIgnoreCase("success")) {
                    try {
                        JSONObject jsonObject = new JSONObject(jsonData);
                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        for(int i =0; i<jsonArray.length();i++) {
                            JSONObject prod = jsonArray.getJSONObject(i);
                            product.setDiscount(prod.getInt("discount"));
                            product.setProductName(prod.getString("name"));
                            //product.setProductImage(jsondataObject.getString("photo"));
                            product.setProductPrice(prod.getDouble("price"));
                            product.setCategory(prod.getString("region"));
                            products.add(product);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d("chella","Error in retrieving the JSONDATA");
                }
            }
        });
        return products;
    }

}
