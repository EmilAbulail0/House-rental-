package com.example.houserentals;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AgHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0, VIEW_TYPE_LOADING = 0;
    ILoadMore loadMore;
    boolean isLoading;
    Activity activity;
    List<String> email;
    List<String> period;
    List<String> postal;
    int lastVisibleItem, totalItemCount;
    houseRentalDataBaseHelper dataBaseHelper, dataBaseHelper1;

    public AgHistoryAdapter(RecyclerView recyclerView, Activity activity, List<String> period, List<String> postal, List<String> email) {
        this.email = email;
        this.period = period;
        this.postal = postal;
        this.activity = activity;
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
        return email.get(position) == null ? VIEW_TYPE_LOADING:VIEW_TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_ITEM){
            View view = LayoutInflater.from(activity)
                    .inflate(R.layout.agency_history_layout,parent,false);
            return new ItemViewHolderAgHis(view);
        }
        else if (viewType == VIEW_TYPE_LOADING){
            View view = LayoutInflater.from(activity)
                    .inflate(R.layout.favorite_loading,parent,false);
            return new LoadingViewHolderAgHis(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        houseRentalDataBaseHelper dataBaseHelper = new houseRentalDataBaseHelper(activity, "HOUSE", null, 1);
        Cursor cursor = dataBaseHelper.getByPostal(postal.get(position));
        String propCity = "";
        while (cursor.moveToNext()){
            propCity = cursor.getString(cursor.getColumnIndex("city"));
        }

        dataBaseHelper1 = new houseRentalDataBaseHelper(activity, "TENANT", null, 1);
        Cursor cursor1 = dataBaseHelper1.getTenantFromEmail(email.get(position));
        String firstName = "";
        String lastName = "";
        while (cursor1.moveToNext()){
            firstName = cursor1.getString(cursor1.getColumnIndex("First_name"));
            lastName = cursor1.getString(cursor1.getColumnIndex("Last_name"));
        }

        if(holder instanceof ItemViewHolderAgHis){

            final ItemViewHolderAgHis viewHolder = (ItemViewHolderAgHis) holder;
            viewHolder.h.setText("Postal: " + postal.get(position) + ", City: " + propCity);
            viewHolder.per.setText("Period: " + period.get(position));
            viewHolder.ten.setText("Name: " + firstName  + " Last Name: " + lastName);

        }
        else if (holder instanceof LoadingViewHolderAgHis){
            LoadingViewHolderAgHis loadingViewHolder = (LoadingViewHolderAgHis) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return email.size();
    }
    public void setLoaded() {
        isLoading = false;
    }
    public void setLoadMore(ILoadMore loadMore) {
        this.loadMore = loadMore;
    }

}

class LoadingViewHolderAgHis extends RecyclerView.ViewHolder {

    public ProgressBar progressBar;

    public LoadingViewHolderAgHis(@NonNull View itemView) {
        super(itemView);
        progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);

    }
}

class ItemViewHolderAgHis extends RecyclerView.ViewHolder{

    public TextView h, per, ten;

    public ItemViewHolderAgHis(@NonNull View itemView) {
        super(itemView);
        h = (TextView) itemView.findViewById(R.id.his_house);
        per = (TextView) itemView.findViewById(R.id.his_period);
        ten = (TextView) itemView.findViewById(R.id.his_tenant);

    }
}
