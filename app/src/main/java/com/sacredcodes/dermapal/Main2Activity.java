package com.sacredcodes.dermapal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import DeepLearning.DbInsert;
import Models.Doctor;
import Models.Remedy;

public class Main2Activity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    List<Remedy> mList = new ArrayList<>();

    TextView disease ;
    TextView description ;
    TextView remedy ;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mAuth = FirebaseAuth.getInstance();
        String currentUser = mAuth.getCurrentUser().toString();

        disease = findViewById(R.id.Disease);
        description = findViewById(R.id.Description);
        remedy = findViewById(R.id.remedy);
        listView = findViewById(R.id.listView);

        DbInsert db = new DbInsert();


        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("Remedy");
        ref.addValueEventListener(new ValueEventListener() {

            public static final String TAG = "Tag";


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot unit : dataSnapshot.getChildren()) {
                // TODO: handle the post here
                Remedy user = unit.getValue(Remedy.class);
                // profileName.setText(user.getUserName().toString());

                mList.add(user);


               }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message

                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }


        });



 disease.setText("Dermatofibroma");
    description.setText("Dermatofibroma (superficial benign fibrous histiocytoma) is a common cutaneous nodule of unknown etiology that occurs more often in women. Dermatofibroma frequently develops on the extremities (mostly the lower legs) and is usually asymptomatic, although pruritus and tenderness can be present.");
    remedy.setText("Visit Your Nearest Doctor ASAP");

        ArrayAdapter<Doctor> doc = new ArrayAdapter<Doctor>(

                Main2Activity.this, android.R.layout.simple_list_item_1, db.docs()

        );

    listView.setAdapter(doc);

    }
    }


