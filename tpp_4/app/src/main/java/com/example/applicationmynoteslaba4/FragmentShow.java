package com.example.applicationmynoteslaba4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.fragment.app.Fragment;

import com.example.applicationmynoteslaba4.adapters.NoteAdapter;
import com.example.applicationmynoteslaba4.helpers.DatabaseHelper;
import com.example.applicationmynoteslaba4.models.Note;

import java.util.List;

public class FragmentShow extends Fragment {
    private ListView notesListView;
    private NoteAdapter noteAdapter;
    private DatabaseHelper databaseHelper;

    public FragmentShow() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show, container, false);

        notesListView = view.findViewById(R.id.notesListView);
        databaseHelper = new DatabaseHelper(getActivity());

        loadNotes();

        return view;
    }

    private void loadNotes() {
        List<Note> notes = databaseHelper.getAllNotes();
        noteAdapter = new NoteAdapter(getActivity(), notes);
        notesListView.setAdapter(noteAdapter);
    }
}


