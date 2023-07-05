package com.example.behealthy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

public class ProfileActivity extends AppCompatActivity {


    Button updateProfileButton;
    EditText updateNom, updateCon, updateCorreo, usuario;

    String key;


    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        drawerLayout=findViewById(R.id.drawerlayout);
        menu=findViewById(R.id.menu);
        home=findViewById(R.id.principal);
        profile=findViewById(R.id.profile);

        updateNom = (EditText) findViewById(R.id.update_name);
        updateCon = (EditText) findViewById(R.id.update_password);
        updateCorreo = (EditText) findViewById(R.id.update_email);
        usuario = (EditText) findViewById(R.id.edtNom);
        updateProfileButton = (Button) findViewById(R.id.updateUser_Button);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            Glide.with(ProfileActivity.this);
            updateNom.setText(bundle.getString("name"));
            updateCon.setText(bundle.getString("password"));
            updateCorreo.setText(bundle.getString("email"));
            key = bundle.getString(usuario.getText().toString().trim());
        }




        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer(drawerLayout);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(ProfileActivity.this, HomeActivity.class);
            }
        });

    }
    public static void  openDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public static void closeDrawer(DrawerLayout drawerLayout){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    public static void redirectActivity(Activity activity, Class secondActivity){
        Intent intent=new Intent(activity, secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }

    public void checkIfUserExists(View view) {
        String username  =  usuario.getText().toString().trim();
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users");
        Query query = databaseRef.orderByChild("username").equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(ProfileActivity.this, "Usuario Encontrado", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(ProfileActivity.this, "Usuario No Encontrado", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }



    public void updateUser(View view) {
        String username  =  usuario.getText().toString().trim();
        String name   = updateNom.getText().toString().trim();
        String password= updateCon.getText().toString().trim();
        String email = updateCorreo.getText().toString().trim() ;
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users").child(username);

        HelperClass helperClass = new HelperClass(name, email, username,password);

        databaseRef.setValue(helperClass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){

                    Toast.makeText(ProfileActivity.this, "Datos Actualizados", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull  Exception e) {
                Toast.makeText(ProfileActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}