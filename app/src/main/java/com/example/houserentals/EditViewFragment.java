package com.example.houserentals;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditViewFragment extends Fragment {
    HouseProperties house;
    houseRentalDataBaseHelper myDataBase;
    EditText surfaceArea, constructionYear, numberOfBeds, rentalPrice, availableDate, description, status, city;
    TextView address;
    Button editButton, deleteButton;
    ImageView imageView;
    String photo = "";
    int flag = 0;
    int SELECT_PICTURE = 200;
    // One Button
    Button BSelectImage;
    // One Preview Image
    ImageView IVPreviewImage;
    Uri selectedImageUri;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditViewFragment newInstance(String param1, String param2) {
        EditViewFragment fragment = new EditViewFragment();
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
        return inflater.inflate(R.layout.fragment_edit_view, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        house = (HouseProperties) bundle.getSerializable("House_obj_edit");
        address = (TextView)getActivity().findViewById(R.id.pa2_edit_view);
        surfaceArea = (EditText)getActivity().findViewById(R.id.surfaceArea);
        constructionYear = (EditText)getActivity().findViewById(R.id.consYear);
        numberOfBeds = (EditText)getActivity().findViewById(R.id.numOfBeds);
        rentalPrice = (EditText)getActivity().findViewById(R.id.rentalPrice);
        availableDate = (EditText)getActivity().findViewById(R.id.avaDate);
        description = (EditText)getActivity().findViewById(R.id.description);
        city = (EditText)getActivity().findViewById(R.id.city);
        editButton = getActivity().findViewById(R.id.editButton);
        deleteButton = getActivity().findViewById(R.id.deleteButton);
        status = getActivity().findViewById(R.id.status);
        imageView = (ImageView)getActivity().findViewById(R.id.IVPreviewImage2);

        address.setText(house.getPostalAddress());
        surfaceArea.setText(house.getSurfaceArea());
        constructionYear.setText(house.getConstructionYear());
        numberOfBeds.setText(house.getNumOfBedrooms());
        rentalPrice.setText(house.getNumOfBedrooms());
        availableDate.setText(house.getAvailabilityDate());
        description.setText(house.getDescription());
        city.setText(house.getCity());
        status.setText(house.getStatus());
        imageView.setImageURI(Uri.parse((house.getPhoto())));
        photo = house.getPhoto();


    }
    @Override
    public void onStart() {
        super.onStart();
        final Context context = getActivity().getApplicationContext();
        HouseRentalsSPM houseRentalsSPM = HouseRentalsSPM.getInstance(context);
        String email = houseRentalsSPM.readString("email","");
        BSelectImage = (Button) getActivity().findViewById(R.id.BSelectImage2);
        IVPreviewImage = (ImageView) getActivity().findViewById(R.id.IVPreviewImage2);
        myDataBase = new houseRentalDataBaseHelper(context, "HOUSE", null, 1);


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HouseProperties newHouse = new HouseProperties();
                newHouse.setPostalAddress(address.getText().toString());
                newHouse.setSurfaceArea(surfaceArea.getText().toString());
                newHouse.setConstructionYear(constructionYear.getText().toString());
                newHouse.setNumOfBedrooms(numberOfBeds.getText().toString());
                newHouse.setRentalPrice(rentalPrice.getText().toString());
                newHouse.setAvailabilityDate(availableDate.getText().toString());
                newHouse.setDescription(description.getText().toString());
                newHouse.setCity(city.getText().toString());
                newHouse.setStatus(status.getText().toString());
                if(flag==1){
                    newHouse.setPhoto(selectedImageUri.toString());
                    flag = 0;
                }
                else{
                    newHouse.setPhoto(photo);
                }

                HouseProperties editedHouse = new HouseProperties();
                ArrayList<HouseProperties> rentHouses = HouseProperties.rentHouses;
                int i =0;
                for(HouseProperties o : rentHouses) {

                    if(o.getPostalAddress().equals(address.getText().toString())){
                        editedHouse.setPostalAddress(address.getText().toString());
                        editedHouse.setSurfaceArea(surfaceArea.getText().toString());
                        editedHouse.setConstructionYear(constructionYear.getText().toString());
                        editedHouse.setNumOfBedrooms(numberOfBeds.getText().toString());
                        editedHouse.setRentalPrice(rentalPrice.getText().toString());
                        editedHouse.setAvailabilityDate(availableDate.getText().toString());
                        editedHouse.setDescription(description.getText().toString());
                        editedHouse.setCity(city.getText().toString());
                        editedHouse.setStatus(status.getText().toString());
                        if(flag==1){
                            editedHouse.setPhoto(selectedImageUri.toString());
                            flag = 0;
                        }
                        else{
                            editedHouse.setPhoto(photo);
                        }

                        HouseProperties.rentHouses.set(i,editedHouse);
                    }
                    i +=1;
                }
                myDataBase.updateByPostal(newHouse);
                Toast.makeText(context, "House updated successfully", Toast.LENGTH_SHORT).show();
            }

        });

        BSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myDataBase.deleteByPostal(address.getText().toString());
                ArrayList<HouseProperties> ul = HouseProperties.rentHouses;
                for(HouseProperties o : ul) {
                    if(o.getPostalAddress().equals(address.getText().toString())){
                        ul.remove(o);
                    }
                }
                Toast.makeText(context, "House deleted successfully", Toast.LENGTH_SHORT).show();

            }
        });
    }
    void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PICTURE) {
            selectedImageUri = data.getData();
            if (null != selectedImageUri) {
                IVPreviewImage.setImageURI(selectedImageUri);
                flag = 1;
            }
        }

    }
}