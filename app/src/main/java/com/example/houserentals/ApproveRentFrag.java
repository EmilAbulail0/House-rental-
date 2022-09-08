package com.example.houserentals;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ApproveRentFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ApproveRentFrag extends Fragment {

    HouseRentalsSPM houseRentalsSPM;
    houseRentalDataBaseHelper myDataBase;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ApproveRentFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RentalAppFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ApproveRentFrag newInstance(String param1, String param2) {
        ApproveRentFrag fragment = new ApproveRentFrag();
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
        initializeDB();
    }
    private void initializeDB(){
        Context context = getActivity().getApplicationContext();
        if(myDataBase == null){
            myDataBase = new houseRentalDataBaseHelper(context, "APPLY", null, 1);
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        Context context = getActivity().getApplicationContext();
        myDataBase = new houseRentalDataBaseHelper(context, "APPLY", null, 1);

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.approve_rent_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        final Context context = getActivity().getApplicationContext();
        houseRentalsSPM = HouseRentalsSPM.getInstance(context);
        String agencyMail = houseRentalsSPM.readString("email","");
        myDataBase = new houseRentalDataBaseHelper(context, "APPLY", null, 1);
            ArrayList<String> postalAddress = new ArrayList<>();
            ArrayList<String> tenantMail = new ArrayList<>();
            ArrayList<String> periodOfRent = new ArrayList<>();

        Cursor cursor = myDataBase.GetRentalByEmail(agencyMail);

        while (cursor.moveToNext()){
            postalAddress.add(cursor.getString(cursor.getColumnIndex("postalAddress")));
            tenantMail.add(cursor.getString(cursor.getColumnIndex("Email")));
            periodOfRent.add(cursor.getString(cursor.getColumnIndex("rentingPeriod")));
        }

        RentalAppAdapter adapter = new RentalAppAdapter(recyclerView, getActivity(), postalAddress, tenantMail, periodOfRent);
            recyclerView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rental_application, container, false);
    }
}