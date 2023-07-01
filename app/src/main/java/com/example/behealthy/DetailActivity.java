package com.example.behealthy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DetailActivity extends AppCompatActivity {
    TextView detailTitle, detailDesc, detailTime;
    ImageView detailImage;
    FloatingActionButton detailDelete, detailUpdate;
    String key = "";
    String imageURL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailDesc = findViewById(R.id.detail_desc);
        detailTitle = findViewById(R.id.detail_title);
        detailImage = findViewById(R.id.detail_image);
        detailDelete = findViewById(R.id.detail_delete);
        detailTime = findViewById(R.id.detail_time);
        detailUpdate = findViewById(R.id.detail_update);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            detailTitle.setText(bundle.getString("title"));
            detailDesc.setText(bundle.getString("desc"));
            detailTime.setText(bundle.getString("time"));
            key = bundle.getString("key");
            imageURL = bundle.getString("image");
            Glide.with(this).load(bundle.getString("image")).into(detailImage);
        }

        detailDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Activities");
                FirebaseStorage storage = FirebaseStorage.getInstance();

                StorageReference storageReference = storage.getReferenceFromUrl(imageURL);
                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        reference.child(key).removeValue();
                        Toast.makeText(DetailActivity.this, "Actividad eliminada", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        finish();
                    }
                });
            }
        });

        detailUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, UpdateActivity.class)
                        .putExtra("title", detailTitle.getText().toString())
                        .putExtra("desc", detailDesc.getText().toString())
                        .putExtra("time", detailTime.getText().toString())
                        .putExtra("image", imageURL)
                        .putExtra("key", key);

                startActivity(intent);
            }
        });
    }
}