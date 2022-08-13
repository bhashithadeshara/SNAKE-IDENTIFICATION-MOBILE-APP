package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ForgotPassword extends AppCompatActivity {

    Button resetPassword;
    EditText email,password,confirmPassword;
    TextView gotoSignUp;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        resetPassword = findViewById(R.id.btnResetPassword);
        email = findViewById(R.id.InputResetEmail);
        password = findViewById(R.id.InputResetPassword);
        confirmPassword = findViewById(R.id.inputResetConfirmPassword);
        gotoSignUp = findViewById(R.id.txtgoToSignUp);

        DB = new DBHelper(this);

        gotoSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),SignInActivity.class);
                startActivity(intent);

            }
        });



        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mail = email.getText().toString();
                String pass = password.getText().toString();
                String repass = confirmPassword.getText().toString();

                if(mail.equals("")||pass.equals("")||repass.equals("")) {
                    Toast.makeText(ForgotPassword.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(pass.equals(repass)){
                        Boolean update  = DB.UpdateData(mail,pass);

                        if(update == true){
                            Toast.makeText(ForgotPassword.this, "Password Updated successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),SignInActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(ForgotPassword.this,"Cannot reset the password",Toast.LENGTH_SHORT).show();
                        }


                    }
                    else{
                        Toast.makeText(ForgotPassword.this,"Passwords not match",Toast.LENGTH_SHORT).show();
                    }
                }

            }

        });


    }
}