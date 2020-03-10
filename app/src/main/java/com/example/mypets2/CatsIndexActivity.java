package com.example.mypets2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.mypets2.controllers.CatsController;
import com.example.mypets2.models.Cat;

import java.util.ArrayList;
import java.util.List;

public class CatsIndexActivity extends AppCompatActivity {
    private List<Cat> catsList;
    private RecyclerView recyclerView;
    private CatAdapter catsAdapter;
    private CatsController catsController;
    private FloatingActionButton fabAddCat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cats_index);

        catsController = new CatsController(CatsIndexActivity.this);

        recyclerView = findViewById(R.id.recyclerViewCats);
        fabAddCat = findViewById(R.id.fabAddCat);

        catsList = new ArrayList<>();
        catsAdapter = new CatAdapter(catsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(catsAdapter);

        refreshPetsList();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),
                recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                final Cat catToDelete = catsList.get(position);
                AlertDialog alertDialog = new AlertDialog
                        .Builder(CatsIndexActivity.this)
                        .setPositiveButton("Sí, eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                catsController.deleteCat(catToDelete);
                                refreshPetsList();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setTitle("Confirmar")
                        .setMessage("¿Eliminar a la mascota " + catToDelete.getName() + "?")
                        .create();
                alertDialog.show();
            }
        }));

        // float button listener
        fabAddCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // it just changes activity
                Intent intent = new Intent(CatsIndexActivity.this, AddCatActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        refreshPetsList();
    }

    public void refreshPetsList(){
        if (catsAdapter == null) return;
        catsList = catsController.getCats();
        catsAdapter.setPetsList(catsList);
        catsAdapter.notifyDataSetChanged();
    }
}
