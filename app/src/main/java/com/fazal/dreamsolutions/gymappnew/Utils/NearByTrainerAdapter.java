package com.fazal.dreamsolutions.gymappnew.Utils;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.fazal.dreamsolutions.gymappnew.FragmentsTrainer.TrainerDetailsFragment;
import com.fazal.dreamsolutions.gymappnew.Objects.ModelNearByTrainers;
import com.fazal.dreamsolutions.gymappnew.R;
import com.squareup.picasso.Picasso;


public class NearByTrainerAdapter extends RecyclerView.Adapter<NearByTrainerAdapter.RecyclerViewHolder> {
    String[] FName, LName, Dollers, Kilometer;
    private Context context;
    ModelNearByTrainers[] modelNearByTrainers;

    public NearByTrainerAdapter(Context context, ModelNearByTrainers[] modelNearByTrainers) {
        // this.Image=Image;
        this.modelNearByTrainers = modelNearByTrainers;
        this.context = context;

    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nearby_trainer_item, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;

    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        final ModelNearByTrainers trainers = modelNearByTrainers[position];
        holder.tvName.setText(trainers.getName());
        holder.tvPrice.setText("$" + trainers.getCost());
        holder.tvDistance.setText(String.valueOf(trainers.getDistance()) + " KMs");

        Picasso.with(context).load(GeneralUtils.URL + trainers.getProfilePhoto()).placeholder(R.drawable.profilepic).error(R.drawable.profilepic).into(holder.img);

        Log.e("data", trainers.getEmail() + "\n" + trainers.getDistance());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TrainerDetailsFragment fragment= new TrainerDetailsFragment();
                Bundle bundle=new Bundle();
                bundle.putSerializable("info",trainers);
                fragment.setArguments(bundle);
                ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack("tag").commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelNearByTrainers.length;
    }


    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        RatingBar ratingBar;
        TextView tvName, tvLname, tvPrice, tvDistance, tvStar;

        public RecyclerViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.name);
            tvLname = (TextView) view.findViewById(R.id.center);
            tvPrice = (TextView) view.findViewById(R.id.price);
            tvDistance = (TextView) view.findViewById(R.id.distance);
            img = view.findViewById(R.id.image);
            tvStar = view.findViewById(R.id.star_no);
            ratingBar = view.findViewById(R.id.rating);
        }
    }
}



