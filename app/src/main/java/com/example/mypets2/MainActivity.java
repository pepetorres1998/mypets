package com.example.mypets2;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.mypets2.controllers.DogsController;
import com.example.mypets2.models.Dog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Dog> dogsList;
    private RecyclerView recyclerView;
    private DogAdapter dogsAdapter;
    private DogsController dogsController;
    private FloatingActionButton fabAddDog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dogsController = new DogsController(MainActivity.this);

        recyclerView = findViewById(R.id.recyclerViewPets);
        fabAddDog = findViewById(R.id.fabAddPet);

        dogsList = new ArrayList<>();
        dogsAdapter = new DogAdapter(dogsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(dogsAdapter);

        refreshPetsList();
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
