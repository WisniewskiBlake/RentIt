package edu.svsu.rentit.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import edu.svsu.rentit.R;
import edu.svsu.rentit.workers.RegisterBackgroundWorker;

public class RegisterActivity extends AppCompatActivity   {

    private static final String TAG = "Testing: ";

    Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        btnRegister = findViewById(R.id.button_Register);
        btnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                EditText etFirstName = findViewById(R.id.txt_FirstName);
                final String User_FirstName = etFirstName.getEditableText().toString();
                EditText etLastName = findViewById(R.id.txt_LastName);
                final String User_LastName = etLastName.getEditableText().toString();
                EditText etEmail = findViewById(R.id.txt_Email);
                final String User_Email = etEmail.getEditableText().toString();
                EditText etPassword = findViewById(R.id.txt_VerifyPassword);
                final String User_Password = etPassword.getEditableText().toString();


                RegisterBackgroundWorker registerWorker = new RegisterBackgroundWorker(RegisterActivity.this);
                registerWorker.execute(User_FirstName, User_LastName, User_Email, User_Password);
            }
        });
    }

}