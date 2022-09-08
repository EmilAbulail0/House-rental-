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

public class TnHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0, VIEW_TYPE_LOADING = 0;
    ILoadMore loadMore;
    boolean isLoading;
    Activity activity;
    List<String> period;
    List<String> postal;
    List<String> emailAg;
    int lastVisibleItem, totalItemCount;

    public TnHistoryAdapter(RecyclerView recyclerView, Activity activity, List<String> period, List<String> postal, List<String> emailAg) {
        this.activity = activity;
        this.emailAg = emailAg;
        this.period = period;
        this.postal = postal;
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
        return postal.get(position) == null ? VIEW_TYPE_LOADING:VIEW_TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_ITEM){
            View view = LayoutInflater.from(activity)
                    .inflate(R.layout.tenant_history_layout,parent,false);
            return new ItemViewHolder5(view);
        }
        else if (viewType == VIEW_TYPE_LOADING){
            View view = LayoutInflater.from(activity)
                    .inflate(R.layout.favorite_loading,parent,false);
            return new LoadingViewHolder5(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        houseRentalDataBaseHelper dataBaseHelper1 = new houseRentalDataBaseHelper(activity, "HOUSE", null, 1);
        Cursor cursor1 = dataBaseHelper1.getByPostal(postal.get(position));
        String city = "";
        while (cursor1.moveToNext()){
            city = cursor1.getString(cursor1.getColumnIndex("city"));
        }

        houseRentalDataBaseHelper dataBaseHelper2 = new houseRentalDataBaseHelper(activity, "AGENCY", null, 1);
        Cursor cursor2 = dataBaseHelper2.getAgencyByEmail(emailAg.get(position));
        String name = "";
        while(cursor2.moveToNext()){
            name = cursor2.getString(cursor2.getColumnIndex("Name"));
        }
        if(holder instanceof ItemViewHolder5){
            final ItemViewHolder5 viewHolder = (ItemViewHolder5) holder;
            viewHolder.house.setText("Postal: " + postal.get(position) + ", City: " + city);
            viewHolder.period.setText("Period: "+ period.get(position));
            viewHolder.name.setText("Name: " + name);

        }
        else if (holder instanceof LoadingViewHolder5){
            LoadingViewHolder5 loadingViewHolder = (LoadingViewHolder5) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return postal.size();
    }
    public void setLoaded() {
        isLoading = false;
    }
    public void setLoadMore(ILoadMore loadMore) {
        this.loadMore = loadMore;
    }

}

class LoadingViewHolder5 extends RecyclerView.ViewHolder {

    public ProgressBar progressBar;

    public LoadingViewHolder5(@NonNull View itemView) {
        super(itemView);
        progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
    }
}

class ItemViewHolder5 extends RecyclerView.ViewHolder{

    public TextView house, period, name;

    public ItemViewHolder5(@NonNull View itemView) {
        super(itemView);
        house = (TextView) itemView.findViewById(R.id.ag_house);
        period = (TextView) itemView.findViewById(R.id.ag_period);
        name = (TextView) itemView.findViewById(R.id.ag_name);

    }
}
