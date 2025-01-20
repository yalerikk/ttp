package com.example.applicationmynoteslaba4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import com.example.applicationmynoteslaba4.helpers.DatabaseHelper;

public class FragmentDel extends Fragment {
    private EditText editTextNoteIdToDelete;
    private Button buttonDel;
    private DatabaseHelper databaseHelper;

    public FragmentDel() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_del, container, false);

        editTextNoteIdToDelete = view.findViewById(R.id.editTextNoteIdToDelete);
        buttonDel = view.findViewById(R.id.buttonDel);
        databaseHelper = new DatabaseHelper(getActivity());

        buttonDel.setOnClickListener(v -> deleteNote());

        return view;
    }

    private void deleteNote() {
        String idText = editTextNoteIdToDelete.getText().toString().trim();

        if (!idText.isEmpty()) {
            int noteId = Integer.parseInt(idText);
            int result = databaseHelper.deleteNote(noteId);

            if (result > 0) {
                Toast.makeText(getActivity(), "Note deleted successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "No note found with that ID", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "Please enter a note ID", Toast.LENGTH_SHORT).show();
        }
    }
}
