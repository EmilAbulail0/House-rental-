package com.example.houserentals;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostHistoryFragment extends Fragment {

    PostHistoryAdapter postHistoryAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PostHistoryFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AgencyHistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PostHistoryFragment newInstance(String param1, String param2) {
        PostHistoryFragment fragment = new PostHistoryFragment();
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
        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.post_history_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        final Context context = getActivity().getApplicationContext();
        HouseRentalsSPM sharedPrefManager = HouseRentalsSPM.getInstance(context);
        String email = sharedPrefManager.readString("email", "");
        houseRentalDataBaseHelper dataBaseHelper = new houseRentalDataBaseHelper(getActivity(), "HOUSE", null, 1);
        ArrayList<HouseProperties> history = new ArrayList<>();
        Cursor cursor = dataBaseHelper.getHouseByEmail(email);

        while (cursor.moveToNext()){
            HouseProperties nh = new HouseProperties();
            nh.setPostalAddress(cursor.getString(cursor.getColumnIndex("postalAddress")));
            nh.setCity(cursor.getString(cursor.getColumnIndex("city")));
            nh.setSurfaceArea(cursor.getString(cursor.getColumnIndex("surfaceArea")));
            nh.setConstructionYear(cursor.getString(cursor.getColumnIndex("constructionYear")));
            nh.setNumOfBedrooms(cursor.getString(cursor.getColumnIndex("numOfBedrooms")));
            nh.setRentalPrice(cursor.getString(cursor.getColumnIndex("rentalPrice")));
            nh.setStatus(cursor.getString(cursor.getColumnIndex("status")));
            nh.setPhoto(cursor.getString(cursor.getColumnIndex("photo")));
            nh.setAvailabilityDate(cursor.getString(cursor.getColumnIndex("availabilityDate")));
            nh.setDescription(cursor.getString(cursor.getColumnIndex("propertyDescription")));
            history.add(nh);

        }

        postHistoryAdapter = new PostHistoryAdapter(recyclerView, getActivity(), history);
        recyclerView.setAdapter(postHistoryAdapter);

        // Set Load More Event
        postHistoryAdapter.setLoadMore(new ILoadMore() {
            @Override
            public void onLoadMore() {
                if (history.size() < 0) {
                    history.add(null);
                    postHistoryAdapter.notifyItemInserted(history.size() - 1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            history.remove(history.size() - 1);
                            postHistoryAdapter.notifyItemRemoved(history.size());
                            postHistoryAdapter.setLoaded();
                        }
                    }, 5000);
                } else {
                    Toast.makeText(getActivity(), "Done!", Toast.LENGTH_LONG);
                }

            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_history, container, false);
    }
}