package com.sbtopzzzteam.somethingcool;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.sbtopzzzteam.somethingcool.REST.Methods;

public class RegisterActivity extends AppCompatActivity {

    EditText etEmailAddress, etFullName, etPhoneNumber;

    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Initialize();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmailAddress.getText().toString();
                if (email.contains("@") && email.contains(".com")) {
                    if (email.split("@")[0].length() > 0 && email.split("@")[1].replace(".com", "").length() > 0) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        builder.setMessage("Please wait...");
                        builder.setCancelable(false);

                        AlertDialog dialog = builder.show();

                        Methods.createUserRegisterRequest(RegisterActivity.this, email, etFullName.getText().toString(), etPhoneNumber.getText().toString(),
                                new Methods.UserRegisterRequest() {
                                    @Override
                                    public void onSuccess() {
                                        dialog.dismiss();
                                        Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }

                                    @Override
                                    public void onFailure(String error) {
                                        dialog.dismiss();
                                        Snackbar.make(btnRegister, "Error: "+error, Snackbar.LENGTH_LONG).show();
                                    }
                                });
                    }
                }
            }
        });
    }

    private boolean Initialize() {
        etEmailAddress = ((TextInputLayout) findViewById(R.id.etEmailAddress)).getEditText();
        etFullName = ((TextInputLayout) findViewById(R.id.etFullName)).getEditText();
        etPhoneNumber = ((TextInputLayout) findViewById(R.id.etPhoneNumber)).getEditText();

        btnRegister = findViewById(R.id.btnRegister);

        return true;
    }
}