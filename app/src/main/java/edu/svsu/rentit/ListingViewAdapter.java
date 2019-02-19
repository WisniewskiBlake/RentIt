package edu.svsu.rentit;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.svsu.rentit.activities.MainActivity;
import edu.svsu.rentit.activities.ViewListingActivity;
import edu.svsu.rentit.models.Listing;

public class ListingViewAdapter extends RecyclerView.Adapter {

    ArrayList<Listing> exampleListFull;
    ArrayList<Listing> exampleList;
    Context context;

    class ListingViewHolder1 extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Listing listing;
        private TextView title;
        private TextView description;
        private TextView price;

        public ListingViewHolder1(View view) {
            super(view);
           // view.setOnClickListener(this);
            title = (TextView) view.findViewById(R.id.textView1);
            description = (TextView) view.findViewById(R.id.textView2);
            price = (TextView) view.findViewById(R.id.textView3);
        }
        public void setModel(Listing newListing)
        {
            listing = newListing;
            title.setText(listing.getTitle());
            description.setText(listing.getDescription());
            price.setText(listing.getPrice());
        }
        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            Intent intent = new Intent(context, ViewListingActivity.class);
            intent.putExtra("LISTING", listing);
            context.startActivity(intent);
        }
    }
    public ListingViewAdapter(ArrayList<Listing> exampleList, Context context) {
        this.exampleList = exampleList;
        exampleListFull = new ArrayList<>(exampleList);
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_item, viewGroup, false);

        ListingViewHolder1 holder = new ListingViewHolder1(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((ListingViewHolder1)viewHolder).setModel(exampleListFull.get(i));
    }

    @Override
    public int getItemCount() {
        return exampleListFull.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void insert(int pos, Listing listing) {
        exampleListFull.add(pos, listing);
        notifyItemInserted(pos);
    }

    public void remove(Listing listing) {
        int pos = exampleListFull.indexOf(listing);
        exampleListFull.remove(pos);
        notifyItemRemoved(pos);
    }

}
