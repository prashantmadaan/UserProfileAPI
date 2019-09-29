package com.example.userprofileapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.userprofileapp.pojo.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductFragment extends Fragment implements ProductAdapter.prodInterface {

    View root;
    Product product;
    TextView cart_count;
    private RecyclerView prodRecyclerView;
    private RecyclerView.Adapter prodAdapter;
    private RecyclerView.LayoutManager layoutManager;
    List<Product> productList = new ArrayList<>();
    List<Product> selectedProducts = new ArrayList<>();
    String productURL = "http://ec2-3-89-187-121.compute-1.amazonaws.com:3000/xxxxxxxx";

    private OnFragmentInteractionListener mListener;

    public ProductFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ProductFragment newInstance(String param1, String param2) {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_product, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            productList = new ProductAPI(productURL,getActivity(),product).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ImageButton cart = root.findViewById(R.id.cart);
        cart_count = root.findViewById(R.id.cart_count);

        prodRecyclerView = (RecyclerView)root.findViewById(R.id.products_list);
        prodRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        prodRecyclerView.setLayoutManager(layoutManager);

        if(!productList.isEmpty()) {
            prodAdapter = new ProductAdapter(productList);
            prodAdapter.notifyDataSetChanged();
        }
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.container,new CheckoutDetailsFragment()).commit();
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(selectedProducts);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void setCounter(int counter, List<Product> selectedProductList) {
        cart_count.setText(counter);
        selectedProducts = selectedProductList;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(List<Product> products);
    }
}
