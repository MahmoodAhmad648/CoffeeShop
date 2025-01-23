package com.mahmood.coffeeshop.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mahmood.coffeeshop.Adaptors.CoffeeAdaptor;
import com.mahmood.coffeeshop.Model.CoffeeModel;
import com.mahmood.coffeeshop.R;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<CoffeeModel> arrCoffee;
    CoffeeAdaptor coffeeAdaptor;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    AlertDialog dialog;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.home_recycler_view);
        setRecyclerView();
        createLoadingDialog();
        dialog.show();
        fetchDataFromDatabase();
        return view;

    }
    private void createLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setCancelable(false);
        builder.setView(R.layout.loading);
        dialog = builder.create();

    }

    private void setRecyclerView(){
        arrCoffee = new ArrayList<>();
        // Set layout
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        // set adapter
        coffeeAdaptor = new CoffeeAdaptor(getContext(), arrCoffee);
        recyclerView.setAdapter(coffeeAdaptor);
    }

    private void fetchDataFromDatabase(){

        databaseReference = FirebaseDatabase.getInstance().getReference("Items");
        dialog.show();

        // Fetch data from Firebase
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrCoffee.clear();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    CoffeeModel coffeeModel = itemSnapshot.getValue(CoffeeModel.class);
                    if (coffeeModel != null) {
                        arrCoffee.add(coffeeModel);
                    }
                }
                coffeeAdaptor.notifyDataSetChanged();
                dialog.dismiss(); // Dismiss the dialog after loading data
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss(); // Dismiss the dialog in case of error
            }
        });

    }

}