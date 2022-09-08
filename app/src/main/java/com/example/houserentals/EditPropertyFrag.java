package com.example.houserentals;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
 * Use the {@link EditPropertyFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditPropertyFrag extends Fragment {
    EditPropadapter adapter;
    HouseRentalsSPM houseRentalsSPM;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditPropertyFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditPropFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditPropertyFrag newInstance(String param1, String param2) {
        EditPropertyFrag fragment = new EditPropertyFrag();
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
        return inflater.inflate(R.layout.fragment_edit_home, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.housePosted);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        final Context context = getActivity().getApplicationContext();
        houseRentalsSPM = HouseRentalsSPM.getInstance(context);
        String email = houseRentalsSPM.readString("email","");
        houseRentalDataBaseHelper dataBaseHelper =new houseRentalDataBaseHelper(getActivity(), "HOUSE", null, 1);
        ArrayList<HouseProperties> reservedHouses = new ArrayList<>();
        Cursor cursor = dataBaseHelper.getHouseByEmail(email);

        while (cursor.moveToNext()){
            HouseProperties updatedHouse = new HouseProperties();
            updatedHouse.setPostalAddress(cursor.getString(cursor.getColumnIndex("postalAddress")));
            updatedHouse.setCity(cursor.getString(cursor.getColumnIndex("city")));
            updatedHouse.setSurfaceArea(cursor.getString(cursor.getColumnIndex("surfaceArea")));
            updatedHouse.setConstructionYear(cursor.getString(cursor.getColumnIndex("constructionYear")));
            updatedHouse.setNumOfBedrooms(cursor.getString(cursor.getColumnIndex("numOfBedrooms")));
            updatedHouse.setRentalPrice(cursor.getString(cursor.getColumnIndex("rentalPrice")));
            updatedHouse.setStatus(cursor.getString(cursor.getColumnIndex("status")));
            updatedHouse.setPhoto(cursor.getString(cursor.getColumnIndex("photo")));
            updatedHouse.setAvailabilityDate(cursor.getString(cursor.getColumnIndex("availabilityDate")));
            updatedHouse.setDescription(cursor.getString(cursor.getColumnIndex("propertyDescription")));
            reservedHouses.add(updatedHouse);

        }
        adapter = new EditPropadapter(recyclerView, getActivity(),reservedHouses);
        recyclerView.setAdapter(adapter);

        // Set Load More Event
        adapter.setLoadMore(new ILoadMore() {
            @Override
            public void onLoadMore() {
                if(reservedHouses.size()<0){
                    reservedHouses.add(null);
                    adapter.notifyItemInserted(reservedHouses.size()-1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            reservedHouses.remove(reservedHouses.size()-1);
                            adapter.notifyItemRemoved(reservedHouses.size());
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
}