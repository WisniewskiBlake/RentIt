package edu.svsu.rentit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.svsu.rentit.models.Listing;

public class ListingViewAdapter extends RecyclerView.Adapter{

    ArrayList<Listing> list = new ArrayList<Listing>();
    Context context;

    public ListingViewAdapter(ArrayList<Listing> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_item, viewGroup, false);

        ListingViewHolder holder = new ListingViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        ((ListingViewHolder)viewHolder).setModel(list.get(i));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void insert(int pos, Listing listing) {
        list.add(pos, listing);
        notifyItemInserted(pos);
    }

    public void remove(Listing listing) {
        int pos = list.indexOf(listing);
        list.remove(pos);
        notifyItemRemoved(pos);
    }
}
