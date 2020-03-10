package com.example.mypets2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mypets2.controllers.UsersController;
import com.example.mypets2.models.User;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin, btnLoginRegister, btnCancelLogin;
    private EditText etEmail, etPassword;
    private UsersController usersController;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        btnLoginRegister = findViewById(R.id.btnLoginRegister);
        btnCancelLogin = findViewById(R.id.btnCancelLogin);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        usersController = new UsersController(LoginActivity.this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etEmail.setError(null);
                etPassword.setError(null);

                String email = etEmail.getText().toString(),
                        password = etPassword.getText().toString();

                if ("".equals(email)) {
                    etEmail.setError("Escribe el email");
                    etEmail.requestFocus();
                    return;
                }
                if ("".equals(password)) {
                    etPassword.setError("Escribe la contraseña");
                    etPassword.requestFocus();
                    return;
                }

                User dataBaseUser = usersController.getUser(email);

                if (dataBaseUser.getEmail() == null) {
                    etEmail.setError("Email no existe");
                    etEmail.requestFocus();
                    return;
                }

                if (dataBaseUser.getPassword().equals(password)) {
                    Intent intent = new Intent(LoginActivity.this, DogsIndexActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this,
                            "Contraseña incorrecta", Toast.LENGTH_LONG
                    ).show();
                }
            }
        });

        btnLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnCancelLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
