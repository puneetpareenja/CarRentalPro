package com.pareenja.carrentalpro;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Objects;

public class RegisterActivity
        extends AppCompatActivity
        implements View.OnClickListener {

    private static final String TAG = "RegisterActivity";

    ImageButton userImageButton;
    TextInputLayout nameTextInput;
    TextInputLayout emailTextInput;
    TextInputLayout phoneTextInput;
    TextInputLayout passwordInput;
    TextInputLayout confirmPasswordInput;
    MaterialButton registerButton;
    MaterialButton toLoginButton;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initLayout();
    }

    private void initLayout() {
        userImageButton = findViewById(R.id.image_button_user);
        nameTextInput = findViewById(R.id.text_input_name);
        emailTextInput = findViewById(R.id.text_input_email);
        phoneTextInput = findViewById(R.id.text_input_phone);
        passwordInput = findViewById(R.id.text_input_password);
        confirmPasswordInput = findViewById(R.id.text_input_confirm_password);
        registerButton = findViewById(R.id.material_button_register);
        toLoginButton = findViewById(R.id.material_button_to_login);

        userImageButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
        toLoginButton.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_button_user:
                break;
            case R.id.material_button_register:
                if (validateInformation()) {
                    String email = Objects.requireNonNull(emailTextInput.getEditText())
                            .getText().toString().trim().toLowerCase();
                    String password = Objects.requireNonNull(passwordInput.getEditText())
                            .getText().toString().trim();

                    createUser(email, password);
                }
                break;
            case R.id.material_button_to_login:
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
        }

    }

    private boolean validateInformation() {
        return isNameValid() & isEmailValid() & isPasswordValid() & doPasswordMatch() & isPhoneValid();
    }

    private boolean isPhoneValid() {
        String phone = Objects.requireNonNull(phoneTextInput.getEditText()).getText().toString().trim();
        if (phone.equals("")) {
            phoneTextInput.setError("Required");
            return false;
        } else if (phone.length() != 10) {
            phoneTextInput.setError("Invalid phone number");
            return false;
        }
        phoneTextInput.setError("");
        return true;
    }

    private boolean doPasswordMatch() {
        String password = Objects.requireNonNull(passwordInput.getEditText()).getText().toString().trim();
        String confirmPassword = Objects.requireNonNull(confirmPasswordInput.getEditText()).getText().toString().trim();

        if (confirmPassword.equals("")) {
            confirmPasswordInput.setError("Required");
            return false;
        } else if (!confirmPassword.equals(password)) {
            confirmPasswordInput.setError("Passwords do not match");
            return false;
        }
        confirmPasswordInput.setError("");
        return true;
    }

    private boolean isPasswordValid() {
        String password = Objects.requireNonNull(passwordInput.getEditText()).getText().toString().trim();
        if (password.equals("")) {
            passwordInput.setError("Required");
            return false;
        } else if (password.length() < 6) {
            passwordInput.setError("Password must have at least 6 characters");
            return false;
        }

        passwordInput.setError("");
        return true;
    }

    private boolean isEmailValid() {
        String email = Objects.requireNonNull(emailTextInput.getEditText()).getText().toString().trim();
        if (email.equals("")) {
            emailTextInput.setError("Required");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailTextInput.setError("Invalid Email");
            return false;
        }
        emailTextInput.setError("");
        return true;
    }

    private boolean isNameValid() {
        String name = Objects.requireNonNull(nameTextInput.getEditText()).getText().toString().trim();
        if (name.equals("")) {
            nameTextInput.setError("Required");
            return false;
        }
        nameTextInput.setError("");
        return true;
    }

    public void createUser(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            if (user != null) {
                                String userName = Objects.requireNonNull(nameTextInput.getEditText())
                                        .getText().toString().trim();
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(userName)
//                                    .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                                        .build();

                                user.updateProfile(profileUpdates);
                            }


                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(RegisterActivity.this,
                                        "User Already Exists",
                                        Toast.LENGTH_SHORT)
                                        .show();
                            } else {

                                Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}
