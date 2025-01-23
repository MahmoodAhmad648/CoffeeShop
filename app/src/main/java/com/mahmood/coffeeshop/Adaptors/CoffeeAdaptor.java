package com.mahmood.coffeeshop.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mahmood.coffeeshop.Activities.DetailActivity.DetailActivity;
import com.mahmood.coffeeshop.Model.CoffeeModel;
import com.mahmood.coffeeshop.R;

import java.util.ArrayList;

public class CoffeeAdaptor extends RecyclerView.Adapter<MyViewHolder> {

    private Context context;
    private ArrayList<CoffeeModel> coffeeModels;

    public CoffeeAdaptor(Context context, ArrayList<CoffeeModel> coffeeModels) {
        this.context = context;
        this.coffeeModels = coffeeModels;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_recycler_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.coffee_name.setText(coffeeModels.get(position).getTitle());
        holder.coffee_price.setText(String.format("$%s", coffeeModels.get(position).getPrice()));
        holder.coffee_description.setText(coffeeModels.get(position).getDescription());
        if (!coffeeModels.get(position).getPicUrl().isEmpty()) {
            Glide.with(context).load(coffeeModels.get(position).getPicUrl().get(0)).into(holder.home_recycler_img);
        } else {
            holder.home_recycler_img.setImageResource(R.drawable.recycler_img); // Optional: Set a placeholder if no URL is found
        }

        holder.home_recycler_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("mainImage", coffeeModels.get(holder.getAdapterPosition()).getPicUrl().get(0));
                intent.putExtra("title", coffeeModels.get(holder.getAdapterPosition()).getTitle());
                intent.putExtra("price", String.valueOf(coffeeModels.get(holder.getAdapterPosition()).getPrice()));
                intent.putExtra("description", coffeeModels.get(holder.getAdapterPosition()).getDescription());
                intent.putExtra("key", coffeeModels.get(holder.getAdapterPosition()).getKey());
                intent.putExtra("rating", String.valueOf(coffeeModels.get(holder.getAdapterPosition()).getRating()));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return coffeeModels.size();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder {

    CardView home_recycler_card;
    ImageView home_recycler_img;
    TextView coffee_name, coffee_price, coffee_description;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        home_recycler_card = itemView.findViewById(R.id.home_recycler_card);
        home_recycler_img = itemView.findViewById(R.id.home_recycler_img);
        coffee_name = itemView.findViewById(R.id.coffee_name);
        coffee_price = itemView.findViewById(R.id.coffee_price);
        coffee_description = itemView.findViewById(R.id.coffee_description);

    }
}