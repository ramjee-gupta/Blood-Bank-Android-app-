package com.ram.bloodbank;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText editText1,editText2;
    Button loginButton,registerBtn;
    ImageView imageView;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //setContentView(R.layout.);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Login");

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editText1 =(EditText) findViewById(R.id.et1);
        editText2 = (EditText) findViewById(R.id.et2);
        loginButton = (Button) findViewById(R.id.loginBtn);
        registerBtn = (Button) findViewById(R.id.registerBtn);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("user");

        progressDialog = new ProgressDialog(this);





        // Enter login credential//
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    //User df
    private  void login()
    {
        String email = editText1.getText().toString().trim();
        String pass = editText2.getText().toString().trim();

        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass))
        {
            progressDialog.setMessage("sign-in...");
            progressDialog.show();
            mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        progressDialog.dismiss();
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));


                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this,"Something went wrong! Check your email & password",Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }

                }
            });
        }
        else
        {
            Toast.makeText(LoginActivity.this,"Please Enter Valid Inputs",Toast.LENGTH_SHORT).show();
        }

        //If user not Registred click to register//
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,Register.class));
            }
        });
    }



}
