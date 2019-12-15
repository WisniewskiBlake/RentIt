package edu.svsu.rentit.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.svsu.rentit.R;

public class HomeScreenActivity extends AppCompatActivity {

    Button loginButton;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeScreenActivity.this.startActivity(new Intent(HomeScreenActivity.this, LoginActivity.class));
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeScreenActivity.this.startActivity(new Intent(HomeScreenActivity.this, RegisterActivity.class));
            }
        });

    }
}
