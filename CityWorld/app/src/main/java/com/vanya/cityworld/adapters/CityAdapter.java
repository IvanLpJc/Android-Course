package com.vanya.cityworld.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vanya.cityworld.R;
import com.vanya.cityworld.models.City;

import org.w3c.dom.Text;

import java.util.List;

public class CityAdapter extends  RecyclerView.Adapter<CityAdapter.ViewHolder>{

    private List<City> cities ;
    private int layout ;
    private OnItemClickListener itemListener ;
    private OnButtonClickListener buttonListener ;
    private Context context ;

    public CityAdapter(List<City> cities, int layout, OnItemClickListener itemListener, OnButtonClickListener buttonListener) {
        this.cities = cities;
        this.layout = layout;
        this.itemListener = itemListener;
        this.buttonListener = buttonListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup,false) ;
        context = viewGroup.getContext() ;
        ViewHolder vh = new ViewHolder(v) ;
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(cities.get(i), itemListener, buttonListener);
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageViewCity ;
        public TextView textViewCityName ;
        public TextView textViewCityStars ;
        public TextView textViewCityDescription ;

        public Button btnDelete ;

        public ViewHolder(View itemView){

            super(itemView);

            textViewCityName = itemView.findViewById(R.id.textViewCityName) ;
            textViewCityDescription = itemView.findViewById(R.id.textViewCityDescription) ;
            textViewCityStars = itemView.findViewById(R.id.textViewStars) ;
            imageViewCity = itemView.findViewById(R.id.imageViewCity) ;
            btnDelete = itemView.findViewById(R.id.buttonDeleteCity) ;
        }

        public void bind(final City city, final OnItemClickListener itemListener, final OnButtonClickListener buttonListtener){
            this.textViewCityName.setText(city.getName());
            this.textViewCityDescription.setText(city.getDescription()); ;
            this.textViewCityStars.setText(city.getRate()+"");

            Picasso.get().load(city.getBackgroundImage()).into(imageViewCity);

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonListtener.onButtonClick(city,getAdapterPosition());
                }
            });

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    itemListener.onItemClick(city,getAdapterPosition());
                }
            });


        }
    }

    public interface OnItemClickListener{
        void onItemClick(City citiy, int position) ;
    }

    public interface OnButtonClickListener{
        void onButtonClick(City city, int position) ;
    }
}
