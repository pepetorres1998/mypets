package com.example.mypets2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mypets2.controllers.UsersController;
import com.example.mypets2.models.User;

public class RegisterActivity extends AppCompatActivity {
    private Button btnRegister, btnCancelRegister;
    private EditText etRegisterEmail, etRegisterPassword;
    private UsersController usersController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // instance views components
        btnRegister = findViewById(R.id.btnRegister);
        btnCancelRegister = findViewById(R.id.btnCancelRegister);
        etRegisterEmail = findViewById(R.id.etRegisterEmail);
        etRegisterPassword = findViewById(R.id.etRegisterPassword);

        usersController = new UsersController(RegisterActivity.this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // reset error on both fields
                etRegisterEmail.setError(null);
                etRegisterPassword.setError(null);

                String email = etRegisterEmail.getText().toString(),
                        password = etRegisterPassword.getText().toString();

                if ("".equals(email)) {
                    etRegisterEmail.setError("Escribe el email");
                    etRegisterEmail.requestFocus();
                    return;
                }
                if ("".equals(password)) {
                    etRegisterPassword.setError("Escribe la contraseña");
                    etRegisterPassword.requestFocus();
                    return;
                }

                User newUser = new User(email, password);
                long id = usersController.registerUser(newUser);
                if (id == -1) {
                    // an error occurred
                    Toast.makeText(RegisterActivity.this,
                            "Error al guardar. Intenta de nuevo",
                            Toast.LENGTH_SHORT
                    ).show();
                } else {
                    finish();
                }
            }
        });

        btnCancelRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
