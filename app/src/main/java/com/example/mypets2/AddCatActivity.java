package com.example.mypets2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mypets2.controllers.CatsController;
import com.example.mypets2.models.Cat;

public class AddCatActivity extends AppCompatActivity {
    private Button btnAddCat, btnCancelNewCat;
    private EditText etCatName, etCatAge;
    private CatsController catsController;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cat);

        etCatName = findViewById(R.id.etCatName);
        etCatAge = findViewById(R.id.etCatAge);
        btnAddCat = findViewById(R.id.btnAddCat);
        btnCancelNewCat = findViewById(R.id.btnCancelNewCat);

        catsController = new CatsController(AddCatActivity.this);

        btnAddCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // reset error on both fields
                etCatName.setError(null);
                etCatAge.setError(null);
                String name = etCatName.getText().toString(),
                        ageAsString = etCatAge.getText().toString();

                if ("".equals(name)){
                    etCatName.setError("Escribe el nombre de la mascota");
                    etCatName.requestFocus();
                    return;
                }
                if ("".equals(ageAsString)) {
                    etCatAge.setError("Escribe la edad de la mascota");
                    etCatAge.requestFocus();
                    return;
                }

                // add age integer validation
                // Ver si es un entero
                int age;
                try {
                    age = Integer.parseInt(etCatAge.getText().toString());
                } catch (NumberFormatException e) {
                    etCatAge.setError("Escribe un número");
                    etCatAge.requestFocus();
                    return;
                }

                // after all validations passed
                Cat newCat = new Cat(name, age);
                long id = catsController.newCat(newCat);
                if (id == -1) {
                    // an error occurred
                    Toast.makeText(AddCatActivity.this,
                            "Error al guardar. Intenta de nuevo",
                            Toast.LENGTH_SHORT
                    ).show();
                } else {
                    finish();
                }
            }
        });

        btnCancelNewCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
