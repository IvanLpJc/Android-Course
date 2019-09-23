package com.vanya.seccion_02;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends BaseAdapter {

    private Context context ; //Contexto donde vamos a usar este adaptador
    private int layout ; //Id del layout que queremos usar
    private List<String> names ; //Lo que vamos a usar

    public MyAdapter(Context context, int layout, List<String> names){
        this.context = context ;
        this.layout = layout ;
        this.names = names ;
    }

    //Dice cuantas veces hay que iterar sobre una coleccion que le damos
    //El return nos da el numero de items
    @Override
    public int getCount() {
        return this.names.size();
    }

    /*************************************/
    //Obtener un item
    @Override
    public Object getItem(int position) {
        return this.names.get(position);
    }

    //Obtener el Id de un item
    @Override
    public long getItemId(int position) {
        return position;
    }
    /***************************************/

    //Aqui se dibuja lo que queremos
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        /*  Esto es anterior al uso del Holder
            Es una forma con un mal rendimiento ya que la llamada a findView
            es muy pesada

        //Copiamos la vista
        View v = convertView ;

        //Inflamos la vista que nos ha llegado con nuestro layout personalizado
        LayoutInflater layoutInflater = LayoutInflater.from(this.context) ;
        v = layoutInflater.inflate(R.layout.list_item, null) ;


        //Nos traemos el valor actual dependiente de la posicion
        String currentName = names.get(position) ;

        //Referenciamos el elemento a modificar y lo rellenamos
        TextView textView = v.findViewById(R.id.textView);
        textView.setText(currentName);

        //Devolvemos la vista inflada y modificada con nuestros datos
        return v ;
        */

        // View Holder Pattern
        // Esto nos elimina la necesidad de tener que llamar a findViewById cada vez que
        // necesitemos el Id de algún item
        ViewHolder holder ;

        if(convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(this.context) ;
            convertView = layoutInflater.inflate(this.layout, null) ;

            holder = new ViewHolder() ;
            //Referenciamos el elemento a modificar y lo rellenamos
            holder.nameTextView = convertView.findViewById(R.id.textView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag() ;
        }

        String currentName = names.get(position) ;

        holder.nameTextView.setText(currentName);

        return convertView ;



    }

    static class ViewHolder {
        private TextView nameTextView ;
    }
}
