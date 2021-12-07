package com.example.thenavynd;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thenavynd.Adapters.ProductAdapter;
import com.example.thenavynd.Models.Products;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LatestArrivedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LatestArrivedFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LatestArrivedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LatestArrivedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LatestArrivedFragment newInstance(String param1, String param2) {
        LatestArrivedFragment fragment = new LatestArrivedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    RecyclerView recyclerView;
    ProductAdapter productAdapter;

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
        View view = inflater.inflate(R.layout.fragment_latest_arrived, container, false);

        recyclerView = view.findViewById(R.id.latest_arrived_recycler_view);
        productAdapter = new ProductAdapter(container.getContext());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(container.getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        productAdapter.setData(getListProducts());
        recyclerView.setAdapter(productAdapter);

        return view;
    }

    private List<Products> getListProducts() {
        List<Products> list = new ArrayList<>();
        list.add(new Products(1,1,R.drawable.image_1,"Product 1","200000","Cực sịn",13));
        list.add(new Products(2,2,R.drawable.image_2,"Product 2","200000","Cực sịn",13));
        list.add(new Products(3,3,R.drawable.image_3,"Product 3","200000","Cực sịn",13));
        list.add(new Products(4,2,R.drawable.image_4,"Product 4","200000","Cực sịn",13));
        list.add(new Products(5,3,R.drawable.image_5,"Product 5","200000","Cực sịn",13));
        list.add(new Products(6,1,R.drawable.image_6,"Product 6","200000","Cực sịn",13));

        return list;
    }
}