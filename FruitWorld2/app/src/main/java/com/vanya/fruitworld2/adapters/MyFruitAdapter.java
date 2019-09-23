package com.vanya.fruitworld2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vanya.fruitworld2.R;
import com.vanya.fruitworld2.models.Fruit;

import java.util.List;

public class MyFruitAdapter extends BaseAdapter {

    private Context context ;
    private int layout ;
    private List<Fruit> fruits ;

    public MyFruitAdapter(Context context, int layout, List<Fruit> fruits){
        this.context = context ;
        this.fruits = fruits ;
        this.layout = layout ;
    }

    @Override
    public int getCount() {
        return this.fruits.size();
    }

    @Override
    public Object getItem(int position) {
        return this.fruits.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder ;

        if(convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(context) ;
            convertView = layoutInflater.inflate(layout, null) ;

            holder = new ViewHolder() ;

            holder.name = convertView.findViewById(R.id.textViewName) ;
            holder.origin = convertView.findViewById(R.id.textViewOrigin) ;
            holder.icon = convertView.findViewById(R.id.imageViewIcon) ;

            convertView.setTag(holder);

        }else{
            holder = (ViewHolder) convertView.getTag() ;
        }

        final Fruit currentFruit = (Fruit) getItem(position) ;
        holder.name.setText(currentFruit.getName());
        holder.icon.setImageResource(currentFruit.getIcon());
        holder.origin.setText(currentFruit.getOrigin());

        return convertView ;

    }

    static class ViewHolder{
        private TextView name ;
        private TextView origin ;
        private ImageView icon ;
    }
}
