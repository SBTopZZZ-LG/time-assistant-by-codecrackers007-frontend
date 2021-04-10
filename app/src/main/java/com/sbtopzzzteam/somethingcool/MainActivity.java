package com.sbtopzzzteam.somethingcool;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.sbtopzzzteam.somethingcool.REST.Methods;

public class MainActivity extends AppCompatActivity {
    Button btnSignIn, btnRegister;

    EditText etEmailAddress;

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Initialize();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmailAddress.getText().toString();
                if (email.contains("@") && email.contains(".com")) {
                    if (email.split("@")[0].length() > 0 && email.split("@")[1].replace(".com", "").length() > 0) {
                        View main = View.inflate(MainActivity.this, R.layout.signin_email_verification_dialog, null);

                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setView(main);
                        builder.setCancelable(true);

                        AlertDialog dialog = builder.show();

                        Methods.createUserLoginRequest(MainActivity.this, email, new Methods.UserLoginRequest() {
                            @Override
                            public void onSuccess(String userId) {
                                createUserLoginStatusRequest(email, userId, dialog);
                            }

                            @Override
                            public void onFailure(String error) {
                                dialog.dismiss();
                                Snackbar.make(btnSignIn, "Login Error: " + error, Snackbar.LENGTH_LONG).show();
                            }
                        });
                    }
                }

                //startActivity(new Intent(MainActivity.this, Register2Activity.class));
            }
        });
    }

    private void putInSP(String key, String val) {
        editor.putString(key, val);
        editor.apply();
    }

    private void createUserLoginStatusRequest(String email, String userId, AlertDialog dialog) {
        if (dialog.isShowing())
            Methods.createUserLoginStatusRequest(email, new Methods.UserLoginStatusRequest() {
                @Override
                public void onSuccess(double verified) {
                    if (verified == 1) {
                        dialog.dismiss();
                        putInSP("user_id", userId);

                        Methods.createUserLoginAuthTokenRequest(MainActivity.this, email, new Methods.UserLoginAuthTokenRequest() {
                            @Override
                            public void onSuccess(String authToken) {
                                putInSP("user_token", authToken);

                                Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(MainActivity.this, Register2Activity.class)
                                .putExtra("emailAddress", email));
                            }

                            @Override
                            public void onFailure(String error) {
                                Toast.makeText(MainActivity.this, "Login Failed due to unknown error", Toast.LENGTH_SHORT).show();
                                Toast.makeText(MainActivity.this, "Error: "+error, Toast.LENGTH_LONG).show();
                            }
                        });
                    } else
                        createUserLoginStatusRequest(email, userId, dialog);
                }

                @Override
                public void onFailure(String error) {
                    createUserLoginStatusRequest(email, userId, dialog);
                }
            });
    }

    @SuppressLint("CommitPrefEdits")
    private boolean Initialize() {
        btnSignIn = findViewById(R.id.btnSignIn);
        btnRegister = findViewById(R.id.btnRegister);

        etEmailAddress = ((TextInputLayout) findViewById(R.id.etEmailAddress)).getEditText();

        sharedpreferences = getSharedPreferences("root", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        return true;
    }
}