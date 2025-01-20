package com.example.applicationmynoteslaba4.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.applicationmynoteslaba4.R;
import com.example.applicationmynoteslaba4.models.Note;

import java.util.List;

public class NoteAdapter extends BaseAdapter {
    private Context context;
    private List<Note> notes;

    public NoteAdapter(Context context, List<Note> notes) {
        this.context = context;
        this.notes = notes;
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Object getItem(int position) {
        return notes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return notes.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_note, parent, false);
        }

        TextView noteIdTextView = convertView.findViewById(R.id.noteIdTextView);
        TextView noteDescriptionTextView = convertView.findViewById(R.id.noteDescriptionTextView);

        Note note = notes.get(position);
        noteIdTextView.setText(String.valueOf(note.getId()));
        noteDescriptionTextView.setText(note.getDescription());

        return convertView;
    }
}
