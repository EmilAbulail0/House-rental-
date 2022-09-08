package com.example.houserentals;


import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

class LoadingViewHolder1 extends RecyclerView.ViewHolder {

    public ProgressBar progressBar;

    public LoadingViewHolder1(@NonNull View itemView) {
        super(itemView);
        progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);


    }
}

class ItemViewHolder1 extends RecyclerView.ViewHolder {

    public TextView postalAddress, city;
    public ImageView imageView;


    public ItemViewHolder1(@NonNull View itemView) {
        super(itemView);
        postalAddress = (TextView) itemView.findViewById(R.id.postalAdd_fav);
        city = (TextView) itemView.findViewById(R.id.city_fav);
        imageView = (ImageView) itemView.findViewById(R.id.imageView_fav);
    }
}

class FavAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0, VIEW_TYPE_LOADING = 0;
    ILoadMore loadMore;
    boolean isLoading;
    Activity activity;
    List<HouseProperties> items;
    int visibleThreshold = 5;
    int lastVisibleItem, totalItemCount;
    houseRentalDataBaseHelper myDataBase;

    public FavAdapter(RecyclerView recyclerView, Activity activity, List<HouseProperties> items) {
        this.activity = activity;
        this.items = items;
        myDataBase = new houseRentalDataBaseHelper(activity, "USER", null, 1);
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {

                    if (loadMore != null)
                        loadMore.onLoadMore();
                }
                isLoading = true;
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public void setLoadMore(ILoadMore loadMore) {
        this.loadMore = loadMore;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(activity)
                    .inflate(R.layout.favorite_layout, parent, false);
            return new ItemViewHolder1(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(activity)
                    .inflate(R.layout.favorite_loading, parent, false);
            return new LoadingViewHolder1(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ItemViewHolder1) {

            HouseProperties item = items.get(position);
            final ItemViewHolder1 viewHolder = (ItemViewHolder1) holder;
            viewHolder.postalAddress.setText("Postal Address: " + items.get(position).getPostalAddress());
            viewHolder.city.setText(item.toString());

            Uri selectedImageUri = Uri.parse(items.get(position).getPhoto());
            if (null != selectedImageUri) {
                viewHolder.imageView.setImageURI(selectedImageUri);
            }


        } else if (holder instanceof LoadingViewHolder1) {
            LoadingViewHolder1 loadingViewHolder = (LoadingViewHolder1) holder;
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
