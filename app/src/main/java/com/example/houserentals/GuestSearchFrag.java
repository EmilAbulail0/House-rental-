package com.example.houserentals;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GuestSearchFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GuestSearchFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static ArrayList<String> temp;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GuestSearchFrag() {
        // Required empty public constructor
    }
    @Override
    public void onStart() {
        super.onStart();
        final Context context = getActivity().getApplicationContext();
        final EditText city = (EditText)getActivity().findViewById(R.id.guestCity);
        final EditText minS = (EditText)getActivity().findViewById(R.id.guestMSA);
        final EditText maxS = (EditText)getActivity().findViewById(R.id.guestMSurA);
        final EditText minNob = (EditText)getActivity().findViewById(R.id.guestMNOB);
        final EditText maxNob = (EditText)getActivity().findViewById(R.id.guestMaNOB);
        final EditText minP = (EditText)getActivity().findViewById(R.id.guestMP);
        final CheckBox hasBal = (CheckBox)getActivity().findViewById(R.id.hasB);
        final CheckBox hasGar = (CheckBox)getActivity().findViewById(R.id.hasG);
        final Button search = getActivity().findViewById(R.id.searchButton);
        String[] options11 = { "furnished", "unfurnished" };
        final Spinner statusSp =(Spinner) getActivity().findViewById(R.id.statusSp);
        ArrayAdapter<String> objArr = new ArrayAdapter<>(context,android.R.layout.simple_spinner_item, options11);
        statusSp.setAdapter(objArr);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp = new ArrayList<>();
                temp.add(city.getText().toString());
                temp.add(minS.getText().toString());
                temp.add(maxS.getText().toString());
                temp.add(minNob.getText().toString());
                temp.add(maxNob.getText().toString());
                temp.add(minP.getText().toString());
                temp.add(statusSp.getSelectedItem().toString());
                if (hasBal.isChecked()){
                    temp.add("Yes");
                } else {
                    temp.add("No");
                }
                if (hasGar.isChecked()){
                    temp.add("Yes");
                } else {
                    temp.add("No");
                }
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new SearchFragment()).commit();
            }
        });
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fieldSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GuestSearchFrag newInstance(String param1, String param2) {
        GuestSearchFrag fragment = new GuestSearchFrag();
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
        return inflater.inflate(R.layout.fragment_search_home, container, false);
    }
}