package com.example.android.movieapponetestone.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.android.movieapponetestone.R;
import com.example.android.movieapponetestone.db.TestPop;
import com.example.android.movieapponetestone.model.popular.Popular;
import com.example.android.movieapponetestone.view.DetailActivity;

import java.util.ArrayList;
import java.util.List;

public class ModelAdapter extends RecyclerView.Adapter<ModelAdapter.ViewHolder> {

    private static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185";

    private final ArrayList<Popular> modelList;

    private Context context;

    public ModelAdapter(ArrayList<Popular> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final Popular movies = modelList.get(position);
        Glide.with(context).load(IMAGE_BASE_URL + movies.getPosterPath()).into(holder.image);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailActivity.newIntent(context, movies, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.tv_image);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    final Popular movies = modelList.get(pos);
                    DetailActivity.newIntent(context, movies, pos);
                }
            });
        }
    }

    public void setData(List<Popular> list) {
        modelList.addAll(list);
        notifyDataSetChanged();
    }
}
