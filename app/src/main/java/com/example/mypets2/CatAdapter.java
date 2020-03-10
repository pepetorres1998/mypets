package com.example.mypets2;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mypets2.models.Cat;
import com.example.mypets2.models.Pet;

import java.util.List;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.MyViewHolder> {

    private List<Cat> petsList;

    public void setPetsList(List<Cat> petsList){
        this.petsList = petsList;
    }

    public CatAdapter(List<Cat> pets){
        this.petsList = pets;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        View petRow = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.pet_row, viewGroup, false);

        return new MyViewHolder(petRow);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i){
        Pet pet = petsList.get(i);

        String petName = pet.getName();
        int petAge = pet.getAge();

        myViewHolder.name.setText(petName);
        myViewHolder.age.setText(String.valueOf(petAge));
    }

    @Override
    public int getItemCount() {
        return petsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, age;

        MyViewHolder(View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.tvName);
            this.age = itemView.findViewById(R.id.tvAge);
        }
    }
}
