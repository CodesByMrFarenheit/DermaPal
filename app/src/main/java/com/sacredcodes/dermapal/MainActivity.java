package com.sacredcodes.dermapal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import DeepLearning.DbInsert;
import Models.User;

public class MainActivity extends AppCompatActivity {
    //Firebase
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    // Layouts
    RelativeLayout layoutMain;
    RelativeLayout layoutSignIn;
    RelativeLayout layoutSignUp;
    RelativeLayout layoutReg;
    RelativeLayout layoutTermsOfUse;

    //main Layout

    Button signInButton;
    Button signUpButton;


    //Sign In Layout
    Button signInButton2;
    EditText userEmail;
    EditText userPassword;

    //Sign Up layout
    EditText userEmailSignUp;
    EditText userPasswordSignUp;
    Button goToRegButton;

    //Reg Layout
    ImageView genderMale;
    ImageView genderFemale;
    Button SignUpButton2;
    EditText userName;
    EditText userPhone;
    EditText userAge;
    String userGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();



        signInButton = findViewById(R.id.signInButton);
        signUpButton = findViewById(R.id.signUpButton);
        layoutMain = findViewById(R.id.layoutMain);
        layoutSignIn = findViewById(R.id.layoutSignIn);
        layoutSignUp = findViewById(R.id.layoutSignUp);
        layoutReg = findViewById(R.id.layoutReg);
        layoutTermsOfUse = findViewById(R.id.layoutTermsOfUse);

        //Sign In Layout
        signInButton2 = findViewById(R.id.signInButton2);
        userEmail =  findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userPassword);

        //Sign Up layout
        userEmailSignUp = findViewById(R.id.userEmailSignUp);
        userPasswordSignUp =  findViewById(R.id.userPasswordSignUp);
        goToRegButton = findViewById(R.id.goToRegButton);

        //Reg Layout
        genderMale =  findViewById(R.id.genderMale);
        genderFemale = findViewById(R.id.genderFemale);
        SignUpButton2 =  findViewById(R.id.signUpButton2);
        userName = findViewById(R.id.userName);
        userPhone = findViewById(R.id.userPhone);
        userAge = findViewById(R.id.userAge);


        //Layout Initial Status
        layoutMain.setVisibility(View.VISIBLE);
        layoutSignIn.setVisibility(View.INVISIBLE);
        layoutSignUp.setVisibility(View.INVISIBLE);
        layoutReg.setVisibility(View.INVISIBLE);
        layoutTermsOfUse.setVisibility(View.INVISIBLE);

    genderMale.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            userGender = "male";
            genderFemale.setImageResource(R.drawable.female);
            genderMale.setImageResource(R.drawable.malethumsup);

        }
    });

    genderFemale.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            userGender = "female";
            genderMale.setImageResource(R.drawable.male);
            genderFemale.setImageResource(R.drawable.femalethumsup);
        }
    });


        SignUpButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName1= userName.getText().toString().trim();
                String userEmail1 = userEmailSignUp.getText().toString().trim();
                String userAge1 = userAge.getText().toString().trim();
                String userPhone1 = userPhone.getText().toString().trim();
                String userGender1 = userGender;
                String userPassword1 = userPasswordSignUp.getText().toString().trim();

                User  user = new User(userEmail1, userName1, userGender1, userAge1, userPhone1);

                signUpFirebase(user, userPassword1);
            }
        });


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                layoutMain.setVisibility(View.GONE);
                layoutSignUp.setVisibility(View.VISIBLE);
            }
        }
    );

    signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                layoutMain.setVisibility(View.GONE);
                layoutSignIn.setVisibility(View.VISIBLE);
            }
        }
    );

    signInButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                layoutSignIn.setVisibility(View.GONE);
                String userName1 = userEmail.getText().toString().trim();
                String userPassword1 = userPassword.getText().toString().trim();

                signInFirebase(userName1, userPassword1);


            }
        }
    );


    goToRegButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            layoutSignUp.setVisibility(View.GONE);
            layoutReg.setVisibility(View.VISIBLE);
        }
    });




//End of OnCreate
    }

    private void signUpFirebase(final User user1, String password) {
        mAuth.createUserWithEmailAndPassword(user1.getUserEmail(), password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            public static final String TAG = "Tag" ;

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    User user = new User(user1.getUserEmail(),user1.getUserName(), user1.getUserGender(), user1.getUserAge(), user1.getUserPhone());
                    mDatabase= FirebaseDatabase.getInstance().getReference();
                    FirebaseUser user2 = mAuth.getCurrentUser();
                    mDatabase.child("Users").child(user2.getUid()).setValue(user);


                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
                    Toast.makeText(MainActivity.this, "Sign Up .",
                            Toast.LENGTH_SHORT).show();

                    updateUI(user2);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(MainActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }

        });




    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void signInFirebase(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    public static final String TAG = "Tag" ;
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }


                    }

                });



    }

    private void updateUI(FirebaseUser user) {


        if(user != null)
        {

            //String userEmail= user.getEmail();
            Intent i = new Intent(this, HomeActivity.class);
            //i.putExtra("userEmail", userEmail);
            startActivity(i);
        }


    }
}
