package edu.svsu.rentit.activities;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import edu.svsu.rentit.models.User;
import edu.svsu.rentit.utilities.PermissionManager;
import edu.svsu.rentit.workers.LoginBackgroundWorker;
import edu.svsu.rentit.R;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    CheckBox cbRemember;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        cbRemember = findViewById(R.id.cbRemember);
        cbRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                // Store password in shared pref if remember checkbox
                // was checked
                if (cbRemember.isChecked()) {
                    requestPermission();
                }

            }
        });

        btnLogin = findViewById(R.id.button_login);
        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                // Grab input values
                EditText etUserName = findViewById(R.id.username);
                String username = etUserName.getEditableText().toString();
                EditText etPassword = findViewById(R.id.password);
                String password = etPassword.getEditableText().toString();

                boolean remember = cbRemember.isChecked();

                // Call login script
                LoginBackgroundWorker loginWorker = new LoginBackgroundWorker(LoginActivity.this);
                loginWorker.execute(username, password, String.valueOf(remember));
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.d("DEBUG", "PERMISSION RESULT " + String.valueOf(requestCode) + " - " + String.valueOf(permissions[0]) + " - " + String.valueOf(grantResults[0]));
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Uncheck remember CheckBox when storage permission is not granted
        if (grantResults[0] == -1) {
            cbRemember.setChecked(false);
        }
    }

    public void requestPermission()
    {
        // Only 23+ has access to permissions
        if (Build.VERSION.SDK_INT >= 23) {

            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                Log.d("DEBUG", "WRITE EXTERNAL PERMISSION  NOT  GRANTED");

                ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PermissionManager.WRITE_EXTERNAL_CODE);

            } else {
                Log.d("DEBUG", "WRITE EXTERNAL PERMISSION GRANTED");
            }

        }
    }



}