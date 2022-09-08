package com.example.houserentals;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.List;

class LoadingViewHolder3 extends RecyclerView.ViewHolder {

    public ProgressBar progressBar;

    public LoadingViewHolder3(@NonNull View itemView) {
        super(itemView);
        progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);

    }
}

class ItemViewHolder3 extends RecyclerView.ViewHolder{

    public TextView postalAddress;
    public ImageView houseImage;
    public Button viewButton;

    public ItemViewHolder3(@NonNull View itemView) {
        super(itemView);
        postalAddress = (TextView) itemView.findViewById(R.id.postalAddress);
        houseImage = (ImageView) itemView.findViewById(R.id.houseImage);
        viewButton = (Button)itemView.findViewById(R.id.view);
    }
}

public class EditPropadapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0, VIEW_TYPE_LOADING=0;
    ILoadMore loadMore;
    boolean isLoading;
    Activity activity;
    List<HouseProperties> items;
    int lastVisibleItem, totalItemCount;
    houseRentalDataBaseHelper myDataBase;

    public EditPropadapter(RecyclerView recyclerView,Activity activity, List<HouseProperties> items) {
        this.activity = activity;
        this.items = items;
        myDataBase = new houseRentalDataBaseHelper(activity, "HOUSE", null, 1);
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if(!isLoading){

                    if(loadMore!=null)
                        loadMore.onLoadMore();
                }
                isLoading = true;
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position) == null ? VIEW_TYPE_LOADING:VIEW_TYPE_ITEM;
    }

    public void setLoadMore(ILoadMore loadMore) {
        this.loadMore = loadMore;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == VIEW_TYPE_ITEM){
            View view = LayoutInflater.from(activity)
                    .inflate(R.layout.edit_home_layout,parent,false);
            return new ItemViewHolder3(view);
        }
        else if (viewType == VIEW_TYPE_LOADING){
            View view = LayoutInflater.from(activity)
                    .inflate(R.layout.favorite_loading,parent,false);
            return new LoadingViewHolder3(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        if(holder instanceof ItemViewHolder3){

            final ItemViewHolder3 viewHolder = (ItemViewHolder3) holder;
            viewHolder.postalAddress.setText(items.get(position).getPostalAddress());

            Uri selectedImageUri = Uri.parse(items.get(position).getPhoto());
            if (null != selectedImageUri) {
                viewHolder.houseImage.setImageURI(selectedImageUri);
            }
            viewHolder.viewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment editViewFragment = new EditViewFragment();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, editViewFragment).addToBackStack(null).commit();
                    Bundle bundle = new Bundle();
                    HouseProperties updatedHouse = new HouseProperties();
                    updatedHouse.setPostalAddress(items.get(position).getPostalAddress());
                    updatedHouse.setCity(items.get(position).getCity());
                    updatedHouse.setSurfaceArea(items.get(position).getSurfaceArea());
                    updatedHouse.setConstructionYear(items.get(position).getConstructionYear());
                    updatedHouse.setNumOfBedrooms(items.get(position).getNumOfBedrooms());
                    updatedHouse.setRentalPrice(items.get(position).getRentalPrice());
                    updatedHouse.setStatus(items.get(position).getStatus());
                    updatedHouse.setPhoto(items.get(position).getPhoto());
                    updatedHouse.setAvailabilityDate(items.get(position).getAvailabilityDate());
                    updatedHouse.setDescription(items.get(position).getDescription());
                    bundle.putSerializable("House_obj_edit", (Serializable) updatedHouse);
                    editViewFragment .setArguments(bundle);
                }
            });
        }
        else if (holder instanceof LoadingViewHolder3){
            LoadingViewHolder3 loadingViewHolder = (LoadingViewHolder3) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public void setLoaded() {
        isLoading = false;
    }

}
