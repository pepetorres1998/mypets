package com.example.mypets2;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.mypets2.controllers.DogsController;
import com.example.mypets2.models.Dog;

import java.util.ArrayList;
import java.util.List;

public class DogsIndexActivity extends AppCompatActivity {
    private List<Dog> dogsList;
    private RecyclerView recyclerView;
    private DogAdapter dogsAdapter;
    private DogsController dogsController;
    private FloatingActionButton fabAddDog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dogs_index);

        dogsController = new DogsController(DogsIndexActivity.this);

        recyclerView = findViewById(R.id.recyclerViewPets);
        fabAddDog = findViewById(R.id.fabAddPet);

        dogsList = new ArrayList<>();
        dogsAdapter = new DogAdapter(dogsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(dogsAdapter);

        refreshPetsList();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),
                recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                // go to EditDogActivity.java
                Dog dogSelected = dogsList.get(position);
                Intent intent = new Intent(DogsIndexActivity.this, EditDogActivity.class);
                intent.putExtra("dogId", dogSelected.getId());
                intent.putExtra("dogName", dogSelected.getName());
                intent.putExtra("dogAge", dogSelected.getAge());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
                final Dog dogToDelete = dogsList.get(position);
                AlertDialog alertDialog = new AlertDialog
                        .Builder(DogsIndexActivity.this)
                        .setPositiveButton("Sí, eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dogsController.deleteDog(dogToDelete);
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
                        .setMessage("¿Eliminar a la mascota " + dogToDelete.getName() + "?")
                        .create();
                alertDialog.show();
            }
        }));

        // float button listener
        fabAddDog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // it just changes activity
                Intent intent = new Intent(DogsIndexActivity.this, AddDogActivity.class);
                startActivity(intent);
            }
        });

        fabAddDog.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(DogsIndexActivity.this)
                        .setTitle("Cambiar a Michitos")
                        .setMessage("¿Seguro que quiere cambiar a la lista de Michitos?")
                        .setPositiveButton("Sí, cambiar a michitos", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(
                                        DogsIndexActivity.this, CatsIndexActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create()
                        .show();
                return false;
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        refreshPetsList();
    }

    public void refreshPetsList(){
        if (dogsAdapter == null) return;
        dogsList = dogsController.getDogs();
        dogsAdapter.setPetsList(dogsList);
        dogsAdapter.notifyDataSetChanged();
    }
}
