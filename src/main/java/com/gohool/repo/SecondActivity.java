package com.gohool.repo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;

public
class SecondActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;



    @Override
    protected
    void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_second );


        //firebase database Instance
        databaseReference= FirebaseDatabase.getInstance ().getReference ().child ("Main");


        //Assign Instance to Firebase Auth;
        mAuth = FirebaseAuth.getInstance ();
        mAuthListener= new FirebaseAuth.AuthStateListener () {
            @Override
            public
            void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //logic check main
                FirebaseUser user = firebaseAuth.getCurrentUser ();
                if(user!=null){

                }

            }
        };

        // Spinner element
        //  Spinner spinner = ( Spinner ) findViewById(R.id.spinner);

        // Spinner click listener
//        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        String categories[] = {"a visitor", "a contractor", "signing out"};

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String> ( this,
                android.R.layout.simple_dropdown_item_1line, categories );

        arrayAdapter.setDropDownViewResource ( R.layout.support_simple_spinner_dropdown_item );

        final MaterialBetterSpinner betterSpinner = ( MaterialBetterSpinner ) findViewById ( R.id.spinner );

        betterSpinner.setAdapter ( arrayAdapter );

        betterSpinner.setOnItemSelectedListener ( new AdapterView.OnItemSelectedListener () {
            @Override
            public
            void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String record;
                switch (position) {
                    case 0:
                         record = "a visitor";
                        break;


                    case 1:
                        record = "signing out";
                        break;

                }
            }

            @Override
            public
            void onNothingSelected(AdapterView<?> parent) {

            }
        } );

       betterSpinner.setOnItemClickListener ( new AdapterView.OnItemClickListener () {
           @Override
           public
           void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               if (position==0){
                   Intent intent= new Intent ( view.getContext (),ThirdActivity.class );
                   startActivityForResult ( intent,0 );
               }


               if (position==1){
                   Intent intent=new Intent ( view.getContext (),SignOutActivity.class );
                   startActivityForResult ( intent,2 );
               }
           }
       } );
    }

    @Override
    public
    void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public
    void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public
    void onNothingSelected(AdapterView<?> parent) {

    }
}



