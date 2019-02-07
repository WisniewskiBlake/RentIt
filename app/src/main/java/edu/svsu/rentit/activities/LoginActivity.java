package edu.svsu.rentit.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import edu.svsu.rentit.workers.LoginBackgroundWorker;
import edu.svsu.rentit.R;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        btnLogin = findViewById(R.id.button_login);
        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                EditText etUserName = findViewById(R.id.username);
                String username = etUserName.getEditableText().toString();
                EditText etPassword = findViewById(R.id.password);
                String password = etPassword.getEditableText().toString();

                LoginBackgroundWorker loginWorker = new LoginBackgroundWorker(LoginActivity.this);
                loginWorker.execute(username, password);
            }
        });
    }

}