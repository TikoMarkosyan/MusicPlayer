package com.example.musicplayer;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    private ArrayList<Example> examples;
    private OnItemClickListener mlistener;
     public interface OnItemClickListener{
         void  OnItemClick(int pos);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
         mlistener = listener;
    }
    public ExampleAdapter(ArrayList<Example> exampleArrayList){
        examples = exampleArrayList;
    }
    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.example_item,viewGroup,false);
        ExampleViewHolder exampleViewHolder = new ExampleViewHolder(v,mlistener);
        return exampleViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder exampleViewHolder, int i) {
        Example currentItem = examples.get(i);
        exampleViewHolder.imageView.setImageResource(currentItem.getmImageResurse());
        exampleViewHolder.text1.setText(currentItem.getMtext1());
        exampleViewHolder.text2.setText(currentItem.getMtext2());
    }

    @Override
    public int getItemCount() {
        return examples.size();
    }

    public static class ExampleViewHolder extends  RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView text1;
        private TextView text2;
        public ExampleViewHolder(@NonNull View itemView, final OnItemClickListener lisner) {
            super(itemView);
            imageView = itemView.findViewById(R.id.Imageview);
            text1 = itemView.findViewById(R.id.text1);
            text2 = itemView.findViewById(R.id.text2);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(lisner != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            lisner.OnItemClick(position);
                        }
                    }
                }
            });
        }
    }

}
