package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText userName;
    private EditText password;
    private Button login;
    private TextView signUp;
    private TextView state;
    static int counter=0;
    database DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = (EditText)findViewById(R.id.etUserName);
        password = (EditText)findViewById(R.id.etPassword);
        login = (Button) findViewById(R.id.loginButton);
        signUp = (TextView) findViewById(R.id.tvsignup);
        DB = new database(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userName.getText().toString().equals("")||password.getText().toString().equals(""))
                    Toast.makeText(MainActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else{
                    verify(userName.getText().toString(),password.getText().toString());
                }

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, signUpActivity.class);
                startActivity(intent);
            }
        });
    }

    public void verify(String userName,String password){
        Boolean checkuserpass = DB.checkusernamepassword(userName, password);
        if(checkuserpass==true){
            Toast.makeText(MainActivity.this, "Sign in successfull", Toast.LENGTH_SHORT).show();
            Intent intent  = new Intent(getApplicationContext(),AllMusics.class);
            startActivity(intent);
        }else{
            counter++;
            if(counter==3) {
                Intent intent = new Intent(MainActivity.this, signUpActivity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(MainActivity.this,"you have" + (3-counter) +"attempts left", Toast.LENGTH_SHORT).show();
            }

        }
    }

}