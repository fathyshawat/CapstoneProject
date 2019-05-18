package com.example.myapplication.Adpater;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myapplication.Activities.Details;
import com.example.myapplication.FavouriteDB.AppDatabase;
import com.example.myapplication.FavouriteDB.FavouriteEntity;
import com.example.myapplication.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavViewHolder> {

    Context context;
    List<FavouriteEntity> data;
    AppDatabase mDb;

    public FavouriteAdapter(Context context) {
        this.context = context;
        mDb = AppDatabase.getInstance(context);
    }

    public void setFav(List<FavouriteEntity> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal, parent, false);
        FavViewHolder vh = new FavViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull FavViewHolder holder, int position) {

        FavouriteEntity fav = data.get(position);
        holder.address.setText(fav.getAddress());
        holder.desc.setText(fav.getDesc());
        holder.restName.setText(fav.getResturantName());
        Picasso.get()
                .load(fav.getUrlImage())
                .fetch(new Callback() {
                    @Override
                    public void onSuccess() {
                        Picasso.get().load(fav.getUrlImage()).fit().into(holder.img, new Callback() {

                            @Override
                            public void onSuccess() {
                                holder.progress.setVisibility(View.INVISIBLE);
                                holder.img.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onError(Exception e) {
                                holder.img.setVisibility(View.VISIBLE);
                                holder.progress.setVisibility(View.INVISIBLE);
                            }


                        });
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });

        holder.mealType.setText(fav.getMeal());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Details.class);
                intent.putExtra("img", fav.getUrlImage());
                intent.putExtra("address", fav.getAddress());
                intent.putExtra("desc", fav.getDesc());
                intent.putExtra("meal", fav.getMeal());
                intent.putExtra("restu", fav.getResturantName());
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
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDb.taskDao().deleteFav(data.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        }
        return data.size();
    }

    public class FavViewHolder extends RecyclerView.ViewHolder {

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

        public FavViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
