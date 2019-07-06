package com.pareenja.carrentalpro;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class LoginActivity
        extends AppCompatActivity
        implements View.OnClickListener {

    TextInputLayout emailTextInputLayout;
    TextInputLayout passwordTextInputLayout;
    MaterialButton loginButton;
    MaterialButton registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initLayout();

    }

    private void initLayout() {
        emailTextInputLayout = findViewById(R.id.text_input_email);
        passwordTextInputLayout = findViewById(R.id.text_input_password);
        loginButton = findViewById(R.id.material_button_login);
        registerButton = findViewById(R.id.material_button_register);

        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.material_button_login:
                if (isEmailValid() & isPasswordValid()) {

                }
                break;
            case R.id.material_button_register:
                break;
        }
    }

    private boolean isPasswordValid() {
        String password = passwordTextInputLayout.getEditText().getText().toString().trim();

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
