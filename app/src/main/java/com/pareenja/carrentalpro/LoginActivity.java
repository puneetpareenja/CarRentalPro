
package com.pareenja.carrentalpro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class LoginActivity
        extends AppCompatActivity
        implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    private static final String SHARED_PREFERENCES = "preferences";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";

    TextInputLayout emailTextInputLayout;
    TextInputLayout passwordTextInputLayout;
    MaterialButton loginButton;
    MaterialButton registerButton;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initLayout();

    }

    @Override
    protected void onResume() {
        super.onResume();

        emailTextInputLayout.getEditText().setText(sharedPreferences.getString(EMAIL, ""));
        passwordTextInputLayout.getEditText().setText(sharedPreferences.getString(PASSWORD, ""));
    }

    private void initLayout() {
        emailTextInputLayout = findViewById(R.id.text_input_email);
        passwordTextInputLayout = findViewById(R.id.text_input_password);
        loginButton = findViewById(R.id.material_button_login);
        registerButton = findViewById(R.id.material_button_to_register);

        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);

        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.material_button_login:
                if (isEmailValid() & isPasswordValid()) {
                    String email = Objects.requireNonNull(emailTextInputLayout.getEditText())
                            .getText().toString().toLowerCase().trim();
                    String password = Objects.requireNonNull(passwordTextInputLayout.getEditText())
                            .getText().toString().trim();

                    signIn(email, password);
                }
                break;
            case R.id.material_button_to_register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void signIn(final String email, String password) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        signInForUser(Objects.requireNonNull(FirebaseAuth.getInstance()
                                .getCurrentUser()).getUid());

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(EMAIL, email);
                        editor.putString(PASSWORD, password);
                        editor.apply();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof FirebaseAuthInvalidUserException) {
                            emailTextInputLayout.setError("User doesn't exists");
                        } else if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            emailTextInputLayout.setError("Invalid Username or Password");
                        }
                        Log.d(TAG, "onFailure: " + e);
                    }
                });
    }

    private void signInForUser(String uid) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference documentReference = db.collection("users").document(uid);

        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Person person = documentSnapshot.toObject(Person.class);

                Intent intent = new Intent();

                if (person != null) {
                    if (person.getPersonRole() == PersonRole.ADMINISTRATOR) {
                        intent = new Intent(LoginActivity.this, AdminViewActivity.class);
                    } else if (person.getPersonRole() == PersonRole.SALESPERSON) {
                        intent = new Intent(LoginActivity.this, SalesViewActivity.class);
                    } else {
                        intent = new Intent(LoginActivity.this, CustomerViewActivity.class);
                    }
                }

                startActivity(intent);
                finish();
            }
        });

    }

    private boolean isPasswordValid() {
        String password = Objects.requireNonNull(passwordTextInputLayout.getEditText()).getText().toString().trim();

        if (password.equals("")) {
            passwordTextInputLayout.setError("Required");
            return false;
        } else if (password.length() < 6) {
            passwordTextInputLayout.setError("Password must have at least 6 characters");
            return false;
        }

        passwordTextInputLayout.setError("");
        return true;
    }

    public boolean isEmailValid() {
        String email = Objects.requireNonNull(emailTextInputLayout.getEditText()).getText().toString().toLowerCase().trim();

        if (email.equals("")) {
            emailTextInputLayout.setError("Required");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailTextInputLayout.setError("Invalid Email");
            return false;
        }

        emailTextInputLayout.setError("");
        return true;
    }
}


