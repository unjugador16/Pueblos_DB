package com.example.pueblosdb;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.*;
import com.example.pueblosdb.clases.Comunero;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity {

    private final FirebaseFirestore db  = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private TextView tv1, tv2, tv3;
    private String Email;
    Comunero user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();

        tv1 = findViewById(R.id.emailvisualizer);
        Email = mAuth.getCurrentUser().getEmail();
        tv1.setText(Email);
        tv2 = findViewById(R.id.namevisualizer);
        tv3 = findViewById(R.id.surnamevisualizer);

        DocumentReference docRef = db.collection("users").document(Email);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(Comunero.class);
                SharedPreferences.Editor prefsEditor = getSharedPreferences(getString(R.string.prefs_file), MODE_PRIVATE).edit();
                prefsEditor.putString("name", user.getNombre());
                prefsEditor.putString("surname", user.getApellidos());
                prefsEditor.apply();
            }
        });
    }

    public void viewData(View view) {
        //comunero.verInformacion(db, Email);
        SharedPreferences prefs = getSharedPreferences(getString(R.string.prefs_file), MODE_PRIVATE);
        tv2.setText(prefs.getString("name", "No hay datos"));
        tv3.setText(prefs.getString("surname", "No hay datos"));
    }

    public void logOut(View view) {
        SharedPreferences.Editor prefs = getSharedPreferences(getString(R.string.prefs_file), MODE_PRIVATE).edit();
        prefs.clear().apply();
        mAuth.signOut();
        Intent auth = new Intent(this, AuthActivity.class);
        startActivity(auth);
    }
}