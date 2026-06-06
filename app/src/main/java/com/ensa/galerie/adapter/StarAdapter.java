package com.ensa.galerie.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ensa.galerie.R;
import com.ensa.galerie.model.entity.Star;
import com.ensa.galerie.service.StarService;

import java.util.ArrayList;
import java.util.List;

public class StarAdapter extends RecyclerView.Adapter<StarAdapter.StarViewHolder> implements Filterable {

    private List<Star> stars;
    private List<Star> starsFull;
    private Context context;

    public StarAdapter(List<Star> stars, Context context) {
        this.stars = stars;
        this.starsFull = new ArrayList<>(stars);
        this.context = context;
    }

    @NonNull
    @Override
    public StarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.star_item, parent, false);
        return new StarViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StarViewHolder holder, int position) {
        Star star = stars.get(position);
        holder.tv.setText(star.getName());
        holder.rb.setRating(star.getRating());
        Glide.with(context).load(star.getImg()).into(holder.iv);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog(star, holder.getAdapterPosition());
            }
        });
    }

    private void showEditDialog(Star star, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.edit_star, null);
        builder.setView(dialogView);

        final EditText editName = dialogView.findViewById(R.id.edit_name);
        final RatingBar editRating = dialogView.findViewById(R.id.edit_rating);

        editName.setText(star.getName());
        editRating.setRating(star.getRating());

        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                star.setName(editName.getText().toString());
                star.setRating(editRating.getRating());
                StarService.getInstance().update(star);
                notifyItemChanged(position);
                // Update starsFull to keep it in sync for filtering
                int fullIndex = starsFull.indexOf(star);
                if (fullIndex != -1) {
                    starsFull.get(fullIndex).setName(star.getName());
                    starsFull.get(fullIndex).setRating(star.getRating());
                }
            }
        });

        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return stars.size();
    }

    @Override
    public Filter getFilter() {
        return starFilter;
    }

    private Filter starFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Star> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(starsFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Star item : starsFull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            stars.clear();
            stars.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    class StarViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv;
        RatingBar rb;

        public StarViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            tv = itemView.findViewById(R.id.tv);
            rb = itemView.findViewById(R.id.rb);
        }
    }
}
