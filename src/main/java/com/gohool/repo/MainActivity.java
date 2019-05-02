package com.gohool.repo;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public
class MainActivity extends AppCompatActivity {
    private ImageView welcomeImageView;
    private static int SPLASH_TIME_OUT= 4000;



    @Override
    protected
    void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );

        welcomeImageView= (ImageView ) findViewById ( R.id.imageView2 );



        new Handler (  ).postDelayed ( new Runnable () {
            @Override
            public
            void run() {
                Intent intent=new Intent ( MainActivity.this,SecondActivity.class );
                startActivity ( intent );
                finish ();
            }
        },SPLASH_TIME_OUT );





    }
}
