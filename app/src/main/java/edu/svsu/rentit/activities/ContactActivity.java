package edu.svsu.rentit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import edu.svsu.rentit.R;

public class ContactActivity extends AppCompatActivity {
    private EditText editText_To;
    private EditText editText_subject;
    private EditText editText_message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        editText_To = findViewById(R.id.edit_to_email);
        editText_subject = findViewById(R.id.edit_subject);
        editText_message = findViewById(R.id.edit_message_email);
        Button btn_send = findViewById(R.id.btn_send_email);

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if(bd != null){

            String getName = (String) bd.get("User_name");
            editText_To.setText(getName);
            String getSubject = (String) bd.get("Subject_name");
            editText_subject.setText(getSubject);
        }


        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });
    }

    private void sendMail(){
        String recipient = editText_To.getText().toString();
        String[] recipients = recipient.split(",");
        String subject = editText_subject.getText().toString();
        String message = editText_message.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT, "RentIT: " + subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an email Client"));

    }

}
