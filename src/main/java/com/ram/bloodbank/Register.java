package com.ram.bloodbank;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Register extends AppCompatActivity {

    private EditText editText1,editText2,editText3,editText4;
    private Button button;
    private String spinerObject;
    Spinner spin;
    Toolbar toolbar;
    private Firebase mRef;

    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private DatabaseReference dbRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Register");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference().child("Users");

        editText1 = (EditText) findViewById(R.id.et1);
        editText2 = (EditText) findViewById(R.id.et2);
        editText3 = (EditText) findViewById(R.id.et3);
        editText4 = (EditText) findViewById(R.id.et4);
        button = (Button) findViewById(R.id.b1);

        spin = (Spinner) findViewById(R.id.spin);

        String group[] = {"Blood Group","A+","A-","B+","B-","O+","O-","AB+","AB-"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,group);
        spin.setAdapter(arrayAdapter);


        progressDialog = new ProgressDialog(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });




    }
    private void registerUser()
    {
       final String name = editText1.getText().toString().trim();
        String email = editText2.getText().toString().trim();
        final String contact = editText3.getText().toString().trim();
        String password = editText4.getText().toString().trim();
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinerObject = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(contact) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(spinerObject) ){

            progressDialog.setMessage("Registering...");
            progressDialog.show();
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener
                    (new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {

                        String user_id = mAuth.getCurrentUser().getUid();
                        DatabaseReference current_user_db =  dbRef.child(user_id);
                        current_user_db.child("name").setValue(name);
                        current_user_db.child("contact").setValue(contact);
                        current_user_db.child("BloodGroup").setValue(spinerObject);

                        Toast.makeText(Register.this,"Data Saved",Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                        startActivity(new Intent(Register.this,MainActivity.class));

                    }
                }
            });
        }


    }
    @Override
    protected void onStart() {
        super.onStart();
       // mAuth.addAuthStateListener(mAuthListener);

    }
}
