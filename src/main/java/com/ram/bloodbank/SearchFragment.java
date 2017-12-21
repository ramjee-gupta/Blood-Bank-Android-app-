package com.ram.bloodbank;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.support.v4.os.LocaleListCompat.create;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {


    public SearchFragment() {
        // Required empty public constructor
    }



    Spinner spin;

    ArrayList<String> muser= new ArrayList<>();
    Map<String,String> map = new HashMap<>();
    ListView listView;
    DatabaseReference ref;
    private String spinerObject;
    FirebaseAuth mAuth;
    Firebase mRef;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        // Inflate the layout for this fragment

        spin = (Spinner) view.findViewById(R.id.spin);
        listView = (ListView) view.findViewById(R.id.listview);

        String group[] = {"Select Blood Group","A+","A-","B+","B-","O+","O-","AB+","AB-"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_dropdown_item_1line,group);
        //ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,group);
        spin.setAdapter(arrayAdapter);
        //String muser[] = {"Select Blood Group","A+","A-","B+","B-","O+","O-","AB+","AB-"};
       final ArrayAdapter<String>  arrayAdapte1 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_dropdown_item_1line,muser);

        listView.setAdapter(arrayAdapte1);
        mAuth = FirebaseAuth.getInstance();
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               // spin.setSelection(1);
                spinerObject = adapterView.getItemAtPosition(i).toString();

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

                Query query = reference.child("Users").orderByChild("BloodGroup").equalTo(spinerObject);

                query.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        //  Map<String , String> map = dataSnapshot.getValue(Map.class);


                        muser.clear();
                        //Log.v("")
                        for(DataSnapshot data : dataSnapshot.getChildren())
                        {
                            // map = dataSnapshot.getValue(Map.class);




                            String name = data.getValue(String.class);

                            muser.add(name);
                            arrayAdapte1.notifyDataSetChanged();


                        }
                        //String name  = dataSnapshot.getKey();
                        //muser.add(name);


                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                Toast.makeText(getContext(),spinerObject,Toast.LENGTH_SHORT);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

               /* AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("Select Blood Group");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();*/
            }
        });



        /* try {
            final DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Users");

            database.orderByChild("child/name")
                    .equalTo("Pankaj")
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //Timber.d(dataSnapshot.toString());
                            String value = dataSnapshot.getValue(String.class);
                            muser.add(value);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
        } catch (Exception ex) {
            ex.printStackTrace();
        }

      /*  mRef = new Firebase("https://bloodbank-bacad.firebaseio.com/Users");

        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(com.firebase.client.DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getValue(String.class);
                muser.add(value);
                arrayAdapte1.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(com.firebase.client.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(com.firebase.client.DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(com.firebase.client.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        /*ref = FirebaseDatabase.getInstance().getReference().child("Users");

        //ref..orderByChild("name").equals("A+")
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                muser.add(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
        return view;
    }



}
