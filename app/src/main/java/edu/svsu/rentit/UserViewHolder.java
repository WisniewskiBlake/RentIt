package edu.svsu.rentit;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import edu.svsu.rentit.activities.ManageUserActivity;
import edu.svsu.rentit.activities.ManageUsersActivity;
import edu.svsu.rentit.activities.ViewListingActivity;
import edu.svsu.rentit.models.Listing;
import edu.svsu.rentit.models.User;

public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private User user;

    private TextView title;
    private TextView description;
    private TextView price;
    private TextView distance;

    public UserViewHolder(View view) {
        super(view);
        view.setOnClickListener(this);

        title = view.findViewById(R.id.textView1);
        description =  view.findViewById(R.id.textView2);
        price =  view.findViewById(R.id.textView3);
        distance =  view.findViewById(R.id.distance_view);
    }

    public void setModel(User newUser) {
        user = newUser;

        title.setText(user.getUsername());
        description.setText(user.getFirstname() + " " + user.getLastname());
        //description.setText(listing.getDescription());
        //price.setText(String.format("$%.2f", listing.getPrice()));
        //distance.setText(String.format("%.2f", listing.getDistance()) + " mi.");
    }

    @Override
    public void onClick(View view) {

        Context context = view.getContext();
        Intent intent = new Intent(context, ManageUserActivity.class);

        intent.putExtra("USER", user);

        context.startActivity(intent);
    }
}
