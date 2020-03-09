package com.example.mypets2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mypets2.controllers.DogsController;
import com.example.mypets2.models.Dog;

public class AddDogActivity extends AppCompatActivity {
    private Button btnAddDog, btnCancelNewDog;
    private EditText etName, etAge;
    private DogsController dogsController;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dog);

        // instance views
        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        btnAddDog = findViewById(R.id.btnAddDog);
        btnCancelNewDog = findViewById(R.id.btnCancelNewDog);

        // create controller
        dogsController = new DogsController(AddDogActivity.this);

        // add listener of save button
        btnAddDog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // reset error on both fields
                etName.setError(null);
                etAge.setError(null);
                String name = etName.getText().toString(),
                        ageAsString = etAge.getText().toString();

                if ("".equals(name)){
                    etName.setError("Escribe el nombre de la mascota");
                    etName.requestFocus();
                    return;
                }
                if ("".equals(ageAsString)) {
                    etAge.setError("Escribe la edad de la mascota");
                    etAge.requestFocus();
                    return;
                }

                // add age integer validation
                // Ver si es un entero
                int age;
                try {
                    age = Integer.parseInt(etAge.getText().toString());
                } catch (NumberFormatException e) {
                    etAge.setError("Escribe un número");
                    etAge.requestFocus();
                    return;
                }

                // after all validations passed
                Dog newDog = new Dog(name, age);
                long id = dogsController.newDog(newDog);
                if (id == -1) {
                    // an error occurred
                    Toast.makeText(AddDogActivity.this,
                            "Error al guardar. Intenta de nuevo",
                            Toast.LENGTH_SHORT
                    ).show();
                } else {
                    finish();
                }

            }
        });

        // cancel button just finishes activity
        btnCancelNewDog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
