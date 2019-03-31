package com.sacredcodes.dermapal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Button cambutton;
    ImageView image;
    Button uploadbutton;
    Bitmap bitmap = null;
    public FirebaseUser mUser;
    private FirebaseAuth mAuth;
    String ImgUrl;



    private StorageReference mStorage;
    String currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        currentUser = mUser.getUid();

        cambutton=findViewById(R.id.cambutton);
        image=findViewById(R.id.image);
        uploadbutton = findViewById(R.id.uploadbutton);

        mStorage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://dermapal-5af43.appspot.com");

        uploadbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bitmap != null) {
                    StorageReference mountainsRef = mStorage.child("DermaImages").child(currentUser);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] data1 = baos.toByteArray();

                    UploadTask uploadTask = mountainsRef.putBytes(data1);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                            ImgUrl = taskSnapshot.getDownloadUrl().toString().trim();

                            sendData(ImgUrl);

                            //new Intent
                            //Intent i = new Intent(HomeActivity.this, Main2Activity.class);
                            //i.putExtra("userEmail", userEmail);
                            //startActivity(i);

                        }
                    });

                }

            }
        });
        cambutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);
                cambutton.setText("Retry");
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //end of oncreate
    }


    private String formatJson(String imgUrl){
    JSONObject json = new JSONObject();

    try{
        json.put("imgUrl", imgUrl);

        return  json.toString(1);
    }catch(JSONException e)
    {
        e.printStackTrace();
    }


        return null;
    }

    private void sendData(String imgUrl){

        final String json = formatJson(imgUrl);

        new AsyncTask<Void, Void, String>(){


            @Override
            protected String doInBackground(Void... voids) {
                
                
                
                return getServerResponse(json);
            }

            @Override
            protected void onPostExecute(String s) {
                Toast.makeText(HomeActivity.this,s + "StartingNext Intent" ,
                        Toast.LENGTH_LONG).show();

                Intent i = new Intent(HomeActivity.this, Main2Activity.class);
                //i.putExtra("userEmail", userEmail);
                startActivity(i);

            }
        }.execute();





    }

    private String getServerResponse(String json) {
        HttpPost post = new HttpPost("http://103.250.36.82:5002/pred.py");

        try {
            StringEntity entity = new StringEntity(json);

            post.setEntity(entity);
            post.setHeader("Content-type", "application/json");

            DefaultHttpClient client = new DefaultHttpClient();
            BasicResponseHandler handler = new BasicResponseHandler();
            String response = client.execute(post, handler);

            return  response;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


return  "Unable to contact server";
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        bitmap=(Bitmap)data.getExtras().get("data");
        image.setImageBitmap(bitmap);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,0);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_send) {
            mAuth.getInstance().signOut();
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




}


//    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
//    DatabaseReference ref = database.child("Users/"+currentUser);
//        ref.addValueEventListener(new ValueEventListener() {
//
//  public static final String TAG = "Tag";
//
//
//  @Override
//  public void onDataChange(DataSnapshot dataSnapshot) {
//
//
//        // for (DataSnapshot unit : dataSnapshot.getChildren()) {
//        // TODO: handle the post here
//        Person user = dataSnapshot.getValue(Person.class);
//        // profileName.setText(user.getUserName().toString());
//
//        mList.add(user);
//
//
//        //}
//
//
//        while (flag == 0) {
//        if (mList.get(i).getUserEmail().equals(intent.getStringExtra("userEmail"))) {
//        profilePhone.setText(mList.get(i).getUserPhone());
//        profileName.setText(mList.get(i).getUserName());
//        profileEmail.setText(mList.get(i).getUserEmail());
//
//        liveUser = new Person(mList.get(i).getUserEmail(), mList.get(i).getUserName(), mList.get(i).getUserPhone());
//
//        flag = 1;
//        }
//
//        i++;
//
//        }
//
//
//        HomeLayout.setVisibility(View.VISIBLE);
//        mapButton.setVisibility(View.VISIBLE);
//        buttonBar.setVisibility(View.VISIBLE);
//
//
//        }
//
//
//
//
//
//
//
//
//
//@Override
//public void onCancelled(DatabaseError databaseError) {
//        // Getting Post failed, log a message
//        profileEmail.setText(databaseError.toException().toString());
//        Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
//        // ...
//        }
//
//
//        });