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

public class FragmentUpdate extends Fragment {
    private EditText editTextNoteIdToUpdate, editTextNewDescription;
    private Button buttonUpdate;
    private DatabaseHelper databaseHelper;

    public FragmentUpdate() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update, container, false);

        editTextNoteIdToUpdate = view.findViewById(R.id.editTextNoteIdToUpdate);
        editTextNewDescription = view.findViewById(R.id.editTextNewDescription);
        buttonUpdate = view.findViewById(R.id.buttonUpdate);
        databaseHelper = new DatabaseHelper(getActivity());

        buttonUpdate.setOnClickListener(v -> updateNote());

        return view;
    }

    private void updateNote() {
        String idText = editTextNoteIdToUpdate.getText().toString().trim();
        String newDescription = editTextNewDescription.getText().toString().trim();

        if (!idText.isEmpty() && !newDescription.isEmpty()) {
            int noteId = Integer.parseInt(idText);
            int result = databaseHelper.updateNote(noteId, newDescription);

            if (result > 0) {
                Toast.makeText(getActivity(), "Note updated successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "No note found with that ID", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "Please enter both ID and new description", Toast.LENGTH_SHORT).show();
        }
    }
}
