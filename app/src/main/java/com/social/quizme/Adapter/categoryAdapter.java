package com.social.quizme.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.social.quizme.R;
import com.social.quizme.model.categoryModel;
import com.social.quizme.model.profileModel;
import com.social.quizme.quizActivity;

import java.util.ArrayList;

public class categoryAdapter extends RecyclerView.Adapter<categoryAdapter.CategoryViewHolder> {

    Context context;
    ArrayList<categoryModel> catagoryModels;
    public categoryAdapter(Context context, ArrayList<categoryModel>catagoryModels){
        this.context=context;
        this.catagoryModels=catagoryModels;
    }




    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_catagory,null,false);

        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        //catagory model ky karaga jo jagaku a bindingviewholder palasithiba sia kn kariba sa object ra model ku catGORY MODEL RA SAVE KARIDABA
        categoryModel model=catagoryModels.get(position);

        holder.textView.setText(model.getCategoryName());
        Glide.with(context).load(model.getCategoryImage()).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context, quizActivity.class);
                intent.putExtra("catId",model.getCategoryId());
                context.startActivity(intent);
            }
        });


    }


    @Override
    public int getItemCount() {
        return catagoryModels.size();
    }

    public  class CategoryViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);
            textView=itemView.findViewById(R.id.catagory);
        }
    }
}




