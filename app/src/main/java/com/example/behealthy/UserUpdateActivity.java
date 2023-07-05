package com.example.behealthy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class UserUpdateActivity extends AppCompatActivity {


    Button updateProfileButton;
    EditText updateNom, updateCon, updateCorreo, usuario;

    String key;

    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update);

        updateNom = (EditText) findViewById(R.id.update_name);
        updateCon = (EditText) findViewById(R.id.update_password);
        updateCorreo = (EditText) findViewById(R.id.update_email);
        usuario = (EditText) findViewById(R.id.edtNom);
        updateProfileButton = (Button) findViewById(R.id.updateUser_Button);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            Glide.with(UserUpdateActivity.this);
            updateNom.setText(bundle.getString("name"));
            updateCon.setText(bundle.getString("password"));
            updateCorreo.setText(bundle.getString("email"));
            key = bundle.getString("key");
        }

        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users").child("key");

    }



    public void checkIfUserExists(View view) {
        String username  =  usuario.getText().toString().trim();
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users");
        Query query = databaseRef.orderByChild("username").equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(UserUpdateActivity.this, "Usuario Encontrado", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(UserUpdateActivity.this, "Usuario No Encontrado", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }



   public void updatePerfil(View view) {
       String username  =  usuario.getText().toString().trim();
       String name   = updateNom.getText().toString().trim();
       String password= updateCon.getText().toString().trim();
       String email = updateCorreo.getText().toString().trim() ;
       DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users").child("key");


       storageReference = FirebaseStorage.getInstance().getReference().child("users").child(key);

                   HelperClass helperClass = new HelperClass(name, email, username, password);

                   databaseRef.setValue(helperClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {
                           if (task.isSuccessful()) {
                               Toast.makeText(UserUpdateActivity.this, "Datos Actualizados", Toast.LENGTH_SHORT).show();
                               finish();
                           }
                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           Toast.makeText(UserUpdateActivity.this, "No se actualizaron datos", Toast.LENGTH_SHORT).show();
                       }
                   });

               }


    public void updateData(View view) {
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

                    Toast.makeText(UserUpdateActivity.this, "Datos Actualizados", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull  Exception e) {
                Toast.makeText(UserUpdateActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
};

