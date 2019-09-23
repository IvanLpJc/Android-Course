package com.vanya.fruitworldadvanced.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vanya.fruitworldadvanced.R;
import com.vanya.fruitworldadvanced.models.Fruits;


import java.lang.reflect.Type;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Fruits> fruits ;
    private int layout ;
    private OnItemClickListener itemClickListener;
    private Activity activity;

    public MyAdapter(List<Fruits> fruits, int layout, Activity activity, OnItemClickListener itemClickListener) {
        this.fruits = fruits;
        this.layout = layout;
        this.itemClickListener = itemClickListener;
        this.activity = activity ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(activity).inflate(layout, viewGroup, false) ;
        ViewHolder vh = new ViewHolder(v) ;
        return vh ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(fruits.get(i), itemClickListener) ;
    }

    @Override
    public int getItemCount() {
        return fruits.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        private ImageView fruitBackgroundIcon;
        private TextView fruitName ;
        private TextView fruitDescription ;
        private TextView fruitQuantity ;

        public ViewHolder(View itemView){
            super(itemView);
            fruitBackgroundIcon = itemView.findViewById(R.id.fruitIcon) ;
            fruitName = itemView.findViewById(R.id.fruitName) ;
            fruitDescription = itemView.findViewById(R.id.fruitDescription) ;
            fruitQuantity = itemView.findViewById(R.id.fruitQuantity);

            itemView.setOnCreateContextMenuListener(this);
        }

        public void bind(final Fruits fruit, final OnItemClickListener listener){
            this.fruitName.setText(fruit.getName());
            this.fruitDescription.setText(fruit.getDescription());
            this.fruitQuantity.setText(fruit.getQuantity()+"");

            if(fruit.getQuantity() == Fruits.MAX_QUANTITY){
                fruitQuantity.setTextColor(ContextCompat.getColor(activity, R.color.colorAlert));
                fruitQuantity.setTypeface(null, Typeface.BOLD);
            }else{
                fruitQuantity.setTextColor(ContextCompat.getColor(activity, R.color.colorPrimaryText));
                fruitQuantity.setTypeface(null, Typeface.NORMAL);
            }
            Picasso.get().load(fruit.getBackgroundIcon()).fit().into(this.fruitBackgroundIcon);

            this.fruitBackgroundIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(fruit, getAdapterPosition());
                }
            });
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            //Recogemos la posicion con el método getAdapterPosition
            Fruits fruitSelected = fruits.get(this.getAdapterPosition()) ;
            // Establecemos título e icono para cada elemento, mirando en sus propiedades
            menu.setHeaderTitle(fruitSelected.getName()) ;
            menu.setHeaderIcon(fruitSelected.getIcon()) ;

            // Inflamos el menu
            MenuInflater inflater = activity.getMenuInflater() ;
            inflater.inflate(R.menu.context_menu, menu);

            // Por último, añadimos uno por uno, el listener onMenuItemClick para
            // controlar las acciones en el contextMenu, anteriormente lo manejábamos
            // con el método onContextItemSelected en el activity
            for(int i = 0 ; i < menu.size() ; i++){
                menu.getItem(i).setOnMenuItemClickListener(this) ;
            }
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            // No obtenemos nuestro objeto info
            // porque la posición la podemos rescatar desde getAdapterPosition
            switch (item.getItemId()){
                case R.id.deleteBotton:
                    // Al estar en el adaptador, podemos acceder a los metodos
                    // propios de él, como notifyItemRemoved o notifyItemChanged
                    fruits.remove(getAdapterPosition()) ;
                    notifyItemRemoved(getAdapterPosition());
                    return true ;
                case R.id.resetFruit:
                    fruits.get(getAdapterPosition()).reset();
                    notifyItemChanged(getAdapterPosition());
                    return true ;
                    default:return false ;
            }
        }


    }

    public interface OnItemClickListener{
        void onItemClick(Fruits fruit, int position) ;
    }
}
