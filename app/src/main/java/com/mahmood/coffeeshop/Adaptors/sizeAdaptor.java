package com.mahmood.coffeeshop.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.mahmood.coffeeshop.R;
import com.mahmood.coffeeshop.SizeSelectionListener;

import java.util.ArrayList;

public class sizeAdaptor extends RecyclerView.Adapter<sizeAdaptor.ViewHolder> {


    private int selectedPosition = -1;
    private int lastSelectedPosition = -1;

    private Context context;
    ArrayList<String> sizeArray;
    private SizeSelectionListener listener;

    public sizeAdaptor(ArrayList<String> sizeArray, SizeSelectionListener listener) {
        this.sizeArray = sizeArray;
        this.listener = listener;
    }

    @NonNull
    @Override
    public sizeAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.view_holder_size, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull sizeAdaptor.ViewHolder holder, int position) {

        holder.sizeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lastSelectedPosition = selectedPosition;
                selectedPosition = holder.getAdapterPosition();
                notifyItemChanged(lastSelectedPosition);
                notifyItemChanged(selectedPosition);
                listener.onSizeSelected(sizeArray.get(selectedPosition));
            }
        });
        if (selectedPosition == position) {
            holder.sizeImage.setColorFilter(context.getResources().getColor(R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);
        } else {
            holder.sizeImage.setColorFilter(context.getResources().getColor(R.color.background), android.graphics.PorterDuff.Mode.SRC_IN);
        }




        int imageSize;  // Declare imageSize variable

        switch (position) {
            case 0:
                // Perform actions specific to the "Small" size
                imageSize = dpToPx(45, context);
                holder.sizeImage.getLayoutParams().width = imageSize;
                break;

            case 1:
                // Perform actions specific to the "Medium" size
                imageSize = dpToPx(50, context);
                holder.sizeImage.getLayoutParams().width = imageSize;
                break;

            case 2:
                // Perform actions specific to the "Large" size
                imageSize = dpToPx(55, context);
                holder.sizeImage.getLayoutParams().width = imageSize;
                break;

            case 3:
                // Perform actions specific to the "Extra Large" size
                imageSize = dpToPx(60, context);
                holder.sizeImage.getLayoutParams().width = imageSize;
                break;

            default:
                // Default case if none match
                imageSize = dpToPx(45, context);  // Set a default size
                holder.sizeImage.getLayoutParams().width = imageSize;
                break;
        }

        ViewGroup.LayoutParams params = holder.sizeImage.getLayoutParams();
        params.width = imageSize;
        params.height = imageSize;  // Set both width and height
        holder.sizeImage.setLayoutParams(params);


    }

    public int dpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    @Override
    public int getItemCount() {
        return sizeArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout sizeLayout;
        ImageView sizeImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sizeImage = itemView.findViewById(R.id.sizeImage);
            sizeLayout = itemView.findViewById(R.id.sizeLayout);
        }
    }
}
