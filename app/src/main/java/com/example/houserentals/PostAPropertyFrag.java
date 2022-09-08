package com.example.houserentals;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostAPropertyFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostAPropertyFrag extends Fragment {
    int SELECT_PICTURE = 200;
    // One Button
    Button selectImagButton;
    // One Preview Image
    ImageView IVPreviewImage;
    Uri selectedImageUri;
    HouseRentalsSPM houseRentalsSPM;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PostAPropertyFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment postPFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PostAPropertyFrag newInstance(String param1, String param2) {
        PostAPropertyFrag fragment = new PostAPropertyFrag();
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
        return inflater.inflate(R.layout.fragment_post_home, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        final Context context = getActivity().getApplicationContext();
        houseRentalsSPM = HouseRentalsSPM.getInstance(context);
        String email = houseRentalsSPM.readString("email","");
        selectImagButton = (Button) getActivity().findViewById(R.id.selectImagB);
        IVPreviewImage = (ImageView) getActivity().findViewById(R.id.IVPreviewImage);
        // handle the Choose Image button to trigger
        // the image chooser function
        final EditText  address = (EditText)getActivity().findViewById(R.id.postAdd);
        final EditText surfaceA = (EditText)getActivity().findViewById(R.id.surArea);
        final EditText consrtYear = (EditText)getActivity().findViewById(R.id.constYear);
        final EditText numOfB = (EditText)getActivity().findViewById(R.id.numberOB);
        final EditText rentalP = (EditText)getActivity().findViewById(R.id.rentalP);
        final EditText availDate = (EditText)getActivity().findViewById(R.id.availDate);
        final EditText desc = (EditText)getActivity().findViewById(R.id.descrip);
        final EditText city = (EditText)getActivity().findViewById(R.id.cityName);
        final Button postButton = getActivity().findViewById(R.id.post);
        final ImageView imageView = (ImageView)getActivity().findViewById(R.id.imageViewv);
        final CheckBox garden = (CheckBox) getActivity().findViewById(R.id.checkBox_garden);
        final CheckBox balcony = (CheckBox) getActivity().findViewById(R.id.checkBox_balcony);
        String[] statusOp = { "furnished", "unfurnished" };
        final Spinner opSpinner =(Spinner) getActivity().findViewById(R.id.natSp);
        ArrayAdapter<String> objGenderArr = new ArrayAdapter<>(context,android.R.layout.simple_spinner_item, statusOp);
        opSpinner.setAdapter(objGenderArr);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    houseRentalDataBaseHelper myDataBase =new houseRentalDataBaseHelper(context,"HOUSE",null,1);
                    HouseProperties newHouse = new HouseProperties();
                    newHouse.setPostalAddress(address.getText().toString());
                    newHouse.setSurfaceArea(surfaceA.getText().toString());
                    newHouse.setConstructionYear(consrtYear.getText().toString());
                    newHouse.setNumOfBedrooms(numOfB.getText().toString());
                    newHouse.setRentalPrice(rentalP.getText().toString());
                    newHouse.setAvailabilityDate(availDate.getText().toString());
                    newHouse.setDescription(desc.getText().toString());
                    newHouse.setCity(city.getText().toString());
                    newHouse.setStatus(opSpinner.getSelectedItem().toString());
                    newHouse.setPhoto(selectedImageUri.toString());
                    if (garden.isChecked()){
                        newHouse.setHasGarden("Yes");
                    }else {
                        newHouse.setHasGarden("No");
                    }
                    if (balcony.isChecked()){
                        newHouse.setHasBalcony("Yes");
                    } else {
                        newHouse.setHasBalcony("No");
                    }
                    HouseProperties.rentHouses.add(newHouse);
                    myDataBase.insertHouse(newHouse,email);
                    Toast.makeText(context, "Added Property Done Successfully", Toast.LENGTH_SHORT).show();
                }
        });

        selectImagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });
    }
    void imageChooser() {
        Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == SELECT_PICTURE) {
                selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    IVPreviewImage.setImageURI(selectedImageUri);
                }
            }

    }
}