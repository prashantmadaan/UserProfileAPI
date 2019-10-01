package com.example.userprofileapp;

import android.media.Image;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;
import com.example.userprofileapp.pojo.Product;

import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    List<Product> productList= new ArrayList<>();
    int counter=0;
    //prodInterface object=;
    List<Product> selectedProduct;

    public ProductAdapter(List<Product> productLists, List<Product> cartProds) {

        productList = productLists;
        selectedProduct=cartProds;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_row,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("sheetal","products list"+ productList.toString());
        final Product product = productList.get(position);
        holder.prodName.setText(product.getProductName());
        holder.prodPrice.setText(product.getProductPrice().toString());
        //holder.prodImage.setImageResource(product.getProductImage());
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter=counter+1;
                selectedProduct.add(product);
            //    object.setCounter(counter,selectedProduct);

            }
        });
     //   notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView prodName,prodPrice;
        ImageView prodImage;
        Button add;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            prodName =itemView.findViewById(R.id.product_name);
            prodPrice = itemView.findViewById(R.id.product_price);
            prodImage = itemView.findViewById(R.id.prodImage);
            add = itemView.findViewById(R.id.addButton);
        }
    }
//    public interface prodInterface{
//        public void setCounter(int counter, List<Product> product);
//    }
}
