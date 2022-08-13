package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    Button signUp;
    EditText email,password,confirmPassword;
    TextView gotoSignIn;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUp = findViewById(R.id.btnSignUp);
        email = findViewById(R.id.InputSignUpEmail);
        password = findViewById(R.id.InputSignUpPassword);
        confirmPassword = findViewById(R.id.inputSignUpConfirmPassword);
        gotoSignIn = findViewById(R.id.txtgoToSignIn);

        DB = new DBHelper(this);

        gotoSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),SignInActivity.class);
                startActivity(intent);

            }
        });



        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mail = email.getText().toString();
                String pass = password.getText().toString();
                String repass = confirmPassword.getText().toString();

                if(mail.equals("")||pass.equals("")||repass.equals("")) {
                    Toast.makeText(SignUpActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(pass.equals(repass)){
                        Boolean checkuser  = DB.checkemail(mail);

                        if(checkuser== false){
                            Boolean insert = DB.insertData(mail,pass);
                            if(insert == true){
                                Toast.makeText(SignUpActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(SignUpActivity.this,"Registration failed",Toast.LENGTH_SHORT).show();
                            }

                        }
                        else{
                            Toast.makeText(SignUpActivity.this,"Email already registered! please sign in",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(SignUpActivity.this,"Passwords not match",Toast.LENGTH_SHORT).show();
                    }
                }

            }

        });



    }
}