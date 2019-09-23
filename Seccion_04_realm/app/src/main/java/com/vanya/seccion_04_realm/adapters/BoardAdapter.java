package com.vanya.seccion_04_realm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.vanya.seccion_04_realm.R;
import com.vanya.seccion_04_realm.models.Board;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class BoardAdapter extends BaseAdapter {

    private Context context ;
    private List<Board> boards ;
    private int layout ;

    public BoardAdapter(Context context, List<Board> boards, int layout) {
        this.context = context;
        this.boards = boards;
        this.layout = layout;
    }


    @Override
    public int getCount() {
        return boards.size();
    }

    @Override
    public Board getItem(int position) {
        return boards.get(position);
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
            vh.title = convertView.findViewById(R.id.textViewBoardTitle) ;
            vh.notes = convertView.findViewById(R.id.textViewBoardNotes) ;
            vh.createdAt = convertView.findViewById(R.id.textViewDate) ;
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag() ;

        }

        Board board = boards.get(position) ;
        vh.title.setText(board.getTitle());

        int numberOfNotes = board.getNotes().size() ;
        String textForNotes = (numberOfNotes == 1) ? numberOfNotes + " note " : numberOfNotes + " notes" ;
        vh.notes.setText(textForNotes);
        DateFormat df = new SimpleDateFormat("dd/mm/yyyy") ;
        String createdAt = df.format(board.getCreatedAt()) ;
        vh.createdAt.setText(createdAt);
        return convertView;
    }

    public class ViewHolder{
        TextView title;
        TextView notes;
        TextView createdAt;
    }
}
