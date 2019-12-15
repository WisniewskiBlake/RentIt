package edu.svsu.rentit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import edu.svsu.rentit.activities.RegisterActivity;
import edu.svsu.rentit.activities.ViewListingActivity;
import edu.svsu.rentit.models.Listing;

public class ListingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Listing listing;

    private TextView title;
    private TextView description;
    private TextView price;
    private TextView distance;
    private ImageView image;

    public ListingViewHolder(View view) {
        super(view);
        view.setOnClickListener(this);

        title = view.findViewById(R.id.textView1);
        description =  view.findViewById(R.id.textView2);
        price =  view.findViewById(R.id.textView3);
        distance =  view.findViewById(R.id.distance_view);
        image =  view.findViewById(R.id.img_view);
    }

    public void setModel(Listing newListing) {
        listing = newListing;

        title.setText(listing.getTitle());
        description.setText(listing.getDescription());
        price.setText(String.format("$%.2f", listing.getPrice()));
        distance.setText(String.format("%.2f", listing.getDistance()) + " mi.");
        int list_image = listing.getImage();
        image.setImageResource(list_image);

//        if(list_image != null) {
//            byte[] imageBytes = Base64.decode(list_image, Base64.DEFAULT);
//            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
//            image.setImageBitmap(decodedImage);
//        }
    }

    @Override
    public void onClick(View view) {

        Context context = view.getContext();
        Intent intent = new Intent(context, ViewListingActivity.class);

        intent.putExtra("LISTING_ID", listing.getId());

        context.startActivity(intent);
    }
}
