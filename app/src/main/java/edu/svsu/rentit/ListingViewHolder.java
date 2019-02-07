package edu.svsu.rentit;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import edu.svsu.rentit.activities.RegisterActivity;
import edu.svsu.rentit.activities.ViewListingActivity;
import edu.svsu.rentit.models.Listing;

public class ListingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Listing listing;

    private TextView title;
    private TextView description;
    private TextView price;

    public ListingViewHolder(View view) {
        super(view);
        view.setOnClickListener(this);

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
