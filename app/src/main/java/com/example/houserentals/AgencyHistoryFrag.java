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
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AgencyHistoryFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AgencyHistoryFrag extends Fragment {

    AgHistoryAdapter agHistoryAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AgencyHistoryFrag() {
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
    public static AgencyHistoryFrag newInstance(String param1, String param2) {
        AgencyHistoryFrag fragment = new AgencyHistoryFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.agency_history_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        final Context context = getActivity().getApplicationContext();
        HouseRentalsSPM sharedPrefManager = HouseRentalsSPM.getInstance(context);
        String emailAg = sharedPrefManager.readString("email", "");
        houseRentalDataBaseHelper dataBaseHelper = new houseRentalDataBaseHelper(getActivity(), "RENTED", null, 1);
        Cursor cursor = dataBaseHelper.getRentedByAgencyMail(emailAg);

        ArrayList<String> email = new ArrayList<>();
        ArrayList<String> postal = new ArrayList<>();
        ArrayList<String> period = new ArrayList<>();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                period.add(cursor.getString(cursor.getColumnIndex("rentingPeriod")));
                postal.add(cursor.getString(cursor.getColumnIndex("postalAddress")));
                email.add(cursor.getString(cursor.getColumnIndex("Email")));
            }

            AgHistoryAdapter agHistoryAdapter = new AgHistoryAdapter(recyclerView, getActivity(), period, postal, email);
            recyclerView.setAdapter(agHistoryAdapter);
        } else {
            Toast.makeText(getActivity(), "No History Yet!", Toast.LENGTH_SHORT).show();
        }

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
        return inflater.inflate(R.layout.fragment_agency_history, container, false);
    }
}