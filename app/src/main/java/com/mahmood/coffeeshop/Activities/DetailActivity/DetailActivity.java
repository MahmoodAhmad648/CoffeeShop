package com.mahmood.coffeeshop.Activities.DetailActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mahmood.coffeeshop.Adaptors.sizeAdaptor;
import com.mahmood.coffeeshop.Model.CartModel;
import com.mahmood.coffeeshop.R;
import com.mahmood.coffeeshop.SizeSelectionListener;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    ImageView image_main;
    ImageView image_back;
    ImageView image_favourite;
    TextView titleText, priceText, descText, coffeeSize;
    String imageUrl;
    String key;
    RatingBar ratingBar;
    TextView increment, decrement, quantity;
    private int counter = 1;
    RecyclerView sizeList;
    Button addToCart;
    String selectedSize; // Variable to hold the selected size

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        image_main = findViewById(R.id.image_main);
        image_back = findViewById(R.id.image_back);
        image_favourite = findViewById(R.id.image_favourite);
        titleText = findViewById(R.id.titleText);
        priceText = findViewById(R.id.priceText);
        descText = findViewById(R.id.descText);
        coffeeSize = findViewById(R.id.coffeeSize);
        ratingBar = findViewById(R.id.ratingBar);
        increment = findViewById(R.id.increment);
        decrement = findViewById(R.id.decrement);
        quantity = findViewById(R.id.quantity);
        sizeList = findViewById(R.id.sizeList);
        addToCart = findViewById(R.id.addToCart);

        // Get Intent extras
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            titleText.setText(bundle.getString("title", "No Title"));
            priceText.setText(bundle.getString("price"));
            descText.setText(bundle.getString("description", "No description available"));
            key = bundle.getString("key");
            String rating = bundle.getString("rating");

            if (rating != null && !rating.isEmpty()) {
                try {
                    ratingBar.setRating(Float.parseFloat(rating));
                } catch (NumberFormatException e) {
                    ratingBar.setRating(0f);
                }
            } else {
                ratingBar.setRating(0f);
            }

            imageUrl = bundle.getString("mainImage");
            if (imageUrl != null && !imageUrl.isEmpty()) {
                Glide.with(this).load(imageUrl).into(image_main);
            } else {
                image_main.setImageResource(R.drawable.recycler_img);
            }
        }

        // Increment quantity
        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter++;
                quantity.setText(String.valueOf(counter));
            }
        });

        // Decrement quantity
        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counter > 1) {
                    counter--;
                    quantity.setText(String.valueOf(counter));
                }
            }
        });

        // Initialize size list
        initListSize();

        // Add to cart button click listener
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedSize != null) {
                    CartModel cartItem = new CartModel(titleText.getText().toString(), selectedSize, counter,
                            Float.parseFloat(priceText.getText().toString()), imageUrl);
                    addToCart(cartItem);
                } else {
                    // Handle the case where no size is selected (e.g., show a Toast message)
                    Toast.makeText(DetailActivity.this, "Please select a size", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initListSize() {
        ArrayList<String> sizeArray = new ArrayList<>();
        sizeArray.add("Small");
        sizeArray.add("Medium");
        sizeArray.add("Large");
        sizeArray.add("Extra Large");

        sizeAdaptor sizeAdaptor = new sizeAdaptor(sizeArray, new SizeSelectionListener() {
            @Override
            public void onSizeSelected(String size) {
                selectedSize = size; // Capture the selected size
                coffeeSize.setText("Selected Size: " + size); // Update displayed size
            }
        });

        sizeList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        sizeList.setAdapter(sizeAdaptor);
    }

    private void addToCart(CartModel item) {
        SharedPreferences sharedPreferences = getSharedPreferences("CoffeeShopPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Retrieve existing items
        String jsonItems = sharedPreferences.getString("cart_items", null);
        ArrayList<CartModel> cartItems;

        if (jsonItems != null) {
            Type type = new TypeToken<ArrayList<CartModel>>(){}.getType();
            cartItems = new Gson().fromJson(jsonItems, type);
        } else {
            cartItems = new ArrayList<>();
        }

        // Add the new item
        cartItems.add(item);

        // Save the updated list back to SharedPreferences
        String updatedJson = new Gson().toJson(cartItems);
        editor.putString("cart_items", updatedJson);
        editor.apply();

        Toast.makeText(this, "Item added to cart!", Toast.LENGTH_SHORT).show(); // Feedback to user
    }

}
