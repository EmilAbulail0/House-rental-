package com.example.houserentals;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteFiveFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteFiveFrag extends Fragment {

    FavAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Object HomePage;

    public FavoriteFiveFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoriteFiveFrag newInstance(String param1, String param2) {
        FavoriteFiveFrag fragment = new FavoriteFiveFrag();
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.TopHouses);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new FavAdapter(recyclerView, getActivity(), HouseProperties.rentHouses);
        recyclerView.setAdapter(adapter);

        // Set Load More Event
        adapter.setLoadMore(new ILoadMore() {
            @Override
            public void onLoadMore() {
                if(HouseProperties.rentHouses.size()<0){
                    HouseProperties.rentHouses.add(null);
                    adapter.notifyItemInserted(HouseProperties.rentHouses.size()-1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            HouseProperties.rentHouses.remove(HouseProperties.rentHouses.size()-1);
                            adapter.notifyItemRemoved(HouseProperties.rentHouses.size());
                            adapter.setLoaded();
                        }
                    },5000);
                }
                else{
                    Toast.makeText(getActivity(),"Done loading data",Toast.LENGTH_LONG);
                }

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }
}