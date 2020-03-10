package com.example.mypets2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mypets2.controllers.CatsController;
import com.example.mypets2.models.Cat;

public class EditCatActivity extends AppCompatActivity {
    private EditText etEditCatName, etEditCatAge;
    private Button btnSaveCatEdited, btnCancelCatEdited;
    private Cat cat; // the cat that will be edited
    private CatsController catsController;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cat);

        // get data sended
        Bundle extras = getIntent().getExtras();

        if (extras == null) {
            finish();
            return;
        }

        catsController = new CatsController(EditCatActivity.this);

        long catId = extras.getLong("catId");
        String catName = extras.getString("catName");
        int catAge = extras.getInt("catAge");
        cat = new Cat(catName, catAge, catId);

        etEditCatAge = findViewById(R.id.etEditCatAge);
        etEditCatName = findViewById(R.id.etEditCatName);
        btnCancelCatEdited = findViewById(R.id.btnCancelCatEdited);
        btnSaveCatEdited = findViewById(R.id.btnSaveCatEdited);

        etEditCatAge.setText(String.valueOf(cat.getAge()));
        etEditCatName.setText(cat.getName());

        btnCancelCatEdited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSaveCatEdited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // remove precious errors if existing
                etEditCatName.setError(null);
                etEditCatAge.setError(null);

                // Create dog with new changes but with same id
                String newName = etEditCatName.getText().toString();
                String possibleNewAge = etEditCatAge.getText().toString();
                if (newName.isEmpty()) {
                    etEditCatName.setError("Escribe el nombre");
                    etEditCatName.requestFocus();
                    return;
                }
                if (possibleNewAge.isEmpty()) {
                    etEditCatAge.setError("Escribe la edad");
                    etEditCatAge.requestFocus();
                    return;
                }
                // validate if is integer
                int newAge;
                try {
                    newAge = Integer.parseInt(possibleNewAge);
                } catch (NumberFormatException e) {
                    etEditCatAge.setError("Escribe un número");
                    etEditCatAge.requestFocus();
                    return;
                }

                // data already validated
                Cat catWithChanges = new Cat(newName, newAge, cat.getId());
                int modifiedColumns = catsController.updateCat(catWithChanges);
                if (modifiedColumns != 1) {
                    // De alguna forma ocurrió un error porque se debió modificar únicamente una fila
                    Toast.makeText(EditCatActivity.this,
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
