package com.vanya.seccion_04_realm.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vanya.seccion_04_realm.R;
import com.vanya.seccion_04_realm.models.Note;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NoteAdapter extends BaseAdapter {

    private Context context;
    private int layout ;
    private List<Note> notes ;

    public NoteAdapter(Context context, List<Note> notes, int layout) {
        this.context = context;
        this.layout = layout;
        this.notes = notes;
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Note getItem(int position) {
        return notes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh ;

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(layout,null) ;
            vh = new ViewHolder() ;
            vh.description = convertView.findViewById(R.id.textViewNoteDescription) ;
            vh.createdAt = convertView.findViewById(R.id.textViewNoteCreatedAt) ;
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag() ;

        }

        Note note = notes.get(position) ;

        vh.description.setText(note.getDescription());

        DateFormat df = new SimpleDateFormat("dd/mm/yyyy") ;
        String createdAt = df.format(note.getCreatedAt()) ;
        vh.createdAt.setText(createdAt);

        return convertView;
    }

    public class ViewHolder{
        TextView description ;
        TextView createdAt;
    }
}
