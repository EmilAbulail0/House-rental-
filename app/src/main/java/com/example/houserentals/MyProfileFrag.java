package com.example.houserentals;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyProfileFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyProfileFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    HouseRentalsSPM houseRentalsSPM;
    public UserClass user;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyProfileFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static MyProfileFrag newInstance(String param1, String param2) {
        MyProfileFrag fragment = new MyProfileFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Context context = getActivity().getApplicationContext();
        houseRentalsSPM = HouseRentalsSPM.getInstance(context);
        String email = houseRentalsSPM.readString("email","");
        Button searchButton = (Button) getActivity().findViewById(R.id.searchButton);
        final EditText password = (EditText) getActivity().findViewById(R.id.userPass);
        EditText username = (EditText) getActivity().findViewById(R.id.userName);
        houseRentalDataBaseHelper myDataBase =new houseRentalDataBaseHelper(getActivity(), "USER", null, 1);
        Cursor cursor = myDataBase.getFavByEmail(email);
        ArrayList<UserClass> userList = UserClass.allUsers;
        for(UserClass temp : userList) {
            if(temp.getEmail().equals(email)){
                user = temp;
            }
        }
        password.setText(user.getPassword());
        username.setText(email);


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    int flag = 0;
                    if(!SignInUp.isValidPassword(password.getText().toString())){
                        password.setError("InValid password");
                        flag=1;
                    }

                    if( !SignInUp.isValidEmail(username.getText().toString())) {
                        username.setError("InValid Email");
                        flag = 1;
                    }


                    if(flag==0){
                        houseRentalDataBaseHelper myDataBase =new houseRentalDataBaseHelper(getActivity(), "USER", null, 1);
                        Cursor cursor = myDataBase.getAllUsers();
                        String message;
                        while(cursor.moveToNext()){
                            if (cursor.getString(0).equals(email)){
                                user.setEmail(username.getText().toString());
                                user.setPassword(password.getText().toString());
                                if(myDataBase.updateUser(user) > 0)
                                  message = "Updated successfully";
                                else message = "Update failed";

                                Toast toast = Toast.makeText(getActivity(),
                                        message, Toast.LENGTH_LONG);
                                toast.show();

                            }
                        }
                    }


            }
        });

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
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);

    }

}