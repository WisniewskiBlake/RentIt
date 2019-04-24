package edu.svsu.rentit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import edu.svsu.rentit.models.User;
import edu.svsu.rentit.workers.GetListingBackgroundWorker;
import edu.svsu.rentit.workers.ProfileBackgroundWorker;
import edu.svsu.rentit.R;
import edu.svsu.rentit.workers.SubmitReviewBackgroundWorker;

public class ProfileActivity extends AppCompatActivity {

    User currentUser;

    Toolbar toolbar;
    TextView txt_Name;
    TextView txt_Bio;

    RatingBar ratingBar;

    String id;
    Button btn_Submit;
    String sReviewCount;
    String sReview;
    TextView review_text;
    TextView setReview_text;
    TextView textView7;
    Button editProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txt_Name = findViewById(R.id.tv_Profilename);
        txt_Bio = findViewById(R.id.textView_bio);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        // Grab User object from Intent
        if (getIntent().hasExtra("CURRENT_USER")) {
            currentUser = (User)getIntent().getSerializableExtra("CURRENT_USER");
            setUser(currentUser);
        }

        if (getIntent().hasExtra("USER_ID")) {

            ProfileBackgroundWorker profileWorker = new ProfileBackgroundWorker(ProfileActivity.this);
            profileWorker.execute(getIntent().getStringExtra("USER_ID"));
        }

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                double newReview = ratingBar.getRating();
                double review = Double.parseDouble(sReview);
                double reviewCount = Double.parseDouble(sReviewCount);
                double finalReview = ((review * reviewCount) + newReview) / (reviewCount + 1);
                String sFinalReview = Double.toString(finalReview);
                reviewCount = reviewCount + 1;
                sReviewCount = Double.toString(reviewCount);
//                ratingBar.setRating(Float.parseFloat(sFinalReview));

                SubmitReviewBackgroundWorker reviewWorker = new SubmitReviewBackgroundWorker(ProfileActivity.this);
                reviewWorker.execute(sFinalReview, id, sReviewCount);
            }
        });


    }


    public void setUser(User outputUser)
    {
        setOutput(outputUser.getFirstname(), outputUser.getBio(), outputUser.getReview(), outputUser.getReviewCount(), outputUser.getIdString());
    }

    public void setOutput(String name, String bio, String review, String reviewCount, String id)
    {
        toolbar.setTitle(name + "'s Profile");
        txt_Name.setText(name);
        txt_Bio.setText(bio);

        this.sReviewCount = reviewCount;
        this.sReview = review;
        this.id = id;
        ratingBar.setRating(Float.parseFloat(sReview));

    }

}