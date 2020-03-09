package com.example.mypets2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mypets2.controllers.DogsController;
import com.example.mypets2.models.Dog;

public class EditDogActivity extends AppCompatActivity {
    private EditText etEditDogName, etEditDogAge;
    private Button btnSaveDogEdited, btnCancelDogEdited;
    private Dog dog; // the dog that will be edited
    private DogsController dogsController;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dog);

        // get data sended
        Bundle extras = getIntent().getExtras();

        // if there is not data we get out (rare to happen)
        if (extras == null) {
            finish();
            return;
        }

        // instance dog controller
        dogsController = new DogsController(EditDogActivity.this);

        // reconstruct dog
        long dogId = extras.getLong("dogId");
        String dogName = extras.getString("dogName");
        int dogAge = extras.getInt("dogAge");
        dog = new Dog(dogName, dogAge, dogId);

        // views declaration
        etEditDogAge = findViewById(R.id.etEditDogAge);
        etEditDogName = findViewById(R.id.etEditDogName);
        btnCancelDogEdited = findViewById(R.id.btnCancelDogEdited);
        btnSaveDogEdited = findViewById(R.id.btnSaveDogEdited);

        // fill edit text with dog data
        etEditDogAge.setText(String.valueOf(dog.getAge()));
        etEditDogName.setText(dog.getName());

        // Listener del click del botón para salir, simplemente cierra la actividad
        btnCancelDogEdited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSaveDogEdited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // remove precious errors if existing
                etEditDogName.setError(null);
                etEditDogAge.setError(null);

                // Create dog with new changes but with same id
                String newName = etEditDogName.getText().toString();
                String possibleNewAge = etEditDogAge.getText().toString();
                if (newName.isEmpty()) {
                    etEditDogName.setError("Escribe el nombre");
                    etEditDogName.requestFocus();
                    return;
                }
                if (possibleNewAge.isEmpty()) {
                    etEditDogAge.setError("Escribe la edad");
                    etEditDogAge.requestFocus();
                    return;
                }
                // validate if is integer
                int newAge;
                try {
                    newAge = Integer.parseInt(possibleNewAge);
                } catch (NumberFormatException e) {
                    etEditDogAge.setError("Escribe un número");
                    etEditDogAge.requestFocus();
                    return;
                }

                // data already validated
                Dog dogWithChanges = new Dog(newName, newAge, dog.getId());
                int modifiedColumns = dogsController.updateDog(dogWithChanges);
                if (modifiedColumns != 1) {
                    // De alguna forma ocurrió un error porque se debió modificar únicamente una fila
                    Toast.makeText(EditDogActivity.this,
                            "Error guardando cambios. Intente de nuevo.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    // If all went well we close this activity returning to principal activity
                    finish();
                }
            }
        });
    }
}
