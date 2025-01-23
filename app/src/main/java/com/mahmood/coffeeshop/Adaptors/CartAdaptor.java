package com.mahmood.coffeeshop.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mahmood.coffeeshop.Model.CartModel;
import com.mahmood.coffeeshop.R;

import java.util.ArrayList;

public class CartAdaptor extends RecyclerView.Adapter<CartAdaptor.ViewHolder> {

    private Context context;
    private ArrayList<CartModel> arrCart;
    int counter = 1;

    public CartAdaptor(Context context, ArrayList<CartModel> arrCart) {
        this.context = context;
        this.arrCart = arrCart;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.cart_item_title.setText(arrCart.get(position).getCoffeeName());
        holder.cart_item_price.setText(String.valueOf(arrCart.get(position).getPrice()));
        holder.quantity_text.setText(String.valueOf(arrCart.get(position).getQuantity()));
        Glide.with(context).load(arrCart.get(position).getImageUrl()).into(holder.cart_item_image);

        holder.inc_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            counter++;
                holder.quantity_text.setText(String.valueOf(counter));
            }
        });

        holder.dec_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counter > 0){
                    counter--;

                }
                holder.quantity_text.setText(String.valueOf(counter));
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrCart.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView cart_item_image;
        TextView cart_item_title, cart_item_price, inc_text, quantity_text, dec_text;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cart_item_image = itemView.findViewById(R.id.cart_item_image);
            cart_item_title = itemView.findViewById(R.id.cart_item_title);
            cart_item_price = itemView.findViewById(R.id.cart_item_price);
            inc_text = itemView.findViewById(R.id.inc_text);
            quantity_text = itemView.findViewById(R.id.quantity_text);
            dec_text = itemView.findViewById(R.id.dec_text);
        }
    }
}
