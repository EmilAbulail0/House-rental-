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

class ItemViewHolder2 extends RecyclerView.ViewHolder{

    public TextView postalAddress, city;
    public ImageView imageView;
    public Button serButoon;

    public ItemViewHolder2(@NonNull View itemView) {
        super(itemView);
        postalAddress = (TextView) itemView.findViewById(R.id.postalAdd_view);
        city = (TextView) itemView.findViewById(R.id.city_view);
        imageView = (ImageView) itemView.findViewById(R.id.imageView_view);
        serButoon = (Button) itemView.findViewById(R.id.button_view_ser);
    }
}

class LoadingViewHolder2 extends RecyclerView.ViewHolder{

    public ProgressBar progressBar;
    public LoadingViewHolder2(@NonNull View itemView) {
        super(itemView);
        progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
    }
}

class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0, VIEW_TYPE_LOADING=0;
    ILoadMore loadMore;
    boolean isLoading;
    Activity activity;
    List<HouseProperties> items;
    int lastVisibleItem, totalItemCount;
    houseRentalDataBaseHelper myDataBase;

    public SearchAdapter(RecyclerView recyclerView,Activity activity, List<HouseProperties> items) {
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
                    .inflate(R.layout.view_layout,parent,false);
            return new ItemViewHolder2(view);
        }
        else if (viewType == VIEW_TYPE_LOADING){
            View view = LayoutInflater.from(activity)
                    .inflate(R.layout.favorite_loading,parent,false);
            return new LoadingViewHolder2(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        if(holder instanceof ItemViewHolder2){

            HouseProperties house = items.get(position);
            final ItemViewHolder2 viewHolder = (ItemViewHolder2) holder;
            viewHolder.postalAddress.setText("POSTAl ADDRESS: " + items.get(position).getPostalAddress());
            viewHolder.city.setText("CITY:" + items.get(position).getCity());

            Uri selectedImageUri = Uri.parse(items.get(position).getPhoto());
            if (null != selectedImageUri) {
                viewHolder.imageView.setImageURI(selectedImageUri);
            }

            viewHolder.serButoon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment searchApplyFragment = new SearchApplyFragment();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, searchApplyFragment).addToBackStack(null).commit();

                    Bundle bundle = new Bundle();
                    HouseProperties house = new HouseProperties();
                    house.setPostalAddress(items.get(position).getPostalAddress());
                    house.setCity(items.get(position).getCity());
                    house.setSurfaceArea(items.get(position).getSurfaceArea());
                    house.setConstructionYear(items.get(position).getConstructionYear());
                    house.setNumOfBedrooms(items.get(position).getNumOfBedrooms());
                    house.setRentalPrice(items.get(position).getRentalPrice());
                    house.setStatus(items.get(position).getStatus());
                    house.setPhoto(items.get(position).getPhoto());
                    house.setAvailabilityDate(items.get(position).getAvailabilityDate());
                    house.setDescription(items.get(position).getDescription());
                    bundle.putSerializable("House_obj_search", (Serializable) house);
                    searchApplyFragment.setArguments(bundle);

                }
            });
        }
        else if (holder instanceof LoadingViewHolder2){
            LoadingViewHolder2 loadingViewHolder = (LoadingViewHolder2) holder;
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
