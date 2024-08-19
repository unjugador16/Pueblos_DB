package com.example.pueblosdb.clases;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.pueblosdb.AuthActivity;
import com.example.pueblosdb.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public abstract class User {

    // attributes
    protected String nombre;
    protected String apellidos;
    protected SharedPreferences prefs;
    //fecha de nacimiento
    public User(){}

    public User(String nombre, String apellidos) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.prefs = prefs;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public abstract void verInformacion(FirebaseFirestore db, String email);
}