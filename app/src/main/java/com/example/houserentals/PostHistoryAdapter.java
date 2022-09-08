package com.example.houserentals;

import android.annotation.SuppressLint;
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

public class PostHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0, VIEW_TYPE_LOADING = 0;
    ILoadMore loadMore;
    boolean isLoading;
    Activity activity;
    List<HouseProperties> items;
    int lastVisibleItem, totalItemCount;
    houseRentalDataBaseHelper myDataBase;

    public PostHistoryAdapter(RecyclerView recyclerView, Activity activity, List<HouseProperties> items) {
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
                if (!isLoading) {
                    if (loadMore != null)
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

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_ITEM){
            View view = LayoutInflater.from(activity)
                    .inflate(R.layout.post_history_layout,parent,false);
            return new ItemViewHolder4(view);
        }
        else if (viewType == VIEW_TYPE_LOADING){
            View view = LayoutInflater.from(activity)
                    .inflate(R.layout.favorite_loading,parent,false);
            return new LoadingViewHolder4(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if(holder instanceof ItemViewHolder4){

            final ItemViewHolder4 viewHolder = (ItemViewHolder4) holder;
            viewHolder.postal.setText("Postal Address:" + items.get(position).getPostalAddress());
            viewHolder.city.setText("City:" + items.get(position).getCity());

            Uri selectedImageUri = Uri.parse(items.get(position).getPhoto());
            if (null != selectedImageUri) {
                viewHolder.imgv.setImageURI(selectedImageUri);
            }
        }
        else if (holder instanceof LoadingViewHolder4){
            LoadingViewHolder4 loadingViewHolder = (LoadingViewHolder4) holder;
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
    public void setLoadMore(ILoadMore loadMore) {
        this.loadMore = loadMore;
    }

}

class LoadingViewHolder4 extends RecyclerView.ViewHolder {

    public ProgressBar progressBar;

    public LoadingViewHolder4(@NonNull View itemView) {
        super(itemView);
        progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);

    }
}

class ItemViewHolder4 extends RecyclerView.ViewHolder{

    public TextView postal, city;
    public ImageView imgv;

    public ItemViewHolder4(@NonNull View itemView) {
        super(itemView);
        postal = (TextView) itemView.findViewById(R.id.postal_post_his);
        imgv = (ImageView) itemView.findViewById(R.id.image_post_his);
        city = (TextView) itemView.findViewById(R.id.city_post_his);
    }
}
