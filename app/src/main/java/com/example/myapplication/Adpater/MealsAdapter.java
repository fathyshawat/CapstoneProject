package com.example.myapplication.Adpater;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myapplication.Activities.Details;
import com.example.myapplication.R;
import com.example.myapplication.model.MealModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.ViewHolder> {

    Context context;
    List<MealModel> data;
    DatabaseReference mRef;

    public MealsAdapter(Context context, List<MealModel> data) {
        this.context = context;
        this.data = data;
        mRef= FirebaseDatabase.getInstance().getReference("meals");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.delete.setVisibility(View.GONE);
        holder.restName.setText(data.get(position).getRestaurant());
        holder.address.setText(data.get(position).getAddress());
        holder.desc.setText(data.get(position).getDescription());
        holder.mealType.setText(data.get(position).getMeal());

        Picasso.get()
                .load(data.get(position).getImage())
                .fetch(new Callback() {
                    @Override
                    public void onSuccess() {
                        Picasso.get().load(data.get(position).getImage()).fit().into(holder.img, new Callback() {

                            @Override
                            public void onSuccess() {
                                holder.progress.setVisibility(View.INVISIBLE);
                                holder.img.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onError(Exception e) {
                                holder.img.setVisibility(View.VISIBLE);
                                holder.progress.setVisibility(View.INVISIBLE);
                                Log.e("adapter", e.getMessage());
                            }


                        });
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id= mRef.getKey();
                Intent intent = new Intent(context, Details.class);
                intent.putExtra("img", data.get(position).getImage());
                intent.putExtra("address", data.get(position).getAddress());
                intent.putExtra("desc", data.get(position).getDescription());
                intent.putExtra("meal", data.get(position).getMeal());
                intent.putExtra("restu", data.get(position).getRestaurant());
                intent.putExtra("id", id);


                Pair[] m = new Pair[1];
                m[0] = new Pair<View, String>(holder.img, context.getString(R.string.img));
                ActivityOptions options = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    options = ActivityOptions.makeSceneTransitionAnimation((Activity) context, m);
                    context.startActivity(intent, options.toBundle());
                } else
                    context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item)
        CardView cardView;
        @BindView(R.id.img1)
        ImageView img;
        @BindView(R.id.delete)
        ImageView delete;
        @BindView(R.id.rest_name)
        TextView restName;
        @BindView(R.id.meal_type)
        TextView mealType;
        @BindView(R.id.address)
        TextView address;
        @BindView(R.id.desc)
        TextView desc;
        @BindView(R.id.p_img)
        ProgressBar progress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
