package com.example.behealthy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

public class UserUpdateActivity extends AppCompatActivity {


    Button updateProfileButton;
    EditText updateNom, updateCon, updateUsu, updateCorreo, nombre;
    String nom, clave, usu, correo;
    DatabaseReference databaseReference;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update);

        updateNom = (EditText) findViewById(R.id.update_name);
        updateCon = (EditText) findViewById(R.id.update_password);
        updateCorreo = (EditText) findViewById(R.id.update_email);
        nombre = (EditText) findViewById(R.id.edtNom);
        updateProfileButton = (Button) findViewById(R.id.updateUser_Button);
    }

    public void checkIfUserExists(View view) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("users");

        Query query = databaseRef.orderByChild("username").equalTo(nombre.getText().toString().trim());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // El registro con el nombre dado existe en la base de datos.
                    Toast.makeText(UserUpdateActivity.this, "Usuario Encontrado", Toast.LENGTH_LONG).show();

                } else {
                    // El registro con el nombre dado no existe en la base de datos.
                    Toast.makeText(UserUpdateActivity.this, "Usuario No Encontrado", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Ocurrió un error al leer la base de datos.
                // Puedes manejar el error aquí.
            }
        });
    }

   public void updatePefil(View view) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("users");

        Query query = databaseRef.orderByChild("username").equalTo(nombre.getText().toString().trim());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    dataSnapshot.getRef().child("name").setValue(updateNom.getText().toString().trim());
                    dataSnapshot.getRef().child("password").setValue(updateCon.getText().toString().trim());
                    dataSnapshot.getRef().child("email").setValue(updateCorreo.getText().toString().trim());

                    Toast.makeText(UserUpdateActivity.this, "Usuario Actualizado", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(UserUpdateActivity.this, "Usuario No Existe", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Ocurrió un error al leer la base de datos.
                Toast.makeText(UserUpdateActivity.this, "Error al acceder a la base de datos", Toast.LENGTH_LONG).show();
            }
        });
    }




}