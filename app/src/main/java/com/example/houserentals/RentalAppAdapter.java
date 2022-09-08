package com.example.houserentals;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class RentalAppAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0, VIEW_TYPE_LOADING=0;
    ILoadMore loadMore;
    boolean isLoading;
    Activity activity;
    List<String> postalAdd;
    List<String> email;
    List<String> period;
    int lastVisibleItem, totalItemCount;
    houseRentalDataBaseHelper myDataBase;

    public RentalAppAdapter(RecyclerView recyclerView, Activity activity, List<String> postalAdd, List<String> email, List<String> period) {
        this.activity = activity;
this.postalAdd = postalAdd;
this.email = email;
this.period = period;
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

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == VIEW_TYPE_ITEM){
            View view = LayoutInflater.from(activity)
                    .inflate(R.layout.rental_application_layout,parent,false);
            return new ItemViewHolderRental(view);
        }
        else if (viewType == VIEW_TYPE_LOADING){
            View view = LayoutInflater.from(activity)
                    .inflate(R.layout.favorite_loading,parent,false);
            return new LoadingViewHolderRental(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        if(holder instanceof ItemViewHolderRental){
            final ItemViewHolderRental viewHolder = (ItemViewHolderRental) holder;

            viewHolder.postal.setText(postalAdd.get(position));
            viewHolder.email.setText(email.get(position));
            viewHolder.period.setText(period.get(position));


            viewHolder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Bundle bundle1 = new Bundle();
                    bundle1.putString("Email_Tenant", email.get(position));
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment viewTenantFragment = new ViewTenantFragment();
                    viewTenantFragment.setArguments(bundle1);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, viewTenantFragment).addToBackStack(null).commit();
                }
            });


        }
        else if (holder instanceof LoadingViewHolderRental){
            LoadingViewHolderRental loadingViewHolder = (LoadingViewHolderRental) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }

    }

    @Override
    public int getItemCount() {
        return period.size();
    }

    @Override
    public int getItemViewType(int position) {
        return period.get(position) == null ? VIEW_TYPE_LOADING:VIEW_TYPE_ITEM;
    }


    public void setLoaded() {
        isLoading = false;
    }
    public void setLoadMore(ILoadMore loadMore) {
        this.loadMore = loadMore;
    }


    class LoadingViewHolderRental extends RecyclerView.ViewHolder {

        public ProgressBar progressBar;

        public LoadingViewHolderRental(@NonNull View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);

        }
    }

    class ItemViewHolderRental extends RecyclerView.ViewHolder{

        public TextView postal;
        public TextView email;
        public TextView period;
        public Button view;

        public ItemViewHolderRental(@NonNull View itemView) {
            super(itemView);
            email = (TextView) itemView.findViewById(R.id.email_rent_app);
            postal = (TextView) itemView.findViewById(R.id.postal_rent_app);
            period = (TextView) itemView.findViewById(R.id.period_rent_app);
            view = (Button)itemView.findViewById(R.id.viewTenant_rent_app);


        }
    }


}
