package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

public class signUpActivity extends AppCompatActivity {

    private EditText firstName;
    private EditText lastName;
    private EditText mail;
    private EditText phoneNumber;
    private EditText password;
    private EditText repassword;
    private TextView imageSelect;
    private Button register;
    int SELECT_PICTURE = 200;
    database DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firstName = (EditText)findViewById(R.id.etFirstName);
        lastName= (EditText)findViewById(R.id.etLastName);
        mail = (EditText) findViewById(R.id.etMailAdress);
        phoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        password = (EditText) findViewById(R.id.etPasswordSignUp);
        repassword = (EditText) findViewById(R.id.etReenterPassword);
        imageSelect = (TextView) findViewById(R.id.tvImage);
        register = (Button) findViewById(R.id.registerButton);
        DB = new database(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(firstName.getText().toString().equals("")||password.getText().toString().equals("")||repassword.getText().toString().equals("")||lastName.getText().toString().equals("")||mail.getText().toString().equals("")||phoneNumber.getText().toString().equals(""))
                    Toast.makeText(signUpActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else{
                    if(password.getText().toString().equals(repassword.getText().toString())){
                        Boolean checkuser = DB.checkusername(firstName.getText().toString());
                        if(checkuser==false){
                            Boolean insert = DB.insertData(firstName.getText().toString(), password.getText().toString());
                            if(insert==true){
                                Toast.makeText(signUpActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                String to = mail.getText().toString();
                                String subject = "hi!";
                                String message = firstName.getText().toString() + lastName.getText().toString()
                                        + mail.getText().toString() + phoneNumber.getText().toString();

                                Intent email = new Intent(Intent.ACTION_SEND);

                                email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
                                email.putExtra(Intent.EXTRA_SUBJECT, subject);
                                email.putExtra(Intent.EXTRA_TEXT, message);

                                email.setType("message/rfc822");

                                startActivity(Intent.createChooser(email, "Choose an Email client :"));
                            }else{
                                Toast.makeText(signUpActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(signUpActivity.this, "User already exists! please sign in", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(signUpActivity.this, "Passwords not matching", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        imageSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
            }
        });

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // foto kaydet
                }
            }
        }
    }


}
