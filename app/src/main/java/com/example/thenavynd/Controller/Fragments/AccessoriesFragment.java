package com.example.thenavynd.Controller.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
//import android.widget.SearchView;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thenavynd.Controller.Adapters.ProductAdapter;
import com.example.thenavynd.Controller.Adapters.ProductAdapter;
import com.example.thenavynd.Models.Products;
import com.example.thenavynd.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirestoreRegistrar;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccessoriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccessoriesFragment extends Fragment {
    FirebaseFirestore db;
    RecyclerView recyclerView;
    ProductAdapter productAdapter;
    private EditText searchField;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public AccessoriesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccessoriesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccessoriesFragment newInstance(String param1, String param2) {
        AccessoriesFragment fragment = new AccessoriesFragment();
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
        View view = inflater.inflate(R.layout.fragment_accessories,container,false);


        searchField = view.findViewById(R.id.searchField);
        recyclerView = view.findViewById(R.id.accessories_recycler_view);
        productAdapter = new ProductAdapter(container.getContext());

        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                productAdapter.getFilter().filter(editable.toString());

            }});


        GridLayoutManager gridLayoutManager = new GridLayoutManager(container.getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        List<Products> list = new ArrayList<>();

        db = FirebaseFirestore.getInstance();
        db.collection("Products").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        Products p = document.toObject(Products.class);
                        if(p.getCategoryId() == 4){
                            list.add(p);
                        }
                        productAdapter.notifyDataSetChanged();
                    }
                }else
                {
                    Toast.makeText(getActivity(), "Error: "+ task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        productAdapter.setData(list);
        recyclerView.setAdapter(productAdapter);


        //Inflate the layout for this fragment
        return view;
    }

}