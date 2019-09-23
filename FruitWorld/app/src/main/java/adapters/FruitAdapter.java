package adapters;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vanya.fruitworld.R;

import org.w3c.dom.Text;

import java.util.List;

import models.Fruit;

public class FruitAdapter extends BaseAdapter {

    private Context context ;
    private int layout ;
    private List<Fruit> fruits ;

    public FruitAdapter(Context context, int layout, List<Fruit> fruits){
        this.context = context ;
        this.layout = layout ;
        this.fruits = fruits ;
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
            LayoutInflater layoutInflater = LayoutInflater.from(this.context) ;
            convertView = layoutInflater.inflate(this.layout,null) ;

            holder = new ViewHolder() ;

            holder.name = convertView.findViewById(R.id.name) ;
            holder.origin = convertView.findViewById(R.id.origin) ;
            holder.imgIcon = convertView.findViewById(R.id.imgIcon) ;

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag() ;
        }

        final Fruit currentFruit = (Fruit) getItem(position) ;
        holder.name.setText(currentFruit.getName()) ;
        holder.origin.setText((currentFruit.getOrigin()));
        holder.imgIcon.setImageResource(currentFruit.getIcon());

        return convertView ;

    }

    static class ViewHolder {
        private TextView name ;
        private TextView origin ;
        private ImageView imgIcon ;

    }
}
