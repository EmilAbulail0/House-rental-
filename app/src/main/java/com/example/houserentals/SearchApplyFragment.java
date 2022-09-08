package com.example.houserentals;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchApplyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchApplyFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    HouseProperties house;
    String photo = "";
    houseRentalDataBaseHelper myDataBase;
    private static final int NOTIFICATION_ID = 123;
    private static final String MY_CHANNEL_ID = "my_chanel_1";

    public SearchApplyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ApplyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchApplyFragment newInstance(String param1, String param2) {
        SearchApplyFragment fragment = new SearchApplyFragment();
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
        initDatabaseHelper();
    }
    private void initDatabaseHelper(){
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
        final Context context = getActivity();

        Bundle bundle = getArguments();
        house = (HouseProperties) bundle.getSerializable("House_obj_search");

        HouseRentalsSPM houseRentalsSPM = HouseRentalsSPM.getInstance(context);
        String email = houseRentalsSPM.readString("email","");

        TextView description = (TextView) getActivity().findViewById(R.id.description_apply);
        ImageView imageView = (ImageView) getActivity().findViewById(R.id.image_apply);
        Button apply = (Button) getActivity().findViewById(R.id.apply_button);
        DatePicker date = (DatePicker) getActivity().findViewById(R.id.date_picker);

        String postalAdd = house.getPostalAddress();
        String description1 = house.getDescription();
        description.setText(house.toString());
        imageView.setImageURI(Uri.parse((house.getPhoto())));
        photo = house.getPhoto();

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(GuestMainActivity.flag==0){
                        Toast.makeText(view.getContext(), "Accepted Successfully", Toast.LENGTH_SHORT).show();
                        //add to RENTED Table
                    final Context context = getActivity();
          HouseRentalsSPM sharedPrefManager = houseRentalsSPM.getInstance(context);
                    String emailAg = sharedPrefManager.readString("email", "");
                    houseRentalDataBaseHelper dataBaseHelperRent = new houseRentalDataBaseHelper(context, "RENTED", null, 1);
                    dataBaseHelperRent.insertRented(email, postalAdd,date.toString(), emailAg);
                        //delete from arraylist
                        ArrayList<HouseProperties> ul = HouseProperties.rentHouses;
                        for (HouseProperties o : ul) {
                            if (o.getPostalAddress().equals(postalAdd)) {
                                ul.remove(o);
                            }
                        }

                        String rentingPeriod =  date.getDayOfMonth() + "/" + (date.getMonth() + 1) + "/" + date.getYear();
                    myDataBase =new houseRentalDataBaseHelper(context,"HOUSE",null,1);
                    Cursor c = myDataBase.GetMailFromHouse(postalAdd);
                    String e = "";
                    while (c.moveToNext()){
                        Toast.makeText(getActivity(), rentingPeriod, Toast.LENGTH_SHORT).show();
                        e = c.getString(c.getColumnIndex("Email"));
                    }
                    houseRentalDataBaseHelper dataBaseHelper2 =new houseRentalDataBaseHelper(context,"APPLY",null,1);
                    dataBaseHelper2.insertApplyRenting(email, postalAdd, rentingPeriod, e);
                }
                else{
                    GuestMainActivity.flag = 0;
                    Toast.makeText(getActivity(), "You have to sign in first", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context, SignInUp.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }

    });

}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_apply_after_search, container, false);
    }
}