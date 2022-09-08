package com.example.houserentals;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewTenantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewTenantFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ViewTenantFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewTenantFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewTenantFragment newInstance(String param1, String param2) {
        ViewTenantFragment fragment = new ViewTenantFragment();
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

        String emailTenant = (String) getArguments().getSerializable("Email_Tenant");

        TextView firstName = (TextView) getActivity().findViewById(R.id.view_fn);
        TextView lastName = (TextView) getActivity().findViewById(R.id.view_ln);
        TextView email = (TextView) getActivity().findViewById(R.id.view_email);
        TextView nationality = (TextView) getActivity().findViewById(R.id.view_nationality);
        TextView phone = (TextView) getActivity().findViewById(R.id.view_phone);
        TextView city = (TextView) getActivity().findViewById(R.id.view_city);
        TextView family = (TextView) getActivity().findViewById(R.id.view_family);

        houseRentalDataBaseHelper dataBaseHelper = new houseRentalDataBaseHelper(getActivity(), "TENANT", null, 1);
        Cursor cursor = dataBaseHelper.getTenantFromEmail(emailTenant);

        while (cursor.moveToNext()) {
            firstName.setText(cursor.getString(cursor.getColumnIndex("First_name")));
            lastName.setText(cursor.getString(cursor.getColumnIndex("Last_name")));
            email.setText(cursor.getString(cursor.getColumnIndex("Email")));
            nationality.setText(cursor.getString(cursor.getColumnIndex("Nationality")));
            phone.setText("0"+cursor.getString(cursor.getColumnIndex("Phone_number")));
            city.setText(cursor.getString(cursor.getColumnIndex("City")));
            family.setText(cursor.getString(cursor.getColumnIndex("Family_Size")));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_tenant, container, false);
    }
}